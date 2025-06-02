
public class Counter implements CounterIF {

	private static int MAX = 10;
	private static int MIN = 0;
	
	private int count;
	
	public Counter() {
		count = MIN;
	}
	
	@Override
	public synchronized void incermenta() {
		// TODO Auto-generated method stub
		while(count == MAX) {
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		count++;
		System.out.println(Thread.currentThread().getName() + " incrementato\tcount: " + count);
		notifyAll();
	}

	@Override
	public synchronized void decrementa() {
		// TODO Auto-generated method stub
		while(count == MIN) {
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		count--;
		System.out.println(Thread.currentThread().getName() + " decrementato\tcount: " + count);
		notifyAll();
	}

}
