
public class Esercizio4 {
	
	public static void main(String[] args) throws InterruptedException {
		Thread t1 = new Worker();
		t1.start();
		System.out.println("Aspetto che figlio termini");
		try { t1.join(); } catch (InterruptedException ie) { }
	}
}
