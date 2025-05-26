import java.util.concurrent.Semaphore;

public class Conto implements ContoIF {

	private static final int N=10000;
	private int deposito;
	
	Semaphore mutex;
	Semaphore full;
	Semaphore empty;

	public Conto() {
		this.deposito = N;
		this.mutex = new Semaphore(1);
		this.full = new Semaphore(0);
		this.empty = new Semaphore(N);
	}

	@Override
	public void aggiungiDenaro(int somma) {
		// TODO Auto-generated method stub
		try {
			full.acquire();
			mutex.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//SezioneCritica
		deposito += somma;
		System.out.println(Thread.currentThread().getName() + ": aggiunto denaro depotiso: " + deposito);
		
		empty.release(somma);
		mutex.release();
	}

	@Override
	public void prelevoDenaro(int somma) {
		// TODO Auto-generated method stub
		try {
			empty.acquire(somma);
			mutex.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//SezioneCritica
		deposito -= somma;
		System.out.println(Thread.currentThread().getName() + ": prelevato " + somma + " deposito: " + deposito);
		
		if(deposito < somma)
			full.release();
		
		mutex.release();
	}

}
