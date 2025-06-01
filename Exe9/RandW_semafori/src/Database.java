import java.util.concurrent.Semaphore;

/**
 * Database.java
 *
 * This class contains the methods the readers and writers will use
 * to coordinate access to the database.
 * Access is coordinated using Java synchronization.
 *
 * This is modified from the original version so that writers are not starved.
 *
 * @author Gagne, Galvin, Silberschatz
 * Operating System Concepts with Java - Sixth Edition
 * Copyright John Wiley & Sons - 2003.
 */
public class Database implements RWLock
{
   // the number of active readers
   private int readerCount;

   private Semaphore mutex;
   private Semaphore accesso;

   public Database()
   {
      readerCount = 0;
      this.mutex = new Semaphore(1);
      this.accesso = new Semaphore(1);
   }

   // reader will call this when they start reading
   public void acquireReadLock(int readerNum)
   {
      try {
    		  mutex.acquire();
      } catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
      ++readerCount;
      if(readerCount == 1) {
		  try {
			accesso.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  System.out.println("Reader" + readerNum + " leggo count: " + readerCount);
      }
      mutex.release();
   }

   // reader will call this when they finish reading
   public void releaseReadLock(int readerNum)
   {
     try {
    	 
		mutex.acquire();
     } catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	 }
     --readerCount;
     System.out.println("Reader" + readerNum + " Sto usendo count: "+readerCount);
     if(readerCount == 0) {
    	 accesso.release();
    	 System.out.println(Thread.currentThread().getName() + "Count = 0, cambio turno");
     }
     mutex.release();
   }

   // writer will call this when they start writing
    public void acquireWriteLock(int writerNum) {
    	 try {
 			accesso.acquire();
 		} catch (InterruptedException e) {
 			// TODO Auto-generated catch block
 			e.printStackTrace();
 		}
    	 System.out.println("writer" + writerNum + " sto scrivendo");
   }

   // writer will call this when they start writing
   public void releaseWriteLock(int writerNum) {
	   accesso.release();
	   System.out.println("writer" + writerNum + " sto uscendo");
   }
}
