public class Counter {
	private static final int MAX = 10;
	private static final int MIN = 0;
	
	private int c;
	
	public Counter() {
		this.c = MIN;

	}
	
	public synchronized void incrementa() {
		while(c == MAX) {
			try {
				System.out.println(Thread.currentThread().getName() + " Aspetta");
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		c +=1;
		System.out.println(Thread.currentThread().getName() + " incrementato a " + c);
	}
	
	public synchronized void decrementa() {
		
		while(c == MIN) {
			try {
				System.out.println(Thread.currentThread().getName() + " Aspetta");
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		c-=1;
		System.out.println(Thread.currentThread().getName() + " decrementato a " + c);
	}

}
