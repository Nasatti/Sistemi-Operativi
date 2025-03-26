//Versione con semaforo generalizzato per contare le lattine
import java.util.concurrent.Semaphore;
public class CokeMachine4 implements CokeMachineIf {
	static final int N = 5; //capacit� del distributore
	//int count; //contatore delle lattine presenti
	Semaphore count; //semaforo generalizzato per contare le lattine presenti
	Semaphore mutex; //semaforo binario per la sezione critica (per count.availablePermits())
	Semaphore empty; //semaforo per svegliare il fornitore (risorsa logica: macchinetta vuota)
	
	public CokeMachine4() {
		//count = N; //all'inizio la macchinetta � piena
		mutex = new Semaphore(1);
		empty = new Semaphore(0);
		count = new Semaphore(N);
		System.out.println(Thread.currentThread().getName()+
				" : n. lattine = " + count.availablePermits());
	}
	
	
	@Override
	public void remove() {
		try {
			mutex.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//sezione critica
		/*if (count > 0) { //ci sono lattine
			count--; //ne prende una
			System.out.println(Thread.currentThread().getName()+
					" : n. lattine = " + count);
			//se ho rimosso l'ultima lattina, avviso il fornitore
			if (count == 0) 
				empty.release();				
		}*/
		if (count.availablePermits() > 0) { //ci sono lattine
			//mutex.release();
			System.out.println(Thread.currentThread().getName()+
					" : n. lattine = " + count.availablePermits());
			try {
				count.acquire(); //ne prende una
				System.out.println(Thread.currentThread().getName()+
						" : n. lattine = " + count.availablePermits());
				//se ho rimosso l'ultima lattina, avviso il fornitore
				if (count.availablePermits()  == 0) 
					empty.release();
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		mutex.release();
	}

	@Override
	//Variante 1: il fornitore � avvisato dall'utente che preleva l'ultima lattina
	public void insert() {
		//comportamento lazy: va a rifornire solo se avvisato
		try {
			empty.acquire(); //acquisisce la risorsa macchinetta vuota
			mutex.acquire();
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//sezione critica
		//System.out.println(Thread.currentThread().getName()+
		//		" : n. lattine = " + count);
		System.out.println(Thread.currentThread().getName()+
				" : n. lattine = " + count.availablePermits());
		//count = N; //ricarica la macchinetta
		//System.out.println(Thread.currentThread().getName()+
		//		" : n. lattine = " + count);
		//if (count.availablePermits() <= 0) 
		count.release(N); //ricarica la macchinetta
		//End sezione critica
		System.out.println(Thread.currentThread().getName()+
				" : n. lattine = " + count.availablePermits());		
		mutex.release();
		
	}

}
