import java.util.concurrent.locks.*;

/**
 * BoundedBuffer.java
 *
 * This program implements the bounded buffer using Java monitor with condition
 * variables.
 *
 * 
 * @author Patrizia Scandurra
 */

public class BoundedBuffer implements Buffer
{
   private static final int   BUFFER_SIZE = 5;

   private int count; // number of items in the buffer
   private int in;   // points to the next free position in the buffer
   private int out;  // points to the next full position in the buffer
   private Object[] buffer;

   //Condition variables
   final Lock lock = new ReentrantLock();
   final Condition notFull  = lock.newCondition(); // condizione attesa del produttore, non pieno
   final Condition notEmpty = lock.newCondition(); // condizione attesa del consumatore, non vuoto

   
   public BoundedBuffer()
   {
      // buffer is initially empty
      count = 0;
      in = 0;
      out = 0;

      buffer = new Object[BUFFER_SIZE];
   }

// producer calls this method
    public  void insert(Object item) throws InterruptedException {
          
      lock.lock();
      try {
        while (count == BUFFER_SIZE)
          notFull.await();
        // add an item to the buffer
        ++count;
        buffer[in] = item;
        in = (in + 1) % BUFFER_SIZE;
        if (count == BUFFER_SIZE)
    		System.out.println("Producer Entered " + item + " Buffer FULL");
    	else
    		System.out.println("Producer Entered " + item + " Buffer Size = " +  count);
        
        notEmpty.signal();
      } finally {
    	  System.out.println("releasing lock");  
        lock.unlock();
      }

   }

   // consumer calls this method
 
   public Object remove() throws InterruptedException {
      Object item;

      lock.lock();
      try {
        while (count == 0)
          notEmpty.await(); 
        	
        // remove an item from the buffer
        --count;
        item = buffer[out];
        out = (out + 1) % BUFFER_SIZE;

   	   if (count == 0)
   		 System.out.println("Consumer Consumed " + item + " Buffer EMPTY");
   	   else
   		 System.out.println("Consumer Consumed " + item + " Buffer Size = " + count);

        notFull.signal();
        return item;
      } 
    finally {
     	  System.out.println("releasing lock");  
    	lock.unlock();
      }

   }
   
   
}
