import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Bar implements BarIF {

	private static int MAX = 10;
	private int countL;
	private int countO;
	private int countCodaL;
	private int countCodaO;
	private boolean chiusura;
	
	private Lock lock;
	private Condition codaL;
	private Condition codaO;
	private Condition codaB;
	
	public Bar() {
		countL = countO = countCodaL = countCodaO = 0;
		chiusura = false;
		lock = new ReentrantLock();
		codaL = lock.newCondition();
		codaO = lock.newCondition();
		codaB = lock.newCondition();
	}
	@Override
	public void entraO() {
		// TODO Auto-generated method stub
		lock.lock();
		countCodaO++;
		try {
			while(chiusura || countL > 0 || countO == MAX) {
				try {
					codaO.await();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			countCodaO--;
			countO++;
			System.out.println(Thread.currentThread().getName() + " entro\tcountL: " + countL + "\tcountO: " + countO + "\tcoutCodaL: " + countCodaL + "\tcount§CodaO: " + countCodaO + "\tchiusura: " + chiusura);
		}
		finally { lock.unlock(); }
	}

	@Override
	public void esceO() {
		// TODO Auto-generated method stub
		lock.lock();
		try {
			countO--;
			System.out.println(Thread.currentThread().getName() + " esco\tcountL: " + countL + "\tcountO: " + countO + "\tcoutCodaL: " + countCodaL + "\tcount§CodaO: " + countCodaO + "\tchiusura: " + chiusura);
			if(!chiusura && (countL == 0)) {
				codaO.signalAll();
			}
			else if(!chiusura && (countO + countCodaO == 0))
				codaL.signalAll();
			else if(chiusura && countL + countO == 0) 
				codaB.signal();
		}
		finally { lock.unlock(); }
	}

	@Override
	public void entraL() {
		// TODO Auto-generated method stub
		lock.lock();
		countCodaL++;
		try {
			while(chiusura || countO > 0 || countL == MAX || (countO + countL == 0 && countCodaO > 0)) {
				try {
					codaO.await();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			countCodaL--;
			countL++;
			System.out.println(Thread.currentThread().getName() + " entro\tcountL: " + countL + "\tcountO: " + countO + "\tcoutCodaL: " + countCodaL + "\tcount§CodaO: " + countCodaO + "\tchiusura: " + chiusura);
		}
		finally { lock.unlock(); }
	}

	@Override
	public void esceL() {
		// TODO Auto-generated method stub
		lock.lock();
		try {
			countL--;
			System.out.println(Thread.currentThread().getName() + " esco\tcountL: " + countL + "\tcountO: " + countO + "\tcoutCodaL: " + countCodaL + "\tcount§CodaO: " + countCodaO + "\tchiusura: " + chiusura);
			if(!chiusura && (countL == 0)) {
				codaO.signalAll();
			}
			else if(!chiusura && (countO + countCodaO == 0))
				codaL.signalAll();
			else if(chiusura && countL + countO == 0) 
				codaB.signal();
		}
		finally { lock.unlock(); }
	}

	@Override
	public void chiusura() {
		// TODO Auto-generated method stub
		lock.lock();
		try {
			chiusura = true;
			System.out.println(Thread.currentThread().getName() + " voglio chiudere\tcountL: " + countL + "\tcountO: " + countO + "\tcoutCodaL: " + countCodaL + "\tcount§CodaO: " + countCodaO + "\tchiusura: " + chiusura);
			
			while(countL > 0 || countO > 0) {
				try {
					codaB.await();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			System.out.println(Thread.currentThread().getName() + " pulisco\tcountL: " + countL + "\tcountO: " + countO + "\tcoutCodaL: " + countCodaL + "\tcount§CodaO: " + countCodaO + "\tchiusura: " + chiusura);
		}
		finally { lock.unlock(); }
	}

	@Override
	public void apertura() {
		// TODO Auto-generated method stub
		lock.lock();
		try {
			System.out.println(Thread.currentThread().getName() + " apro\tcountL: " + countL + "\tcountO: " + countO + "\tcoutCodaL: " + countCodaL + "\tcount§CodaO: " + countCodaO + "\tchiusura: " + chiusura);
			chiusura = false;
			if(countCodaO > 0)
				codaO.signalAll();
			else codaL.signalAll();
		}
		finally { lock.unlock(); }
	}

}
