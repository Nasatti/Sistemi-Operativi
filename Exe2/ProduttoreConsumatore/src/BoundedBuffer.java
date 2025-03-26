import java.util.concurrent.Semaphore;

/**
 * BoundedBuffer.java
 *
 * This program implements the bounded buffer using Java synchronization.
 *
 * Figure 7.31
 *
 * @author Gagne, Galvin, Silberschatz
 * Operating System Concepts with Java - Sixth Edition
 * Copyright John Wiley & Sons - 2003.
 */

public class BoundedBuffer implements Buffer
{
   private static final int   BUFFER_SIZE = 5; // capacità limitata

   private int in;   // points to the next free position in the buffer
   private int out;  // points to the next full position in the buffer
   //queste due vengono incrementate in modo modulare
   private Object[] buffer; // array di oggetti

   private Semaphore mutex;//mutua esclusione --> accesso sezione critica, mutua esclusione
   private Semaphore full; //conta le locazioni piene --> buffer pieno, produttore si ferma
   private Semaphore empty; //conta le locazioni vuote --> buffer vuoto, consumatore si ferma
   
   //full e empty sono gereralizzati
   
   public BoundedBuffer()
   {
      // buffer is initially empty
      in = 0;
      out = 0;
      buffer = new Object[BUFFER_SIZE];
      mutex = new Semaphore(1); //sez. critica libera
      full = new Semaphore(0);
      empty = new Semaphore(BUFFER_SIZE);
   }

   public void insert(Object item) {
      
	try {
		empty.acquire();//produttore acquisce una locazione vuota
		mutex.acquire();//acquisicse sezione critica
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	  
	 // add an item to the buffer
    //SEZIONE CRITICA
     buffer[in] = item;
     in = (in + 1) % BUFFER_SIZE;

	System.out.println("Producer Entered " + item + " Buffer full locations = " +  full.availablePermits()+1);
	
	 mutex.release();//rilascia sezione critica
	 full.release();//rilascia una locazione piena --> consente al consumatore di acquisire la risorsa
	
   }

   // consumer calls this method
   public Object remove() {
      Object item = null;

    try {
    	full.acquire();//consumatore di acquisire la risorsa
  		mutex.acquire();//mutua esclusione
  	} catch (InterruptedException e) {
  		// TODO Auto-generated catch block
  		e.printStackTrace();
  	}
      
       // remove an item from the buffer
      item = buffer[out];
      out = (out + 1) % BUFFER_SIZE;

	  
		System.out.println("Consumer Consumed " + item + " Buffer empty locations = " + empty.availablePermits()+1);
    
    mutex.release();
    empty.release();
    
    return item;
   }

}
