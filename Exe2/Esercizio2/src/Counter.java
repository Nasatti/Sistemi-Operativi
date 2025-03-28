import java.util.concurrent.Semaphore;

public class Counter {
	private static final int MAX = 10;
	private static final int MIN = 0;
	
	private int c;
	private Semaphore mutex;
	private Semaphore inc;
	private Semaphore dec;
	
	public Counter() {
		this.c = MIN;
		this.mutex = new Semaphore(1);
		this.inc = new Semaphore(MAX);//solo incremento all'inizio visto che parte da 0
		this.dec = new Semaphore(0);
	}
	
	public void incrementa() {
		try {
			inc.acquire();
			mutex.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(c<MAX) {
			c++;
			System.out.println(Thread.currentThread().getName() + " incrementato a " + c);
		}
		mutex.release();
		dec.release();

	}
	
	public void decrementa() {
		try {
			dec.acquire();
			mutex.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(c>0) {
			c--;
			System.out.println(Thread.currentThread().getName() + " decrementato a " + c);
		}

		mutex.release();
		inc.release();

	}

}
