import java.util.concurrent.Semaphore;

public class Counter implements CounterIF {

	private static int MAX = 10;
	private static int MIN = 0;
	private int count;
	
	private Semaphore mutex;
	private Semaphore inc;
	private Semaphore dec;
	
	/**
	 * inc = 0 && dec = 10 --> inizialmente counter vuoto, cioè 0
	 */
	public Counter() {
		this.count = MIN;
		this.mutex = new Semaphore(1);
		this.inc = new Semaphore(MAX);
		this.dec = new Semaphore(MIN);
	}
	
	@Override
	public void incermenta() {
		// TODO Auto-generated method stub
		try {
			inc.acquire();
			mutex.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(count < MAX) {
			++count;
			System.out.println(Thread.currentThread().getName() + " incrementato\tcounter: " + count);
		}
		mutex.release();
		dec.release();
	}

	@Override
	public void decrementa() {
		// TODO Auto-generated method stub
		try {
			dec.acquire();
			mutex.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(count > MIN) {
			--count;
			System.out.println(Thread.currentThread().getName() + " decremento\t\tcounter: " + count);
		}
		mutex.release();
		inc.release();
	}

}
