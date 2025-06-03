import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CokeMachine implements CokeMachIF {

	private static int MAX = 10;
	private int count;
	
	private Lock lock;
	private Condition coda; 
	
	public CokeMachine() {
		this.count = MAX;
		this.lock = new ReentrantLock();
		this.coda = lock.newCondition();
	}
	@Override
	public void preleva() {
		// TODO Auto-generated method stub
		lock.lock();
		try {
			while(count == 0) {
				try {
					coda.await();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			count--;
			System.out.println(Thread.currentThread().getName() + " prelevato\tcount: " + count);
			if(count==0) {
				coda.signalAll();
				System.out.println(Thread.currentThread().getName() + " sveglio");
			}
		}
		finally { lock.unlock(); }
	}

	@Override
	public void rifornisci() {
		// TODO Auto-generated method stub
		lock.lock();
		try {
			while(count > 0) {
				try {
					coda.await();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			count = MAX;
			System.out.println(Thread.currentThread().getName() + " rifornito\tcount: " + count);
			coda.signalAll();
		}
		finally { lock.unlock(); }
	}

}
