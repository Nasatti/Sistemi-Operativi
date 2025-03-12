
public class Esercizio3 {
	
	public static void main(String[] args) throws InterruptedException {
		Thread t1 = new Worker();
		int i = 1;
		t1.start();
		while(true) {
			System.out.println("Controllo se figlio ancora attivo, tentativo " + i);
			Thread.sleep(1000);
			if(!t1.isAlive()) return;
			i++;
		}
	}
}
