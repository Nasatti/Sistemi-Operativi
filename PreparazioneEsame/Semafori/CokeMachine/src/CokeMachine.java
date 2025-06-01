import java.util.concurrent.Semaphore;

public class CokeMachine implements CokeMachIF {

	private static int MAX = 10;
	private int count;
	
	private Semaphore mutex;
	private Semaphore empty;
	
	/**
	 * empty = 0--> macchinetta piena
	 */
	public CokeMachine() {
		this.count = this.MAX;
		this.mutex = new Semaphore(1);
		this.empty = new Semaphore(0);
	}
	
	@Override
	public void preleva() {
		// TODO Auto-generated method stub
		try {
			mutex.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		--count;
		System.out.println(Thread.currentThread().getName() + " prelevato lattina\tcount: " + count);
		if(count == 0) {
			empty.release();
			System.out.println(Thread.currentThread().getName() + " chiamo rifornitore");
		}
		mutex.release();
	}

	@Override
	public void rifornisci() {
		// TODO Auto-generated method stub
		try {
			empty.acquire();
			mutex.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		count = MAX;
		System.out.println(Thread.currentThread().getName() + " rifornito\tcount: " + count);
		mutex.release();
	}

}
