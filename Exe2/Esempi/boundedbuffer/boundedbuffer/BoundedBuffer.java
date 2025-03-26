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
   private static final int   BUFFER_SIZE = 5;

   private int in;   // points to the next free position in the buffer
   private int out;  // points to the next full position in the buffer
   private Object[] buffer;

   private Semaphore mutex;//mutua esclusione 
   private Semaphore full; //conta le locazioni piene
   private Semaphore empty; //conta le locazioni vuote
   
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
		empty.acquire();
		mutex.acquire();
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	  
	  // add an item to the buffer
    
      buffer[in] = item;
      in = (in + 1) % BUFFER_SIZE;

		System.out.println("Producer Entered " + item + " Buffer full locations = " +  full.availablePermits()+1);
	
	 mutex.release();
	 full.release();
	
   }

   // consumer calls this method
   public Object remove() {
      Object item = null;

    try {
    	full.acquire();
  		mutex.acquire();
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
