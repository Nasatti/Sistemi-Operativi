import java.util.concurrent.locks.*;

public class CokeMachine implements CokeMachineIF {
	static final int N = 5; //capacit� del distributore
	private int count; //contatore di lattine
	
	//Condition variables
	final Lock lock = new ReentrantLock();
	final Condition waitsetUtente  = lock.newCondition(); 
	final Condition waitsetFornitore = lock.newCondition(); 
	
	public CokeMachine() {
		//ipotesi: all'inizio il distributore � pieno e tutto tace
		count = N;
	}
	
	
	@Override
	public void preleva() {
	 lock.lock();
	 try {
	    //sezione critica	  
		//se non ci sono lattine aspetto	
		 while (count == 0)
			try {
				waitsetUtente.await();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 //Ne prelevo una
		 count--;
		 System.out.println("Lattina rimossa da utente "+ Thread.currentThread().getName() + " N. lattine:" + count);
		 //Variante 1: se ho prelevato l'ultima avviso il fornitore
		 if (count == 0) {
			 waitsetFornitore.signal(); //notifico tutti e quindi anche il fornitore	
			 System.out.println(Thread.currentThread().getName() +" : sveglio il fornitore! N. lattine:" + count);
				
		 }	  	  
	 }//end try
	 finally {
	    	System.out.println("releasing lock");  
	        lock.unlock();
	  }
		
	}

	
	//ATTENZOINE CHE NON VIENE UTILIZZATO NEL WHILE
	@Override
	public void rifornisci() {
	  lock.lock();
	  //sezione critica	  	
	  //sospendiamo il fornitore
	  try {
		waitsetFornitore.await(); 
		//rifornisce il distributore
		count = N;
		System.out.println( "Il fornitore rifornisce e sveglia tutti. N. lattine:" + count);
		waitsetUtente.signalAll(); //svegliamo tutti gli utenti in attesa
	  }
	 catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
	 }		
	 finally {
	    	  System.out.println("releasing lock");  
	        lock.unlock();
	  }
	}

}
