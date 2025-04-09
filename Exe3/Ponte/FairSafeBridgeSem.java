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
		if((nblue + nb_waiting == 0) && nred == 0) {//ponte sgombro
			nred++;
			rturn.release();
			System.out.println(Thread.currentThread().getId() + " macchina rossa passata sul ponte | nred: " + nred + " | nblue: " + nblue + " | nr_waiting: " + nr_waiting + " | nb_waiting: " + nb_waiting);
		}
		else nr_waiting++;
		mutex.release();
		try {
			rturn.acquire();
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
		System.out.println(Thread.currentThread().getId() + " Macchina rossa sta uscendo | nred: " + nred + " | nblue: " + nblue + " | nr_waiting: " + nr_waiting + " | nb_waiting: " + nb_waiting);
		if(nred == 0 && nb_waiting > 0) {//non ci sono in transito auto rosse e in coda auto blu in coda
			bturn.release();
			nblue++;
			nb_waiting--;
			System.out.println("una macchina blu passerà sul ponte | nred: " + nred + " | nblue: " + nblue + " | nr_waiting: " + nr_waiting + " | nb_waiting: " + nb_waiting);
		}
		else if(nb_waiting == 0 && nr_waiting > 0) {//auto rosse in attesa ne sveglio una
			rturn.release();
			nred++;
			nr_waiting--;
			System.out.println("una macchina rossa passerà sul ponte | nred: " + nred + " | nblue: " + nblue + " | nr_waiting: " + nr_waiting + " | nb_waiting: " + nb_waiting);
		}
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
		if((nred + nr_waiting == 0) && nblue == 0) {//ponte sgombro
			nblue++;
			bturn.release();
			System.out.println(Thread.currentThread().getId() + " macchina blue passata sul ponte | nred: " + nred + " | nblue: " + nblue + " | nr_waiting: " + nr_waiting + " | nb_waiting: " + nb_waiting);
		}
		else nb_waiting++;
		mutex.release();
		try {
			bturn.acquire();
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
		System.out.println(Thread.currentThread().getId() + " Macchina blu sta uscendo | nred: " + nred + " | nblue: " + nblue + " | nr_waiting: " + nr_waiting + " | nb_waiting: " + nb_waiting);
		if(nblue == 0 && nr_waiting > 0) {//non ci sono in transito auto rosse e in coda auto blu in coda
			rturn.release();
			nred++;
			nr_waiting--;
			System.out.println("una macchina rossa passerà sul ponte | nred: " + nred + " | nblue: " + nblue + " | nr_waiting: " + nr_waiting + " | nb_waiting: " + nb_waiting);
		}
		else if(nr_waiting == 0 && nb_waiting > 0) {//auto rosse in attesa ne sveglio una
			bturn.release();
			nblue++;
			nb_waiting--;
			System.out.println("una macchina blue passerà sul ponte | nred: " + nred + " | nblue: " + nblue + " | nr_waiting: " + nr_waiting + " | nb_waiting: " + nb_waiting);
		}
		mutex.release();
	}
}
