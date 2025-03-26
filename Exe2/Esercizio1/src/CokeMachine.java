import java.util.concurrent.Semaphore;

public class CokeMachine implements CokeMachineIf{

	static final int N = 5;
	int count;
	private Semaphore mutex;//mutua eslusione
	private Semaphore empty;//binario che rappresenta macchinetta vuota-->x fornitore
	
	
	public CokeMachine() {
		this.count = N;//macchinetta già piena
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
		count = N;
		System.out.println(Thread.currentThread().getName()+" Finito di riempire");
		mutex.release();
	}

	@Override
	public void remove() {
		try {
			mutex.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//SEZ CRITICA
		if(count > 0) {
			//prelevo
			count--;
			System.out.println(Thread.currentThread().getName()+" Prelevo una lattina, count =" + count);
			if(count==0) {
				//avverto(sveglio) il fornitore
				System.out.println(Thread.currentThread().getName()+" Avverto il produttore");
				empty.release();
			}
		}
		mutex.release();
	}
}
