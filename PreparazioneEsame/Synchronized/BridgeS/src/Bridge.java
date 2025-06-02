
public class Bridge implements BridgeIF {

	private int r;
	private int b;
	private int count;
	private int codaR;
	private int codaB;
	private boolean turn;
	
	
	/**
	 * turn:
	 * true  --> red
	 * false --> blue
	 */
	public Bridge() {
		r = b = count = codaR = codaB = 0;
	}
	
	@Override
	public synchronized void redEnter() {
		// TODO Auto-generated method stub
		codaR++;
		while(!turn || (r + b == 1)) {
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		codaR--;
		r++;
		count++;
		System.out.println(Thread.currentThread().getName() + " passato\tred: " + r + "\tblue: " + b + "\tcodared: " + codaR + "\tcodablue: " + codaB + "\tcount: " + count + "\tturn: " + turn);
	}

	@Override
	public synchronized void redExit() {
		// TODO Auto-generated method stub
		r--;
		if(count == 5 || codaR == 0) {
			turn = false;
			count = 0;
		}
		System.out.println(Thread.currentThread().getName() + " uscito\tred: " + r + "\tblue: " + b + "\tcodared: " + codaR + "\tcodablue: " + codaB + "\tcount: " + count + "\tturn: " + turn);
		notify();
	}

	@Override
	public synchronized void bluEnter() {
		// TODO Auto-generated method stub
		codaB++;
		while(turn || (r + b == 1)) {
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		codaB--;
		b++;
		count++;
		System.out.println(Thread.currentThread().getName() + " passato\tred: " + r + "\tblue: " + b + "\tcodared: " + codaR + "\tcodablue: " + codaB + "\tcount: " + count + "\tturn: " + turn);
	}

	@Override
	public synchronized void bluExit() {
		// TODO Auto-generated method stub
		b--;
		if(count == 5 || codaB == 0) {
			turn = true;
			count = 0;
		}
		System.out.println(Thread.currentThread().getName() + " uscito\tred: " + r + "\tblue: " + b + "\tcodared: " + codaR + "\tcodablue: " + codaB + "\tcount: " + count + "\tturn: " + turn);
		notify();
	}

}
