import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.concurrent.ArrayBlockingQueue;

public class OldThreadS extends Thread{
	
	public OldThreadS(String command) {
		super(command);
	}
	
    public void run(){
    		ArrayBlockingQueue<String> queue = new ArrayBlockingQueue <> (2);
        String s;
        Process p;
        
        try {
            p = Runtime.getRuntime().exec(this.getName());
            BufferedReader br = new BufferedReader(
                new InputStreamReader(p.getInputStream()));
            while ((s = br.readLine()) != null)
                System.out.println("line: " + s);
            		queue.put(s);
            p.waitFor();
            System.out.println ("exit: " + p.exitValue());
            p.destroy();
        } catch (Exception e) {}
    	
      }   
}
