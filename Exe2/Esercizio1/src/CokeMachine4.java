import java.util.concurrent.Semaphore;

public class CokeMachine4 implements CokeMachineIf{

	static final int N = 5;

	private Semaphore mutex;//mutua eslusione
	private Semaphore empty;//binario che rappresenta macchinetta vuota-->x fornitore
	private Semaphore count;//lattine difentano semafori--> semaforo generalizzato
	
	
	public CokeMachine4() {
		this.count = new Semaphore(N);
		this.mutex = new Semaphore(1); //non ci sono utenti e la mcchinetta è libera
		this.empty = new Semaphore(0); //
	}

	@Override
	public void fill() {
		try {
			empty.acquire();
			mutex.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//SEZ CRITICA
		System.out.println(Thread.currentThread().getName()+" Sto riempiendo");
		count.release(N);
		System.out.println(Thread.currentThread().getName()+" Finito di riempire");
		mutex.release();
	}

	@Override
	public void remove() {
		
		try {
			empty.acquire();
			mutex.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//SEZ CRITICA
		if(count.availablePermits() > 0) {
			//prelevo
			try {
				count.acquire();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(Thread.currentThread().getName()+" Prelevo una lattina, count =" + count);
			if(count.availablePermits() == 0) {
				//avverto(sveglio) il fornitore
				System.out.println(Thread.currentThread().getName()+" Avverto il produttore");
				try {
					count.acquire();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		mutex.release();
	}
}
