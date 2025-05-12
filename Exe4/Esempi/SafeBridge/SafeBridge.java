//Soluzione con sincronizzazione (in)diretta
//Capacità di transito sul ponte è pari a 1
public class SafeBridge implements Bridge {
	private int nred; //auto rosse in transito sul ponte
	private int nblue; //auto blu in transito sul ponte
	
	public SafeBridge() {
		//All'inizio il ponte è sgombro
		nred = 0;
		nblue = 0;
	}
	
	@Override
	public synchronized void redEnter() {
		while (nblue + nred > 0 ) //ponte non libero
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        //sezione critica: stiamo transitando sul ponte!
		nred++;
		System.out.println(Thread.currentThread().getName() + " sta passando | nred: " +
				nred + " nblue: " + nblue);	
	}

	@Override
	public synchronized void redExit() {
		nred--;
		System.out.println(Thread.currentThread().getName() + " sta uscendo | nred: " +
				nred + " nblue: " + nblue);
	    notify(); //capacità transito pari a 1
	}

	@Override
	public synchronized void blueEnter() {
		while (nblue + nred > 0 ) //ponte non libero
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        //sezione critica: stiamo transitando sul ponte!
		nblue++;
		System.out.println(Thread.currentThread().getName() + " sta passando | nred: " +
				nred + " nblue: " + nblue);	

	}

	@Override
	public synchronized void blueExit() {
		nblue--;
		System.out.println(Thread.currentThread().getName() + " sta uscendo | nred: " +
				nred + " nblue: " + nblue);
	    notify(); //capacità transito pari a 1
	}

}
