import java.util.concurrent.locks.*;

public class Bar  implements BarIf {
	private final int NMAX = 8; //caapcità del bar
	private int clientiO; //n. di clienti ospiti dentro il bar
	private int clientiL; //n. di clienti locali dentro il bar
	//requisiti di safety
	//clientiO > 0 -> clientiL ==0 &&
	//clientiL > 0 -> clientiO ==0    
	private boolean uscita; //flag che indica l'intenzione del barista di chiudere
    private int sospesiO; //n. tifosi ospiti in attesa di entrare
	
    //Elemnti per la sincronizzazione
    private Lock lock;
    private Condition codaClientiL, codaClientiO, codaBarista; //code di sospensione
    
    public Bar() {
    	//Bar è aperto, pulito e senza clienti
    	clientiO=0;
    	clientiL=0;
    	sospesiO=0;
    	uscita=false;
    	lock = new ReentrantLock();
    	codaClientiL = lock.newCondition();
    	codaClientiO = lock.newCondition();
    	codaBarista = lock.newCondition();
    }
    
    
	@Override
	public void entraO() {
		lock.lock();
	     try { //sezione critica
	     while ( clientiL > 0 ||
	    		 clientiO == NMAX ||
	    		 uscita
	    		 ) {
	    	 sospesiO++;
	    	 codaClientiO.await();
	    	 sospesiO--;
	         }
	     clientiO++;
	     }
	     catch (InterruptedException e)
	     {
	    	 e.printStackTrace();
	     }
	     finally {
	    	lock.unlock();
	     }		// TODO Auto-generated method stub

	}

	@Override
	public void entraL() {
		lock.lock();
	     try { //sezione critica
	      while(clientiO > 0 ||  //vincolo di safety
	    		clientiL == NMAX || //vincolo capacitivo
	    		uscita || //vincolo di chiusura del barista
	    		sospesiO > 0 //vincolo di priorità agli ospiti
	    		) 
	    	  codaClientiL.await();
	      clientiL++; //ci sarà un cliente locale in più nel bar
	  
	     }
	     catch (InterruptedException e)
	     {
	    	 e.printStackTrace();
	     }
	     finally {
	    	lock.unlock();
	     }
	}

	@Override
	public void esceO() {
		lock.lock();
	     try { //sezione critica
	    	 clientiO--; //un cliente locale in meno
	    	 //prima proviamo a svegliare il barista
	    	 if (uscita)
	    		 codaBarista.signal();
	    	 //precedenza ai clienti ospite
	    	 else if (sospesiO > 0)
	    		 codaClientiO.signalAll();
	    	 else 
	    		 codaClientiL.signalAll();
	    	
	     }	
	     finally {
	    	lock.unlock();
	     }	// TODO Auto-generated method stub

	}

	@Override
	public void esceL() {
		lock.lock();
	     try { //sezione critica
	    	 
	    	 clientiL--; //un cliente locale in meno
	    	 //prima proviamo a svegliare il barista
	    	 if (uscita)
	    		 codaBarista.signal();
	    	 //precedenza ai clienti ospite
	    	 else if (sospesiO > 0)
	    		 codaClientiO.signalAll();
	    	 else 
	    		 codaClientiL.signalAll();
	     }	
	     finally {
	    	lock.unlock();
	     }	// TODO Auto-generated method stub

	}

	@Override
	public void inizio_chiusura() {
     lock.lock();
     try { //sezione critica
        //dichiara la sua intenzione di voler chiudere per pulire
    	uscita = true;
    	while (clientiO >0 || clientiL >0)
			codaBarista.await();
      }
      catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
     }	
     finally {
    	lock.unlock();
     }	
	}//End method

	@Override
	public void fine_chiusura() {
		lock.lock();
	     try { //sezione critica
	       uscita = false;
	       System.out.println(Thread.currentThread().getName()+": BAr dinuovo APERTO!");
	       if (sospesiO > 0) //ci sono clienti ospite in attesa di entrare
	    	   codaClientiO.signalAll();
	       else codaClientiL.signalAll();
	     }	
	     finally {
	    	lock.unlock();
	     }
	}

}
