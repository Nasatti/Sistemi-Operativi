
public class CokeMachine implements CokeMachIF {

	private static int MAX = 10;
	private int count;
	
	
	public CokeMachine() {
		super();
		this.count = MAX;
	}

	@Override
	public synchronized void preleva() {
		// TODO Auto-generated method stub
		while(count == 0) {
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		count--;
		System.out.println(Thread.currentThread().getName()+" prelevato\tcount: " + count);
		if(count == 0) {
			notifyAll();
		}
	}

	@Override
	public synchronized void rifornisci() {
		// TODO Auto-generated method stub
		while(count > 0) {
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		count = MAX;
		System.out.println(Thread.currentThread().getName()+" caricato\tcount: " + count);
		notifyAll();
	}

}
