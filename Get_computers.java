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

public class Get_computers {
	
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
		//return words;
	}
	
	public static void main(String[] args) {
        //List <String> computers_in_network = readAndStoreIP("/Users/cluclu/Documents/IP_Hadoop.txt");
		List <String> computers_in_network = new ArrayList <String> ();
        List <String> pool_of_computers = readAndStoreIP("/Users/cluclu/Documents/IP_Hadoop.txt");
        
        Map<String, String> map = new HashMap<String, String>();
        //System.out.println(computers_in_network);
		List <String> UMList = new ArrayList <String>();
		List <String> UMList_tot = new ArrayList <String>();
		List <String> UMList_values = new ArrayList <String>();
		ConcurrentHashMap<String, List <String>> dictUM = new ConcurrentHashMap <String, List <String>>();
		ConcurrentHashMap<String, String> dictUM_content = new ConcurrentHashMap <String, String>();
		ArrayBlockingQueue<String> pool_queue = new ArrayBlockingQueue <> (1000);
		long timeout_pool = 2000;
		//System.out.println(pool_of_computers);
		//System.out.println(computers_in_network);
		
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
	}
}
