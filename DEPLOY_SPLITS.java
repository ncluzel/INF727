import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

public class DEPLOY_SPLITS {
	
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
        List <String> computers_in_network = readAndStoreIP("/Users/cluclu/Documents/IP_Hadoop.txt");
        
        System.out.println(computers_in_network);
        
        for (int i = 0; i < 3 ; i++) {
        	// ATTENTION GERER LA POSSIBILITE QUE LE PC i NE REPONDE PAS ET PASSER AU SUIVANT
        	//computers_in_network.size()
        		ArrayBlockingQueue<String> queue = new ArrayBlockingQueue <> (6);
            ThreadStandard s_copy_split = new ThreadStandard("scp -r -p /tmp/ncluzel/S" + Integer.toString(i) + ".txt" + " ncluzel@" + computers_in_network.get(i) + ":/tmp/ncluzel/splits/S" + Integer.toString(i) + ".txt", queue);
            ThreadError e_copy_split = new ThreadError("scp -r -p /tmp/ncluzel/S" + Integer.toString(i) + ".txt" +  " ncluzel@" + computers_in_network.get(i) + ":/tmp/ncluzel/splits/S" + Integer.toString(i) + ".txt", queue);
            ThreadStandard s_mkdir = new ThreadStandard("ssh ncluzel@" + computers_in_network.get(i) + " mkdir -p /tmp/ncluzel/splits", queue);
            ThreadError e_mkdir = new ThreadError("ssh ncluzel@" + computers_in_network.get(i) + " mkdir -p /tmp/ncluzel/splits", queue);
            ThreadStandard s_test_response = new ThreadStandard("ssh ncluzel@" + computers_in_network.get(i) + " hostname", queue);
            ThreadError e_test_response = new ThreadError("ssh ncluzel@" + computers_in_network.get(i) + " hostname", queue);
            
            long timeout_1 = 2000;

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
            s_copy_split.start();
            e_copy_split.start();
        	
        }
	}
}
