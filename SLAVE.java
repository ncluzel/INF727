
public class SLAVE {

	public static void main(String[] args) {
		int a = 3;
		int b = 5;
		int c = 0;
		c = a + b;
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(a + " + " + b +" = " + c);
		//System.err.println(a + " + " + b +" = " + c);
	}
	
}
