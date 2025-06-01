import java.util.concurrent.Semaphore;

public class Bridge implements BridgeIF {

	private int codaR;
	private int codaB;
	private int r;
	private int b;
	private int count;
	
	private Semaphore mutex;
	private Semaphore red;
	private Semaphore blu;
	
	public Bridge() {
		// TODO Auto-generated method stub
		count = r = b = codaR = codaB = 0;
		mutex = new Semaphore(1);
		red = new Semaphore(0);
		blu = new Semaphore(0);
	}
	
	@Override
	public void redEnter() {
		// TODO Auto-generated method stub
		try {
			mutex.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if((b + codaB == 0) && r == 0) {//ponte sgombro
			r++;
			count++;
			red.release();
			System.out.println(Thread.currentThread().getName() + " passata\tnred: " + r + "\tnblue: " + b + "\tnr_waiting: " + codaR + "\tnb_waiting: " + codaB+"\tcount: " + count);
		}
		else{
			codaR++;
			System.out.println(Thread.currentThread().getName() + " coda\tnred: " + r + "\tnblue: " + b + "\tnr_waiting: " + codaR + "\tnb_waiting: " + codaB+"\tcount: " + count);
		}
		mutex.release();
		try {
			red.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	@Override
	public void redExit() {
		// TODO Auto-generated method stub
		try {
			mutex.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("check\tnred: " + r + "\tnblue: " + b + "\tnr_waiting: " + codaR + "\tnb_waiting: " + codaB+"\tcount: " + count);
		r--;
		if((r == 0 && codaR == 0) || count == 3) {
			blu.release();
			b++;
			count=0;
			codaB--;
			System.out.println("una XB passata\tnred: " + r + "\tnblue: " + b + "\tnr_waiting: " + codaR + "\tnb_waiting: " + codaB+"\tcount: " + count);
		}
		else if(count < 3 && r == 0 && codaR > 0) {
			red.release();
			r++;
			count++;
			codaR--;
			System.out.println("una XR passata\tnred: " + r + "\tnblue: " + b + "\tnr_waiting: " + codaR + "\tnb_waiting: " + codaB+"\tcount: " + count);
		}
		mutex.release();
	}

	@Override
	public void bluEnter() {
		// TODO Auto-generated method stub
		try {
			mutex.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if((r + codaR == 0) && b == 0) {//ponte sgombro
			b++;
			count++;
			blu.release();
			System.out.println(Thread.currentThread().getName() + " passata\tnred: " + r + "\tnblue: " + b + "\tnr_waiting: " + codaR + "\tnb_waiting: " + codaB+"\tcount: " + count);
		}
		else{
			codaB++;
			System.out.println(Thread.currentThread().getName() + " coda\tnred: " + r + "\tnblue: " + b + "\tnr_waiting: " + codaR + "\tnb_waiting: " + codaB+"\tcount: " + count);
		}
		mutex.release();
		try {
			blu.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void bluExit() {
		// TODO Auto-generated method stub
		try {
			mutex.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		b--;
		System.out.println("check\tnred: " + r + "\tnblue: " + b + "\tnr_waiting: " + codaR + "\tnb_waiting: " + codaB+"\tcount: " + count);
		if((b == 0 && codaB == 0) || count == 3) {
			red.release();
			r++;
			count = 0;
			codaR--;
			System.out.println("una XR passata\tnred: " + r + "\tnblue: " + b + "\tnr_waiting: " + codaR + "\tnb_waiting: " + codaB+"\tcount: " + count);
		}
		else if(count < 3 && b == 0 && codaB > 0) {
			blu.release();
			b++;
			count++;
			codaB--;
			System.out.println("una XB passata\tnred: " + r + "\tnblue: " + b + "\tnr_waiting: " + codaR + "\tnb_waiting: " + codaB+"\tcount: " + count);
		}
		mutex.release();
	}

}
