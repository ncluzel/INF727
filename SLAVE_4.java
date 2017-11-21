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

public class SLAVE_4{
	
    public static HashMap<String, Integer> countWords(String txt_file) throws FileNotFoundException, IOException {
		HashMap<String, Integer> count = new HashMap <String, Integer>();
		//TreeMap<String, Integer> count = new TreeMap <String, Integer>();
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
   // for (Object key : sort_by_count.keySet()) {
   //     System.out.println("Word: " + key + "\tCounts: " + count.get(key));
   // }

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
	    		OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream("/tmp/ncluzel/SMs/SM" + "1" + ".txt"));
	    		
	    		for (int k = 2; k < args.length; k++) {	    		
 		
	    		try (BufferedReader br = new BufferedReader(new FileReader(args[k]))) {
	    	        StringBuilder stringBuilder = new StringBuilder();
	    	        String line = br.readLine();

	    	            String[] words = line.split(":");    	            
	    	            if (Objects.equals(words[0], args[1])){
	    	            	System.out.println(args[1] + " present");	
	    	            	out.write(line + "\n");
	    	            }
	    	            
	    	            else
	    	            {
	    	            }
	    		}
	    		catch(IOException exp) {}
	    	    }
	    		out.close();
	    		
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

/*firstVersion = countWords(args[k]);
MapComparator sortedByValue = new MapComparator(firstVersion);
Map<String,Integer> newMapSorted = new TreeMap(sortedByValue);
newMapSorted.putAll(firstVersion);    
values.add(Integer.toString(firstVersion.get(key)));	    		    				    			
}*/	    			    		
/*try {
	// On concatène ici avec 1 comme numéro de SM. Idéalement, il faudra assigner un indice à chaque clé du dico...
OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream("/tmp/ncluzel/SMs/SM" + "1" + ".txt"));
/*for (String word : firstVersion.keySet()) {
	System.out.println(word + ": "+ firstVersion.get(word));
	out.write(word + ": "+ firstVersion.get(word) + "\n");
}*/
/*    for (String line : ) {
	if (Objects.equals(args[1], line.split(":")[0])){
		out.write(line + "\n");
	}			    	
}
out.close();
} catch(IOException exp) { exp.printStackTrace(); }*/	   



