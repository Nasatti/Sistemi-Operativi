//Versione base (le lattine sono rappresentate da un semplice contatore)
//Variante 3: il fornitore � svegliato da tutti i clienti che trovano la macchinetta vuota 
//Questa soluzione � da scartare rispetto alle altre perch� il semaforo empty viene sempre incrementato 
//e quindi il fornitore viene sempre svegliato. La correttezza � data dal controllo count==0 che fa il fornitore
import java.util.concurrent.Semaphore;
public class CokeMachine3 implements CokeMachineIf {
	static final int N=5;
	int count; //contatore delle lattine presenti
		
	private Semaphore mutex;//mutua esclusione
	private Semaphore empty;//per svegliare il fornitore quando la macchinetta � vuota

	
	public CokeMachine3()	{
		count=N;//all'inizio la macchinetta � piena
		mutex=new Semaphore(1);//l'accesso alla macchinetta � libero
		empty=new Semaphore(0); //all'inizio la macchinetta � piena (il fornitore deve attendere)
		
	}
	
	public void remove() {
		try {
			mutex.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if (count == 0){ //la macchinetta � vuota	
				System.out.println(Thread.currentThread().getName() + " : valore semaforo empty "+ empty.availablePermits());
				empty.release(); 
				System.out.println(Thread.currentThread().getName() + " : valore semaforo empty "+ empty.availablePermits());
		     
		 }
		else { //ci sono lattine 	
			count--;
		    System.out.println("Lattina rimossa da utente "+ Thread.currentThread().getName());
		}
		mutex.release();
	}
	
	public void insert() 	{
		try {
			empty.acquire();//per sospendere il fornitore, quando la macchinetta non � vuota
			mutex.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(Thread.currentThread().getName() + ": N. lattine = " + count);
		System.out.println(Thread.currentThread().getName() + ": Valore semaforo empty "+ empty.availablePermits());		
		if (count == 0){ //ricarica la macchinetta, ma solo se � vuota (potrebbe ricevere pi� avvisi su empty)
		  count=N; 
		  System.out.println(Thread.currentThread().getName() + ": Ricarico! N. lattine = " + count);
		}
		mutex.release();
	}
}
