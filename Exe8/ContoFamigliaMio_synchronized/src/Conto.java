
public class Conto implements ContoIF {

	private static final int N=10000;
	private int deposito;


	public Conto() {
		this.deposito = N;

	}

	@Override
	public synchronized void aggiungiDenaro(int somma) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public synchronized void prelevoDenaro(int somma) {
		// TODO Auto-generated method stub
		while(deposito < somma) {
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		deposito -= somma;
		
		System.out.println(Thread.currentThread().getName() + ": prelevato " + somma + " deposito: " + deposito);
		
		if(deposito < somma) {
			notify();
			System.out.println(Thread.currentThread().getName() + " Sveglio padre");
		}
	}

}
