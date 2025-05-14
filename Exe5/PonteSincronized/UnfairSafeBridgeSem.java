public class UnfairSafeBridgeSem implements Bridge {

	public static final int CT = 3;
	private int nblue; 				// n. di auto blu in transito in un dato istante sul ponte
	private int nred;  				// n. di auto rosse in transito in un dato istante sul ponte
	
	
	public UnfairSafeBridgeSem() {
		// Assumiamo che inizialmente il ponte sia sgombro e non ci sono auto in attesa di entrare
		nblue = 0;
		nred = 0;		
	}

	@Override
	public synchronized void redEnter() {
		/*
		 * while (condizione attesa){
		 *	notify/All
		 *	wait()
		 *}
		 * 
		 * sezione critica
		 * notify/All
		 */
		while(nblue+nred > 0) {
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		nred++;
		System.out.println(Thread.currentThread().getName() + " sta passando, nred=" + nred +", nblue=" + nblue);
	}

	@Override
	public synchronized void redExit() {
		nred--;
		System.out.println(Thread.currentThread().getName() + " sta uscendo, nred=" + nred +", nblue=" + nblue);
		notify();//ne avverte uno che può passare
	}

	@Override
	public synchronized void blueEnter() {
		while(nblue+nred > 0) {
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		nblue++;
		System.out.println(Thread.currentThread().getName() + " sta passando, nred=" + nred +", nblue=" + nblue);
	}

	@Override
	public synchronized void blueExit() {
		nblue--;
		System.out.println(Thread.currentThread().getName() + " sta uscendo, nred=" + nred +", nblue=" + nblue);
		notify();//ne avverte uno che può passare
	}
}
