import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Counter implements CounterIF {
	
	private static int MAX = 10;
	private static int MIN = 0;
	private int count;

	private Lock lock;
	private Condition codaI;
	private Condition codaD;
	
	public Counter() {
		this.count = MIN;
		lock = new ReentrantLock();
		codaI = lock.newCondition();
		codaD = lock.newCondition();
	}
	
	@Override
	public void incermenta() {
		// TODO Auto-generated method stub
		lock.lock();
		try {
			while(count == MAX) {
				try {
					System.out.println("I");
					codaI.await();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			count++;
			System.out.println(Thread.currentThread().getName() + " inrementato\t\tcount: " + count);
			if(count == MAX) {
				codaD.signalAll();
				System.out.println(Thread.currentThread().getName() + " chiamoD\t\tcount: " + count);
			}
		}
		finally { lock.unlock(); }
	}

	@Override
	public void decrementa() {
		// TODO Auto-generated method stub
		lock.lock();
		try {
			while(count == MIN) {
				try {
					System.out.println("D");
					codaD.await();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			count--;
			System.out.println(Thread.currentThread().getName() + " decrementato\tcount: " + count);
			if(count == MIN) {
				codaI.signalAll();
				System.out.println(Thread.currentThread().getName() + " chiamoI\t\tcount: " + count);
			}
		}
		finally { lock.unlock(); }
	}

}
