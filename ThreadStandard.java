import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.concurrent.ArrayBlockingQueue;

public class ThreadStandard extends Thread{
	private final ArrayBlockingQueue <String> queue;
	
	public ThreadStandard(String command, ArrayBlockingQueue <String> queue) {
		super(command);
		this.queue = queue;
	}
	
    public void run(){
        String s;
        Process p;
        
        try {
        		//queue.put("VBTEST");
            p = Runtime.getRuntime().exec(this.getName());
            BufferedReader br = new BufferedReader(
                new InputStreamReader(p.getInputStream()));
            while ((s = br.readLine()) != null)
            	
            {
            		//System.out.println("line: " + s);
            		queue.put(s);
            }
    				//queue.put("VBTEST");
                //System.out.println("line: " + s);
            //p.waitFor();
            //System.out.println ("exit: " + p.exitValue());
            //p.destroy();
        } catch (Exception e) {}
    	
      }   
}

	
