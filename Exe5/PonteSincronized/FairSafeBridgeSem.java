public class FairSafeBridgeSem implements Bridge {

	private int nblue; 				// n. di auto blu in transito in un dato istante sul ponte
	private int nred;  				// n. di auto rosse in transito in un dato istante sul ponte
	private int nb_waiting; 		// n. di auto blu in attesa di entrare nel ponte
	private int nr_waiting; 		// n. di auto rosse in attesa di entrare nel ponte
	private boolean bturn;
	
	public FairSafeBridgeSem() {
		// Assumiamo che inizialmente il ponte sia sgombro e non ci sono auto in attesa di entrare
		nblue = 0;
		nred = 0;		
		nb_waiting = 0;
		nr_waiting = 0; 
		bturn = false;
	}

	@Override
	public synchronized void redEnter() {
		nr_waiting++;
		while(nblue+nred > 0 || (nb_waiting > 0 && bturn)) {
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		nred++;
		nr_waiting--;
		System.out.println(Thread.currentThread().getName() + " sta passando, nred=" + nred +", nblue=" + nblue);
	}

	@Override
	public synchronized void redExit() {
		nred--;
		System.out.println(Thread.currentThread().getName() + " sta uscendo, nred=" + nred +", nblue=" + nblue);
		if(nred == 0) {
			bturn = true;
			notifyAll();
		}
	}

	@Override
	public synchronized void blueEnter() {
		nb_waiting++;
		while(nblue+nred > 0 || (nr_waiting > 0 && !bturn)){
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		nblue++;
		nb_waiting--;
		System.out.println(Thread.currentThread().getName() + " sta passando, nred=" + nred +", nblue=" + nblue);
	}

	@Override
	public synchronized void blueExit() {
		nblue--;
		System.out.println(Thread.currentThread().getName() + " sta uscendo, nred=" + nred +", nblue=" + nblue);
		if(nblue == 0) {
			bturn = false;
			notifyAll();
		}
	}
}
