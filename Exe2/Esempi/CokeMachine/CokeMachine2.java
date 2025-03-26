//Versione base (le lattine sono rappresentate da un semplice contatore)
//Variante 2: il fornitore � svegliato dal primo cliente che trova la macchinetta vuota 
import java.util.concurrent.Semaphore;
public class CokeMachine2 implements CokeMachineIf {
	static final int N=5;
	int count; //contatore delle lattine presenti
		
	private Semaphore mutex;//mutua esclusione
	private Semaphore empty;//per svegliare il fornitore quando la macchinetta � vuota
	private boolean fornitoreAvvisato; //flag/segnale per coordinare gli utenti (un solo utente deve avvisare il fornitore)
	
	public CokeMachine2()	{
		count=N;//all'inizio la macchinetta � piena
		mutex=new Semaphore(1);//l'accesso alla macchinetta � libero
		empty=new Semaphore(0); //all'inizio la macchinetta � piena (il fornitore deve attendere)
		fornitoreAvvisato = false;
	}
	
	public void remove() {
		try {
			mutex.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if (count == 0 && !fornitoreAvvisato){ //la macchinetta � vuota e nessuno ha ancora avvisato il fornitore	
				System.out.println(Thread.currentThread().getName() + " : valore semaforo empty "+ empty.availablePermits());
				empty.release(); //avvisiamo il fornitore
				System.out.println(Thread.currentThread().getName() + " : valore semaforo empty "+ empty.availablePermits());
		        fornitoreAvvisato = true;	//setta il segnale di avviso
		 }
		else if (count > 0){ //ci sono lattine 	
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
		count=N; //ricarica la macchinetta
		System.out.println(Thread.currentThread().getName() + ": N. lattine = " + count);
		fornitoreAvvisato = false; //resetta il segnale di avviso		  
		mutex.release();
	}
}
