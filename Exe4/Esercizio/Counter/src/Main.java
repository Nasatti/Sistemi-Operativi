
public class Main {

	public static void main(String[] args) {
		final int nThread = 10;
		
		Counter c = new Counter();
		Thread[] I = new Thread[nThread];
		Thread[] D = new Thread[nThread];
		
		for(int i = 0; i < nThread ; i++) {
			I[i] = new TaskI(c,i);
			I[i].start();
			D[i] = new TaskD(c,i);
			D[i].start();
		}

	}

}
