/**
 * This class file illustrates deferred cancellation using the
 * isInterrupted() method.
 *
 * Figure 5.11
 *
 * @author Gagne, Galvin, Silberschatz
 * Operating System Concepts with Java - Sixth Edition
 * Copyright John Wiley & Sons - 2003.
 */

public class InterruptibleThread implements Runnable 
{
	/**
	 * This thread will continue to run as long
	 * as it is not interrupted.
	 */
	public void run() {
		while (true) {
			/**
			 * do some work for awhile
			 */
			if (Thread.currentThread().isInterrupted()) {
				System.out.println("Thread figlio: I'm interrupted!");
				break;
			}
			System.out.println("lavoro");	
		}
		// clean up and terminate
	}
}
