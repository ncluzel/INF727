import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class DEPLOY {
	public static void main(String[] args) {
        String s_copy_jar;
        Process p_copy_jar;
        String s_mkdir;
        Process p_mkdir;
        List <String> computers_in_network = new ArrayList <String>();
        computers_in_network.add("c130-17");
        for (String computer : computers_in_network) {
        	
        try {
            p_mkdir = Runtime.getRuntime().exec("ssh ncluzel@c130-17 mkdir -p /tmp/ncluzel");
            BufferedReader br = new BufferedReader(
                new InputStreamReader(p_mkdir.getInputStream()));
            while ((s_mkdir = br.readLine()) != null)
                System.out.println("line: " + s_mkdir);
            p_mkdir.waitFor();
            System.out.println ("exit: " + p_mkdir.exitValue());
            //System.err.println();
            p_mkdir.destroy();
        } catch (Exception e) {}
        try {
            p_copy_jar = Runtime.getRuntime().exec("scp -r -p /tmp/ncluzel/slave.jar ncluzel@c130-17:/tmp/ncluzel/slave.jar");
            BufferedReader br = new BufferedReader(
                new InputStreamReader(p_copy_jar.getInputStream()));
            while ((s_copy_jar = br.readLine()) != null)
                System.out.println("line: " + s_copy_jar);
            p_copy_jar.waitFor();
            System.out.println ("exit: " + p_copy_jar.exitValue());
            //System.err.println();
            p_copy_jar.destroy();
        } catch (Exception e) {}
        }
	}
}
