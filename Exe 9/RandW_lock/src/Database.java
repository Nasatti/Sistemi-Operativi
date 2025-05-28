import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

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
   private boolean write;
   private Lock lock;
   private Condition accesso;


   public Database()
   {
      readerCount = 0;
      write = false;
      this.lock = new ReentrantLock();
      this.accesso = lock.newCondition();
   }

   // reader will call this when they start reading
   public void acquireReadLock(int readerNum)
   {
      lock.lock();
      try {
    	  while(write) {
    		  try {
				accesso.await();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	  }
    	  readerCount++;
    	  System.out.println("reader " + readerNum + " leggo count:" + readerCount);
      }
      finally {
    	  lock.unlock();
      }
   }

   // reader will call this when they finish reading
   public void releaseReadLock(int readerNum)
   {
     lock.lock();
     try {
    	 readerCount--;
    	 System.out.println("reader " + readerNum + " finito leggere count:" + readerCount);
    	 if(readerCount == 0) {
    		 accesso.signalAll();
    		 System.out.println("reader " + readerNum + " sveglio scrittore");
    	 }
    	 else accesso.signal();
     }
     finally {
    	 lock.unlock();
     }
   }

   // writer will call this when they start writing
    public void acquireWriteLock(int writerNum) {
    	 lock.lock();
    	 try {
    		 write = true;
    		 while(!write) {
    			 try {
					accesso.await();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    		 }
    		 System.out.println("writer " + writerNum + " scrivo count:" + readerCount);
    	 }
    	 finally {
    		 lock.unlock();
    	 }
   }

   // writer will call this when they start writing
   public void releaseWriteLock(int writerNum) {
	  lock.lock();
	  try {
		  write = false;
		  accesso.signalAll();
		  System.out.println("writer " + writerNum + " esco count:" + readerCount);
	  }
	  finally {
		  lock.unlock();
	  }
   }
}
