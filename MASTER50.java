import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MASTER50 {
	
	public static  List <String> readAndStoreIP (String txt_file) {
		List <String> computers_in_network = new ArrayList <String>();
		
		try (BufferedReader br = new BufferedReader(new FileReader(txt_file))) {
            StringBuilder stringBuilder = new StringBuilder();
            String line = br.readLine();
       
            while (line != null) {
                String[] words = line.split(" ");
                for (int i = 0; i < words.length; i++) {
                		computers_in_network.add(words[i]);

                }
                stringBuilder.append(System.lineSeparator());
                line = br.readLine();
            }
            
        } catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return computers_in_network;
	}
	
	public static void main(String[] args) {
		List <String> computers_in_network = new ArrayList <String> ();
        List <String> pool_of_computers = readAndStoreIP("/Users/cluclu/Documents/IP_Hadoop.txt");        
        Map<String, String> map = new HashMap<String, String>();
		List <String> UMList = new ArrayList <String>();
		List <String> UMList_tot = new ArrayList <String>();
		List <String> UMList_values = new ArrayList <String>();
		ConcurrentHashMap<String, List <String>> dictUM = new ConcurrentHashMap <String, List <String>>();
		ConcurrentHashMap<String, String> dictUM_content = new ConcurrentHashMap <String, String>();
		ArrayBlockingQueue<String> pool_queue = new ArrayBlockingQueue <> (1000);
		long timeout_pool = 2000;
		
		// On va d'abord parcourir le pool d'ordinateurs et en récupérer trois qui sont disponibles:
		// On laisse deux secondes à un ordinateur pour qu'il ait le temps de répondre
		
		for (int l = 0; l < pool_of_computers.size(); l++) {
			
		ThreadStandard s_test_response_pool = new ThreadStandard("ssh ncluzel@" + pool_of_computers.get(l) + " hostname", pool_queue);
        ThreadError e_test_response_pool = new ThreadError("ssh ncluzel@" + pool_of_computers.get(l) + " hostname", pool_queue);
		
        if (computers_in_network.size() == 3) {
            break;
        }        
        else
        {        
        s_test_response_pool.start();
        e_test_response_pool.start(); 
        
        try {
        	String test = pool_queue.poll(timeout_pool, TimeUnit.MILLISECONDS);
        		if(test == null) {
        			s_test_response_pool.interrupt();
        			e_test_response_pool.interrupt();
        		}
        		else {
        			computers_in_network.add(pool_of_computers.get(l));
        			System.out.println(computers_in_network);
        		}
        }      
        catch (Exception e) {}		
		}
		}
		
		try {
		Thread.sleep(2000);
		System.out.println(computers_in_network);
		}
		catch (Exception e) {}
		
		// POUR LE PARALLELE, CREER TOUS LES THREADS DANS UNE BOUCLE
		// LES PLACER DANS UNE LISTE
		// TOUT LANCER
		// On considère arbitrairement que le premier ordinateur de la liste sera celui sur lequel on copiera les UM:
        	for (int i = 0; i < 3 ; i++) {
        	// process.join
        	// créer des threads machine (master, numeromachine)
            long timeout_1 = 2000;
        		ArrayBlockingQueue<String> queue = new ArrayBlockingQueue <> (1000);
            ThreadStandard s_copy_split = new ThreadStandard("scp -r -p /tmp/ncluzel/S" + Integer.toString(i) + ".txt" + " ncluzel@" + computers_in_network.get(i) + ":/tmp/ncluzel/splits/S" + Integer.toString(i) + ".txt", queue);
            ThreadError e_copy_split = new ThreadError("scp -r -p /tmp/ncluzel/S" + Integer.toString(i) + ".txt" +  " ncluzel@" + computers_in_network.get(i) + ":/tmp/ncluzel/splits/S" + Integer.toString(i) + ".txt", queue);
            ThreadStandard s_copy_jar = new ThreadStandard("scp -r -p /tmp/ncluzel/slave_5.jar ncluzel@" + computers_in_network.get(i) + ":/tmp/ncluzel/slave_5.jar", queue);
            ThreadError e_copy_jar = new ThreadError("scp -r -p /tmp/ncluzel/slave_5.jar ncluzel@" + computers_in_network.get(i) + ":/tmp/ncluzel/slave_5.jar", queue);
            ThreadStandard s_mkdir = new ThreadStandard("ssh ncluzel@" + computers_in_network.get(i) + " mkdir -p /tmp/ncluzel/splits", queue);
            ThreadError e_mkdir = new ThreadError("ssh ncluzel@" + computers_in_network.get(i) + " mkdir -p /tmp/ncluzel/splits", queue);
            ThreadStandard s_mkdir_UMs = new ThreadStandard("ssh ncluzel@" + computers_in_network.get(i) + " mkdir -p /tmp/ncluzel/UMs", queue);
            ThreadError e_mkdir_UMs = new ThreadError("ssh ncluzel@" + computers_in_network.get(i) + " mkdir -p /tmp/ncluzel/UMs", queue);
            ThreadStandard s_mkdir_SMs = new ThreadStandard("ssh ncluzel@" + computers_in_network.get(i) + " mkdir -p /tmp/ncluzel/SMs", queue);
            ThreadError e_mkdir_SMs = new ThreadError("ssh ncluzel@" + computers_in_network.get(i) + " mkdir -p /tmp/ncluzel/SMs", queue);
            ThreadStandard s_mkdir_RMs = new ThreadStandard("ssh ncluzel@" + computers_in_network.get(i) + " mkdir -p /tmp/ncluzel/RMs", queue);
            ThreadError e_mkdir_RMs = new ThreadError("ssh ncluzel@" + computers_in_network.get(i) + " mkdir -p /tmp/ncluzel/RMs", queue);
            ThreadStandard s_test_response = new ThreadStandard("ssh ncluzel@" + computers_in_network.get(i) + " hostname", queue);
            ThreadError e_test_response = new ThreadError("ssh ncluzel@" + computers_in_network.get(i) + " hostname", queue);
            ThreadStandard s_write_UM = new ThreadStandard("ssh ncluzel@" + computers_in_network.get(i) + " java -jar /tmp/ncluzel/slave_5.jar " + "0 " + Integer.toString(i), queue);
            ThreadError e_write_UM = new ThreadError("ssh ncluzel@" + computers_in_network.get(i) + " java -jar /tmp/ncluzel/slave_5.jar " + "0 " + Integer.toString(i), queue);
            
            
            

            map.put("UM" + Integer.toString(i), computers_in_network.get(i));
            s_test_response.start();
            e_test_response.start(); 
            
            try {
            	String test = queue.poll(timeout_1, TimeUnit.MILLISECONDS);
            		if(test == null) {
            			s_test_response.interrupt();
            			e_test_response.interrupt();
            		}
            }
            
            catch (Exception e) {}
            
            try {
        		s_mkdir.start();
        		e_mkdir.start();
        		s_mkdir_UMs.start();
        		e_mkdir_UMs.start();
        		s_mkdir_SMs.start();
        		e_mkdir_SMs.start();
        		s_mkdir_RMs.start();
        		e_mkdir_RMs.start();
        		s_copy_split.start();
        		e_copy_split.start();
        		s_copy_jar.start();
        		e_copy_jar.start();
        		Thread.sleep(2000);
        		s_write_UM.start();
        		e_write_UM.start();
            }
            catch (Exception e) {}
        		
            try {
            Thread.sleep(2000);
            	System.out.println(queue.size());    
            	
            	while(queue.size() != 0) 
            		{
            			String test = queue.poll(timeout_1, TimeUnit.MILLISECONDS);
            			System.out.println(test);
            			UMList.add(test.split(":")[0] + ":UM" + Integer.toString(i));
            		}
            }
            
            catch (Exception e) {}            
            System.out.println(map);
            System.out.println(UMList);
        }
        
        System.out.println(UMList);
        
        for (int j = 0; j < UMList.size() ; j++) {
        	for (int k = j; k < UMList.size() ; k++) {
        		
        	if (Objects.equals(UMList.get(j).split(":")[0], UMList.get(k).split(":")[0]) && k!=j)
        	
        	{
        		UMList.add(k, UMList.get(j).split(":")[0] + ":" + UMList.get(j).split(":")[1] + "," + UMList.get(k).split(":")[1]);
        		UMList.remove(j);
        		UMList.remove(k);
        		;
        	}
        	
        	for (int l = 1; l < UMList.size() ; l++) {
        		
        	if (Objects.equals(UMList.get(0).split(":")[0], UMList.get(l).split(":")[0]))
        	{
        		UMList.add(l, UMList.get(0).split(":")[0] + ":" + UMList.get(0).split(":")[1] + "," + UMList.get(l).split(":")[1]);
        		UMList.remove(0);
        		UMList.remove(l);
        		;
        	}
        	}
        	
        	for (int n = 0; n < UMList.size(); n++)
        	{
        	List <String> List_UM = new ArrayList <String>();
        	String[] umtest=UMList.get(n).split(":")[1].split(",");
        	
        	for (int m = 0; m <umtest.length; m++)
        	{
        	List_UM.add(UMList.get(n).split(":")[1].split(",")[m]);
        	}
        	dictUM.put(UMList.get(n).split(":")[0], List_UM);
        }
        	}
        }
    	System.out.println(dictUM);
    	System.out.println("Phase de Map terminée.");
    	
    	dictUM.remove("ssh_exchange_identification");
    	dictUM.remove("lost connection");
    	
    	System.out.println(dictUM);
    	  	
    	for (String key : dictUM.keySet()) {
    		System.out.println(key);
    		String UM_for_SM = "";
    		UMList_values = dictUM.get(key);
		ArrayBlockingQueue<String> queue = new ArrayBlockingQueue <> (1000);
    		for (int p =0; p <UMList_values.size(); p++) {
    			UM_for_SM += "/tmp/ncluzel/UMs/" + UMList_values.get(p) + ".txt" +" ";
    		}
    		
    		try {
    		
    			ThreadStandard s_copy_UM = new ThreadStandard("scp -r -p ncluzel@" + map.get(UMList_values.get(0)) + ":/tmp/ncluzel/UMs/" + UMList_values.get(0) + ".txt" + " ncluzel@" + map.get(UMList_values.get(1)) + ":/tmp/ncluzel/UMs/" + UMList_values.get(0) + ".txt", queue);
    			ThreadError e_copy_UM = new ThreadError("scp -r -p ncluzel@" + map.get(UMList_values.get(0)) + ":/tmp/ncluzel/UMs/" + UMList_values.get(0) + ".txt" + " ncluzel@" + map.get(UMList_values.get(1)) + ":/tmp/ncluzel/UMs/" + UMList_values.get(0) + ".txt", queue);

            s_copy_UM.start();
    			e_copy_UM.start();
    		}
    		
    		catch (Exception e) {}
    		
    		System.out.println(UM_for_SM);
    		System.out.println(map);
    		System.out.println(UMList_values);
    		
    		try {
    		
    		ThreadStandard s_write_SM = new ThreadStandard("ssh ncluzel@" + map.get(UMList_values.get(1)) + " java -jar /tmp/ncluzel/slave_5.jar " + "1 " + key + " " + UM_for_SM, queue);
        	ThreadError e_write_SM = new ThreadError("ssh ncluzel@" + map.get(UMList_values.get(1)) + " java -jar /tmp/ncluzel/slave_5.jar " + "1 " + key + " " + UM_for_SM, queue);
    		
        s_write_SM.start();
 		e_write_SM.start();
    		}
    		
    		catch (Exception e) {}
    		
    	}
	}
}

