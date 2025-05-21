import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Bar implements BarIF {
	private final int NMAX = 8;//2 ne restano fuori
	private int clientiO; //N tifosi ospiti dentro al bar
	private int clientiL; //N tifosi locali dentro al bar
	//SAF-1: clientiO > 0 -> clientiL == 0 
	//SAF-2: clientiL > 0 -> clientiO == 0
	//SAF-3: clientiO + clientiL <= NMAX
	//PRE: i clienti ospiti hanno precedenza sui locali
	private boolean uscita;
	/*
	 * T --> Barista vuole chiudere
	 * F --> Barista vuole aprire
	 */
	private int sospesiO; //N tifosi ospiti in attesa di entrare
	
	//Per la sincronizzazione
	private Lock lock;
	private Condition codaClientiL, codaClientiO, codaBarista;
	
	//Inizio: bar aperto pulito e senza clienti 
	public Bar() {
		clientiO = clientiL = sospesiO = 0;
		uscita = false;
		lock = new ReentrantLock();
		codaClientiL = lock.newCondition();
		codaClientiO = lock.newCondition();
		codaBarista = lock.newCondition();
	}
	
	@Override
	public void entraO() {
		// TODO Auto-generated method stub
		lock.lock();
		try {
			while(clientiL > 0 || clientiO == NMAX || uscita) {
				try {
					sospesiO++;
					codaClientiO.await();
					sospesiO--;
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			//entra
			clientiO++;
			
		}
		finally {lock.unlock();}
	}

	@Override
	public void entral() {
		// TODO Auto-generated method stub
		lock.lock();
		try {
			while(clientiO > 0 || clientiL == NMAX || uscita || sospesiO > 0) {
				try {
					codaClientiL.await();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			//entra
			clientiO++;
		}
		finally {lock.unlock();}
	}

	@Override
	public void esceo() {
		// TODO Auto-generated method stub
		lock.lock();
		try {
			clientiO--;
			//sveglia gli altri thread in ordine 
			if(uscita) //Variante: ClientiO==0
				codaBarista.signal();
			else if(sospesiO > 0)
				codaClientiO.signalAll();
			else codaClientiL.signalAll();
		}
		finally {lock.unlock();}
	}

	@Override
	public void escel() {
		// TODO Auto-generated method stub
		lock.lock();
		try {
			clientiL--;
			//sveglia gli altri thread in ordine 
			if(uscita) //Variante: ClientiO==0
				codaBarista.signal();
			else if(sospesiO > 0)
				codaClientiO.signalAll();
			else codaClientiL.signalAll();
		}
		finally {lock.unlock();}
	}

	@Override
	public void inizio_chiusura() {
		// TODO Auto-generated method stub
		lock.lock();
		try {
			//manifesta agli altri la volontà di chiudere
			uscita = true;
			while(clientiO + clientiL > 0) {
				try {
					codaBarista.await();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}
		finally {lock.unlock();}
	}

	@Override
	public void fine_chiusura() {
		// TODO Auto-generated method stub
		lock.lock();
		try {
			uscita = false;
			System.out.println(Thread.currentThread().getName() + ": bar di nuovo aperto");
			//sveglia clienti ospiti --> schema di priorità
			if(sospesiO > 0)
				codaClientiO.signalAll();
			else codaClientiL.signalAll();
		}
		finally {lock.unlock();}
	}

}
