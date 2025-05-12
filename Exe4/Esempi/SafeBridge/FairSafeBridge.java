
public class FairSafeBridge implements Bridge {

	private int nred; //n. auto rosse in transito
	private int nblue; //n. auto blue in transito
	private int waitblue; //n. auto blu in attesa di entrare
	private int waitred; //n. auto rosse in attesa di entrare
	private boolean blueturn; //se true, è il turno delle blu, se false è il turno delle rosse

	
    public FairSafeBridge() {
    	nred = 0;
    	nblue = 0;
    	waitred = 0;
    	waitblue = 0;
    	blueturn = true; //priorità alle blu
    }
    
	@Override
	public synchronized void redEnter() {
		waitred++; //un'auto rossa in attesa di passare
		while ( (nblue + nred > 0) || (waitblue > 0 && blueturn))
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		//Sezione critica: l'auto passa sul ponte
		waitred--; //un'auto rossa in attesa in meno
		nred++;
		System.out.println(Thread.currentThread().getName() + " sta passando; nred= " + nred +
				" nblue= " + nblue);
	}

	@Override
	public synchronized void redExit() {
		nred--;
		System.out.println(Thread.currentThread().getName() + " sta uscendo; nred= " + nred +
				" nblue= " + nblue);
		
		if (nred == 0) { //l'ultima auto rossa cede il turno alle blu
			blueturn = true;
			notifyAll();
		}
	}

	@Override
	public synchronized void blueEnter() {
		waitblue++;
		while ((nblue + nred > 0) || (waitred > 0 && !blueturn))
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		//Sezione critica: l'auto passa sul ponte
		waitblue--;
		nblue++;
		System.out.println(Thread.currentThread().getName() + " sta passando; nred= " + nred +
				" nblue= " + nblue);
	}

	@Override
	public synchronized void blueExit() {
		nblue--;
		System.out.println(Thread.currentThread().getName() + " sta uscendo; nred= " + nred +
				" nblue= " + nblue);
		
		if (nblue == 0) { //l'ultima auto blu cede il turno alle blu 
			blueturn = false;
			notifyAll();
		}
	}

}
