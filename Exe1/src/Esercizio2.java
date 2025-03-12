public class Esercizio2 {

	public static void main(String[] args) throws InterruptedException {
		System.out.println(Thread.currentThread().getName()+": Ciao mondo");
		Thread t1 = new Worker();
		Thread t2 = new Worker();
		Thread t3 = new Thread(new Worker2(), "A");
		Thread t4 = new Thread(new Worker2(), "B");
		
		t1.setName("A");
		t2.setName("B");
		
		Thread.sleep(1000);
		
		t1.start();
		t2.start();
		
		Thread.sleep(1000);
		
		t3.start();
		t4.start();
	}

}
