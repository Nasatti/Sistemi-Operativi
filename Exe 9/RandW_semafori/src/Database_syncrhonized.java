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
public class Database_syncrhonized implements RWLock
{
   // the number of active readers
   private int readerCount;

   // flag to indicate whether the database is in use
   private boolean dbWriting;

   // flag to indicate there are waiting writers
   private boolean waitingWriter;

   public Database_syncrhonized()
   {
      readerCount = 0;
      dbWriting = false;
      waitingWriter = false;
   }

   // reader will call this when they start reading
   public synchronized void acquireReadLock(int readerNum)
   {
      // we cannot proceed as long as there are writers active in the
      // database or waiting to access the database.
      while (dbWriting || waitingWriter) {
         try { wait(); }
         catch(InterruptedException e) { }
      }

      ++readerCount;

      System.out.println("Reader " + readerNum + " is reading. Reader count = " + readerCount);
   }

   // reader will call this when they finish reading
   public synchronized void releaseReadLock(int readerNum)
   {
      --readerCount;

      // if I am the last reader tell all others
      // that the database is no longer being read
      // *** this has been changed from notify() in Figure 7.4 to notifyAll()
      // as it is possible that a notify() may notify a reader
      // waiting upon the condition "waitingWriter"
      if (readerCount == 0)
	 notifyAll();

      System.out.println("Reader " + readerNum + " is done reading. Reader count = " + readerCount);
   }

   // writer will call this when they start writing
    public synchronized void acquireWriteLock(int writerNum) {
      while (readerCount > 0 || dbWriting == true) {
	 waitingWriter = true;
         try { wait(); }
         catch(InterruptedException e) {}
      }

      // once there are either no readers or writers
      // indicate that the database is being written
      dbWriting = true;
      waitingWriter = false;

      System.out.println("writer " + writerNum + " is writing.");
   }

   // writer will call this when they start writing
   public synchronized void releaseWriteLock(int writerNum)
   {
      dbWriting = false;

      System.out.println("writer " + writerNum + " is done writing.");

	/**
	 * This must be notifyAll()  as there may be more than
	 * one waiting reader to read the database and we must
	 * notify ALL of them.
	 */
      notifyAll();
   }
}
