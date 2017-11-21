import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

public class DEPLOY_SET {
	
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
        //String s_copy_jar;
        //Process p_copy_jar;
        //String s_mkdir;
        //Process p_mkdir;
        //String s_test_response;
        //Process p_test_response;
        List <String> computers_in_network = readAndStoreIP("/Users/cluclu/Documents/IP_Hadoop.txt");
        
        System.out.println(computers_in_network);
        
        for (String computer : computers_in_network) {
        		ArrayBlockingQueue<String> queue = new ArrayBlockingQueue <> (6);
            ThreadStandard s_copy_jar = new ThreadStandard("scp -r -p /tmp/ncluzel/slave.jar ncluzel@" + computer + ":/tmp/ncluzel/slave.jar", queue);
            ThreadError e_copy_jar = new ThreadError("scp -r -p /tmp/ncluzel/slave.jar ncluzel@" + computer + ":/tmp/ncluzel/slave.jar", queue);
            ThreadStandard s_mkdir = new ThreadStandard("ssh ncluzel@" + computer + " mkdir -p /tmp/ncluzel", queue);
            ThreadError e_mkdir = new ThreadError("ssh ncluzel@" + computer + " mkdir -p /tmp/ncluzel", queue);
            ThreadStandard s_test_response = new ThreadStandard("ssh ncluzel@" + computer + " hostname", queue);
            ThreadError e_test_response = new ThreadError("ssh ncluzel@" + computer + " hostname", queue);
            
            long timeout_1 = 2000;
        /*	try {
                p_test_response = Runtime.getRuntime().exec("ssh ncluzel@" + computer + " hostname");
                BufferedReader br = new BufferedReader(
                    new InputStreamReader(p_test_response.getInputStream()));
                while ((s_test_response = br.readLine()) != null)
                    System.out.println("line: " + s_test_response);
                p_test_response.waitFor();
                System.out.println ("exit: " + p_test_response.exitValue());
                //System.err.println();
                p_test_response.destroy();
            } catch (Exception e) {}*/
            s_test_response.start();
            e_test_response.start(); 
            
            try {
            	String test = queue.poll(timeout_1, TimeUnit.MILLISECONDS);
            		if(test == null)
            			s_test_response.interrupt();
            			e_test_response.interrupt();
            	System.out.println(test);
            }
            
            catch (Exception e) {}
            
            s_mkdir.start();
            e_mkdir.start();        
            s_copy_jar.start();
            e_copy_jar.start();
        	
        /*try {
            p_mkdir = Runtime.getRuntime().exec("ssh ncluzel@" + computer + " mkdir -p /tmp/ncluzel");
            BufferedReader br = new BufferedReader(
                new InputStreamReader(p_mkdir.getInputStream()));
            while ((s_mkdir = br.readLine()) != null)
                System.out.println("line: " + s_mkdir);
            p_mkdir.waitFor();
            System.out.println ("exit: " + p_mkdir.exitValue());
            //System.err.println();
            p_mkdir.destroy();
        } catch (Exception e) {}*/

/*        try {
            p_copy_jar = Runtime.getRuntime().exec("scp -r -p /tmp/ncluzel/slave.jar ncluzel@" + computer + ":/tmp/ncluzel/slave.jar");
            BufferedReader br = new BufferedReader(
                new InputStreamReader(p_copy_jar.getInputStream()));
            while ((s_copy_jar = br.readLine()) != null)
                System.out.println("line: " + s_copy_jar);
            p_copy_jar.waitFor();
            System.out.println ("exit: " + p_copy_jar.exitValue());
            //System.err.println();
            p_copy_jar.destroy();
        } catch (Exception e) {}*/
        }
	}
}
