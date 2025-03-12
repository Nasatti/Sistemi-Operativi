/**
 * This simple program illustrates the join() method.
 * Since we are joining on the JoinableWorker, the order of
 * the output must be:
 * (1) Worker working
 * (2) Worker done
 *
 * @author Gagne, Galvin, Silberschatz
 * Operating System Concepts with Java - Sixth Edition
 * Copyright John Wiley & Sons - 2003.
 */

class JoinableWorker implements Runnable
{
    public void run() {
    	try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        System.out.println(Thread.currentThread().getName() +" : I'm working");
    }
}

public class JoinExample
{
	public static void main(String[] args) {
		System.out.println(Thread.currentThread().getName() + " thread: I'm creating a thread Worker");
 		Thread task = new Thread(new JoinableWorker(), "Worker");//creazione tramite metodo 2, che implementa l'interfaccia, passiamo pure il nuovo nome
        task.start();
        try { task.join(); } catch (InterruptedException ie) { }//gli stati di blocco possono essere interrotte(InterruptedException) --> try-catch
        System.out.println(Thread.currentThread().getName() + " thread: " + task.getName() + " done!");//con la join aspetta che il thread figlio abbia finito prima di scrivere lui, sanza la join non lo avrebbe aspettato
	}
}
