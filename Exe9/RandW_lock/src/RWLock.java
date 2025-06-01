/**
 * An interface for reader-writer locks.
 *
 * In the text wedo not have readers and writers
 * pass their number into each method. However we do so
 * here to aid in output messages.
 *
 * Figure 7.19
 *
 * @author Gagne, Galvin, Silberschatz
 * Operating System Concepts with Java - Sixth Edition
 * Copyright John Wiley & Sons - 2003.
 */

public interface RWLock
{
	public abstract void acquireReadLock(int readerNum);
	public abstract void acquireWriteLock(int writerNum);
	public abstract void releaseReadLock(int readerNum);
	public abstract void releaseWriteLock(int writerNum);
}
