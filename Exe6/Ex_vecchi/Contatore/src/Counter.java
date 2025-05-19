import java.util.concurrent.locks.*;

public class Counter {
	private static final int MAX = 10;
	private static final int MIN = 0;
	
	private int c;
	Lock lock = new ReentrantLock();
	Condition rmin = lock.newCondition();
	Condition rmax = lock.newCondition();
	
	public Counter() {
		this.c = MIN;
	}
	
	public void incrementa() {
		lock.lock();
		try {
			while(c == 10) {
				try {
					rmax.await();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			c++;
			System.out.println(Thread.currentThread().getName() + " aggiunto	|	count:" + c);
			if(c == 10) {
				System.out.println(Thread.currentThread().getName() + " arrivato a 10, sveglio un decrementa");
				rmin.signal();
			}
		}
		finally {
	    	System.out.println("releasing lock");  
	        lock.unlock();
		}
	}
	
	public void decrementa() {
		lock.lock();
		try {
			while(c == 0) {
				try {
					rmin.await();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			c--;
			System.out.println(Thread.currentThread().getName() + " rimosso	|	count:" + c);
			if(c == 0) {
				System.out.println(Thread.currentThread().getName() + " arrivato a 0, sveglio un incrementa");
				rmax.signal();
			}
		}
		finally {
	    	System.out.println("releasing lock");  
	        lock.unlock();
		}
	}

}
