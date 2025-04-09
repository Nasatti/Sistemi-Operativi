import java.util.concurrent.Semaphore;

public class FairSafeBridgeSem implements Bridge {

	private int nblue; 				// n. di auto blu in transito in un dato istante sul ponte
	private int nred;  				// n. di auto rosse in transito in un dato istante sul ponte
	private int nb_waiting; 		// n. di auto blu in attesa di entrare nel ponte
	private int nr_waiting; 		// n. di auto rosse in attesa di entrare nel ponte
	
	private Semaphore mutex;		// semaforo di mutua esclusione
	private Semaphore rturn;		// semaforo per il turno delle auto rosse
	private Semaphore bturn;		// semaforo il turno delle auto blu
	
	public FairSafeBridgeSem() {
		// Assumiamo che inizialmente il ponte sia sgombro e non ci sono auto in attesa di entrare
		nblue = 0;
		nred = 0;		
		nb_waiting = 0;
		nr_waiting = 0;
		mutex = new Semaphore(1);		// 1 risorsa libera
		rturn = new Semaphore(0, true);	// sbarra abbassata; flag fairness del semaforo
		bturn = new Semaphore(0, true);	// sbarra abbassata; 
	}

	@Override
	public void redEnter() {
		try {
			mutex.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// sezione critica
		if (nred == 0 && (nblue + nb_waiting == 0)) {	// se il ponte è sgombro, l'auto passa
			nred++;
			rturn.release(); 		// alziamo la sbarra
			System.out.println(Thread.currentThread().getName() + " sta passando; nred: " +
					nred + " nblue: " + nblue + 
					" nr_waiting: " + nr_waiting + " nb_waiting: " + nb_waiting);
		}
		else	// l'auto attende di passare
			nr_waiting++;
		mutex.release();
		try {
			rturn.acquire();	// bloccante solo per le auto rosse che devono attendere
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void redExit() {
		try {
			mutex.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		nred--;
		if (nb_waiting == 0 && nr_waiting > 0) {	// ci sono auto rosse in attesa di entrare sul ponte
			rturn.release(); 	// sveglia un'auto rossa in attesa
			nred++;
			nr_waiting--;			
		}
		else if (nb_waiting > 0 && nred == 0) {		// se ci sono auto blu in attesa
			bturn.release();
			nblue++;
			nb_waiting--;
		}
		System.out.println(Thread.currentThread().getName() + " sta uscendo; nred: " +
				nred + " nblue: " + nblue + 
				" nr_waiting: " + nr_waiting + " nb_waiting: " + nb_waiting);
		mutex.release();
	}

	@Override
	public void blueEnter() {
		try {
			mutex.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// sezione critica
		if ((nred + nr_waiting == 0) && nblue == 0) {	// se il ponte è sgombro, l'auto passa
			nblue++;
			bturn.release(); 		// alziamo la sbarra
			System.out.println(Thread.currentThread().getName() + " sta passando; nred: " +
					nred + " nblue: " + nblue + 
					" nr_waiting: " + nr_waiting + " nb_waiting: " + nb_waiting);
		}
		else	// l'auto attende di passare
			nb_waiting++;
		mutex.release();
		try {
			bturn.acquire();	// bloccante solo per le auto blu che devono attendere
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void blueExit() {
		try {
			mutex.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		nblue--;
		if (nr_waiting == 0 && nb_waiting > 0) {	// ci sono auto blu in attesa di entrare sul ponte
			bturn.release(); 	// sveglia un'auto blu in attesa
			nblue++;
			nb_waiting--;			
		}
		else if (nr_waiting > 0 && nblue == 0) {		// se ci sono auto rosse in attesa
			rturn.release();
			nred++;
			nr_waiting--;
		}
		System.out.println(Thread.currentThread().getName() + " sta uscendo; nred: " +
				nred + " nblue: " + nblue + 
				" nr_waiting: " + nr_waiting + " nb_waiting: " + nb_waiting);
		mutex.release();
	}
}
