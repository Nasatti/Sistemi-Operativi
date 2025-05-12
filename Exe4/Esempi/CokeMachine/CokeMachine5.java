
public class CokeMachine5 implements CokeMachineIf {
	static final int N = 5;
	int count; //n. di lattine effettivamente presenti in un dato momento	

	public CokeMachine5(){
		count = N; //Ipotizziamo che all'inizio la macchinetta è piena
	}
	
	@Override
	public synchronized void remove() {
		while (count==0)//distributore vuoto
			try {
				wait(); //bloccante
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		//sezione critica
		count--; //prelievo della lattina
		System.out.println(Thread.currentThread().getName()+" rimuove una lattina; count = "+count);
		//se ho prelevato l'ultima lattina, sveglio il fornitore
		if (count ==0) {
		   notifyAll(); //sveglio tutti e quindi anche il fornitore (non c'è modo di mandare una sveglia mirata)
		   System.out.println(Thread.currentThread().getName()+" sveglio tutti!");
		}
	}

	@Override
	public synchronized void insert() {
		try {
			wait();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(Thread.currentThread().getName()+ ": count = "+count);
		count = N; //rifornitura
		System.out.println(Thread.currentThread().getName()+ ": count = "+count);
		notify();
	}

}
