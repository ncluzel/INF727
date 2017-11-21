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
import java.util.TreeMap;
import java.util.Map.Entry;

public class SLAVE_2 {

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
		//firstVersion = countWords("/tmp/ncluzel/splits/S0.txt");
		//firstVersion = countWords(args[0]);
		//MapComparator sortedByValue = new MapComparator(firstVersion);
		//Map<String,Integer> newMapSorted = new TreeMap(sortedByValue);
	    //newMapSorted.putAll(firstVersion);
	    for (String i : args) {
			firstVersion = countWords("/tmp/ncluzel/splits/S" + Integer.parseInt(i) + ".txt");
			MapComparator sortedByValue = new MapComparator(firstVersion);
			Map<String,Integer> newMapSorted = new TreeMap(sortedByValue);
		    newMapSorted.putAll(firstVersion);
		    /*try (PrintStream out = new PrintStream(new FileOutputStream("/tmp/ncluzel/UMs/UM" + Integer.parseInt(i) + ".txt"))) {
				for (String word : firstVersion.keySet()) {
					//System.out.println(word + ": "+ firstVersion.get(word));
					//System.out.println(word);
					out.println(word + ": "+ firstVersion.get(word));
				}
		    }*/
		    //hashset pour passer de Car Car dans une liste à seulement Car
		    try {
		    		OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream("/tmp/ncluzel/UMs/UM" + Integer.parseInt(i) + ".txt"));
				for (String word : firstVersion.keySet()) {
					System.out.println(word + ": "+ firstVersion.get(word));
					out.write(word + ": "+ firstVersion.get(word) + "\n");
				}
				out.close();
		    } catch(IOException exp) { exp.printStackTrace(); }
	    }
	    //try (PrintStream out = new PrintStream(new FileOutputStream("/tmp/ncluzel/write/UM4.txt"))) {
		//	for (String word : firstVersion.keySet()) {
		//		System.out.println(word + ": "+ firstVersion.get(word));
		//		out.println(word + ": "+ firstVersion.get(word));
		//	}
	    //}
	    //System.out.println(entriesSortedByValues(firstVersion));
	}
}
//}


