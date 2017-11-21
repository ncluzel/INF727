import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.concurrent.ArrayBlockingQueue;

public class ThreadError extends Thread{
	private final ArrayBlockingQueue <String> queue;
	
	public ThreadError(String command, ArrayBlockingQueue <String> queue) {
		super(command);
		this.queue = queue;
	}
	
	public void run(){
		//ArrayBlockingQueue<String> queue = new ArrayBlockingQueue <> (2);		
	    String s;
	    Process p;
	    
	    try {
	        p = Runtime.getRuntime().exec(this.getName());
	        BufferedReader br2 = new BufferedReader(
	        		new InputStreamReader(p.getErrorStream()));
	        while ((s = br2.readLine()) != null)
	        {
	            //System.err.println("line: " + s);
	        		queue.put(s);
	        }	
	        p.waitFor();
	        //System.out.println ("exit: " + p.exitValue());
	        p.destroy();
	    } catch (Exception e) {}
	}
	
	
}
