import java.util.concurrent.Semaphore;

public class UnfairSafeBridgeSem implements Bridge {

	private int nblue; 				// n. di auto blu in transito in un dato istante sul ponte
	private int nred;  				// n. di auto rosse in transito in un dato istante sul ponte
	private int nb_waiting; 		// n. di auto blu in attesa di entrare nel ponte
	private int nr_waiting; 		// n. di auto rosse in attesa di entrare nel ponte
	
	private Semaphore mutex;		// semaforo di mutua esclusione
	private Semaphore rturn;		// semaforo per far attendere il turno delle auto rosse
	private Semaphore bturn;		// semaforo per far attendere il turno delle auto blu
	
	public UnfairSafeBridgeSem() {
		nred = nblue = nb_waiting = nr_waiting = 0;
		mutex = new Semaphore(1);
		rturn = new Semaphore(0, true);//risorsa non disponibile
		bturn = new Semaphore(0, true);//con true verrà seguita una schedulazione FIFO nella coda del semaforo
	}

	@Override
	public void redEnter() {
		try {
			mutex.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(nred + nblue == 0) {//ponte sgombro
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
		if(nr_waiting > 0) {//ci sono auto rosse in attesa
			rturn.release();
			nr_waiting--;
			nred++;
			System.out.println("un'altra macchina rossa passerà sul ponte | nred: " + nred + " | nblue: " + nblue + " | nr_waiting: " + nr_waiting + " | nb_waiting: " + nb_waiting);
		}
		else if(nb_waiting > 0) {//ci sono auto blu in coda
			bturn.release();
			nb_waiting--;
			nblue++;
			try {
				mutex.acquire();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			nred--;
			System.out.println(Thread.currentThread().getId() + " Macchina rossa sta uscendo | nred: " + nred + " | nblue: " + nblue + " | nr_waiting: " + nr_waiting + " | nb_waiting: " + nb_waiting);
			if(nr_waiting > 0) {//ci sono auto rosse in attesa
				rturn.release();
				nr_waiting--;
				nred++;
				System.out.println("una macchina blu passerà sul ponte | nred: " + nred + " | nblue: " + nblue + " | nr_waiting: " + nr_waiting + " | nb_waiting: " + nb_waiting);
			}
			mutex.release();
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
		if(nred + nblue == 0) {//ponte sgombro
			nblue++;
			bturn.release();
			System.out.println(Thread.currentThread().getId() + " Macchina blue passata sul ponte | nred: " + nred + " | nblue: " + nblue + " | nr_waiting: " + nr_waiting + " | nb_waiting: " + nb_waiting);
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
		if(nb_waiting > 0) {//ci sono auto rosse in attesa
			bturn.release();
			nb_waiting--;
			nblue++;
			System.out.println("un'altra macchina blu passerà sul ponte | nred: " + nred + " | nblue: " + nblue + " | nr_waiting: " + nr_waiting + " | nb_waiting: " + nb_waiting);
		}
		else if(nr_waiting > 0) {//ci sono auto blu in coda
			rturn.release();
			nr_waiting--;
			nred++;
			try {
				mutex.acquire();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			nblue--;
			System.out.println(Thread.currentThread().getId() + " Macchina rossa sta uscendo | nred: " + nred + " | nblue: " + nblue + " | nr_waiting: " + nr_waiting + " | nb_waiting: " + nb_waiting);
			if(nb_waiting > 0) {//ci sono auto rosse in attesa
				bturn.release();
				nb_waiting--;
				nblue++;
				System.out.println("una macchina rossa passerà sul ponte | nred: " + nred + " | nblue: " + nblue + " | nr_waiting: " + nr_waiting + " | nb_waiting: " + nb_waiting);
			}
			mutex.release();
		}
		mutex.release();
	}
}
