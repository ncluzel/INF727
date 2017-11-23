import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;
import java.util.Map.Entry;

public class SLAVE_3{
	
    public static HashMap<String, Integer> countWords(String txt_file) throws FileNotFoundException, IOException {
		HashMap<String, Integer> count = new HashMap <String, Integer>();
    try (BufferedReader br = new BufferedReader(new FileReader(txt_file))) {
        StringBuilder stringBuilder = new StringBuilder();
        String line = br.readLine();

        while (line != null) {
            String[] words = line.split(" ");
            for (int i = 0; i < words.length; i++) {
                if (count.get(words[i]) == null) {
                    count.put(words[i], 1);
                } else {
                    int value = Integer.valueOf(String.valueOf(count.get(words[i])));
                    value++;
                    count.put(words[i], value);
                }
            }
            stringBuilder.append(System.lineSeparator());
            line = br.readLine();
        }
    }
    Map<String, Integer> sorted = new TreeMap<String, Integer>(count);
    return count;
}
	
    public static <K,V extends Comparable<? super V>> List<Entry<K, V>> entriesSortedByValues(Map<K,V> map) {
	List<Entry<K,V>> sortedEntries = new ArrayList<Entry<K,V>>(map.entrySet());
    	Collections.sort(sortedEntries, 
    new Comparator<Entry<K,V>>() {
        @Override
        public int compare(Entry<K,V> e1, Entry<K,V> e2) {
            return e2.getValue().compareTo(e1.getValue());
        }
    }
    	);
    	
    	return sortedEntries;
}

		
	public static void main(String[] args) throws FileNotFoundException, IOException{
		HashMap<String, Integer> firstVersion = new HashMap <String, Integer>();

			if (Objects.equals(args[0], "1"))		
	    		{
	    		
	    		String key = args[1];
	    		List <String> values = new ArrayList <String>();
	    		OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream("/tmp/ncluzel/SMs/SM" + args[2] + ".txt"));
	    		OutputStreamWriter out_r = new OutputStreamWriter(new FileOutputStream("/tmp/ncluzel/RMs/RM" + args[2] + ".txt"));
	    		int c = 0;
	    		
	    		//for (int k = 2; k < args.length; k++) {	
	    		for (int k = 3; k < args.length; k++) {	 
 		
	    		try (BufferedReader br = new BufferedReader(new FileReader(args[k]))) {
	    	        StringBuilder stringBuilder = new StringBuilder();
	    	        String line = br.readLine();
	    	        
	    	        while(line!=null) {
	    	            String[] words = line.split(":");    	            
	    	            if (Objects.equals(words[0], args[1])){
	    	            	System.out.println(args[1] + " present");	
	    	            	out.write(line + "\n");
	    	            }
	    	            
	    	            else
	    	            {
	    	            }
	    	            stringBuilder.append(System.lineSeparator());
	    	            line = br.readLine();
	    	        }
	    		}
	    		catch(IOException exp) {}
	    	    }
	    		out.close();
	    		
	    		try (BufferedReader br = new BufferedReader(new FileReader("/tmp/ncluzel/SMs/SM" + args[2] + ".txt"))) {
	    	        StringBuilder stringBuilder = new StringBuilder();
	    	        String line = br.readLine();

	    	        while (line!=null) {
	    	        	
	    	        
	    	        
	    	            String[] words = line.split(":");    	            
	    	            if (Objects.equals(words[0], args[1])){
	    	            	System.out.println("test d'addition");
	    	            c+= Integer.parseInt(words[1].split(" ")[1]);
	    	            //	out_r.write(words[0] + ": " + c + "\n");
	    	            }
	    	            
	    	            else
	    	            {
	    	            }
	    	            stringBuilder.append(System.lineSeparator());
	    	            line = br.readLine();
	    		}
	    		}
	    		catch(IOException exp) {}
            	out_r.write(args[1] + ": " + c + "\n");
	    		out_r.close();
	    		
	    		}
	    		
			else if (Objects.equals(args[0], "0"))    		
	    		{
	    			
	    		for (int k = 1; k < args.length; k++) {
			firstVersion = countWords("/tmp/ncluzel/splits/S" + args[k] + ".txt");
			MapComparator sortedByValue = new MapComparator(firstVersion);
			Map<String,Integer> newMapSorted = new TreeMap(sortedByValue);
		    newMapSorted.putAll(firstVersion);    		
	    		
		    try {
		    		OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream("/tmp/ncluzel/UMs/UM" + args[k] + ".txt"));
				for (String word : firstVersion.keySet()) {
					System.out.println(word + ": "+ firstVersion.get(word));
					out.write(word + ": "+ firstVersion.get(word) + "\n");
				}
				out.close();
		    } catch(IOException exp) { exp.printStackTrace(); }
	    		}
	    		}
	    		
			else
	    		{
	    			System.out.println("Incorrect mode.");
	    		}
	    		
	}
}




