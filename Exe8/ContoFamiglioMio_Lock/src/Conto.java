import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Conto implements ContoIF {

	private static final int N=10000;
	private int deposito;
	
	private Lock lock;
	private Condition codaFigli;
	private Condition codaPadre;


	public Conto() {
		this.deposito = N;
		this.lock = new ReentrantLock();
		this.codaFigli = lock.newCondition();
		this.codaPadre = lock.newCondition();
	}

	@Override
	public void aggiungiDenaro(int somma) {
		// TODO Auto-generated method stub
		lock.lock();
		try {
			codaPadre.await();
			deposito += somma;
			System.out.println(Thread.currentThread().getName() + ": aggiunto " + somma + " deposito: " + deposito);
			codaFigli.signalAll();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {lock.unlock();}
	}

	@Override
	public void prelevoDenaro(int somma) {
		// TODO Auto-generated method stub
		lock.lock();
		try {
			while(deposito < somma) {
				try {
					codaFigli.await();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			deposito -= somma;
			System.out.println(Thread.currentThread().getName() + ": prelevato " + somma + " deposito: " + deposito);
		
			if(deposito < somma) {
				codaPadre.signal();
				System.out.println(Thread.currentThread().getName() + " Sveglio padre");
			}
		}
		finally {lock.unlock();}
	}

}
