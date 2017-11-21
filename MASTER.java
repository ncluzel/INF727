import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;


public class MASTER {
	
    public static void main(String args[]) {
    	
    		ArrayBlockingQueue<String> queue = new ArrayBlockingQueue <> (2);
        String s;
        Process p;
        long timeout_1 = 2000;
        ThreadStandard t1 = new ThreadStandard("java -jar /tmp/ncluzel/slave2.jar", queue);
        ThreadError t2 = new ThreadError("java -jar /tmp/ncluzel/slave2.jar", queue);
        t1.start();
        t2.start();
        
        try {
        	String test = queue.poll(timeout_1, TimeUnit.MILLISECONDS);
        		if(test == null)
        			t1.stop();
        			t2.stop();
        	System.out.println(test);
        }
        
        catch (Exception e) {}
        
/*        try {
            p = Runtime.getRuntime().exec("java -jar /tmp/ncluzel/slave.jar");
            BufferedReader br = new BufferedReader(
                new InputStreamReader(p.getInputStream()));
            BufferedReader br2 = new BufferedReader(
            		new InputStreamReader(p.getErrorStream()));
            while ((s = br.readLine()) != null)
                System.out.println("line: " + s);
            while ((s = br2.readLine()) != null)
                System.err.println("line: " + s);
            p.waitFor();
            System.out.println ("exit: " + p.exitValue());
            p.destroy();
        } catch (Exception e) {}
    */}
    
}