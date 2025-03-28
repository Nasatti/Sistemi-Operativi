//Versione base (le lattine sono rappresentate da un semplice contatore)
//Variante 1: il fornitore è svegliato dal cliente che preleva l'ultima lattina 
import java.util.concurrent.Semaphore;

public class CokeMachine1 implements CokeMachineIf {
	static final int N=5; //capacità della macchinetta
	int count; //contatore del num. di lattine
	
	Semaphore mutex; //semaforo per la la mutua esclusione
	Semaphore empty; //semaforo per la risorsa "macchinetta vuota" 
	
	public CokeMachine1() {
		//Ipotizziamo che inizialmente la macchinetta sia piena e non ci sono clienti.
		count = N;
		mutex = new Semaphore(1);
		empty = new Semaphore(0);
	}
	
	@Override
	//Comportamento del cliente
	public void remove() {
		try {
			mutex.acquire();
		}
	    catch (InterruptedException e) {
		  e.printStackTrace();
	    }

		if (count > 0) { //se ci sono lattine, ne preleva una
			count--; //prelievo della lattina
			System.out.println("111" + Thread.currentThread().getName()+" : lattina rimossa. N. lattine rimaste= "+ count);
			//se ha prelevato l'ultima lattina, sveglia il fornitore
		    if (count == 0) {
				empty.release();
				System.out.println(Thread.currentThread().getName()+" : sveglio il fornitore");			
			}
		}
	  
		mutex.release();			
	}

	@Override
	//Comportamento del fornitore
	public void insert() {
		try {
			empty.acquire();
			mutex.acquire();
			System.out.println("111 Fornitore: n. lattine= "+count);
			count = N; //riempie la macchinetta
			System.out.println("Fornitore riempie: n. lattine= "+count);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			mutex.release();
		}

	}

}
