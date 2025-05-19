
public class Utente extends Thread{
	CokeMachineIF c;

	public Utente(CokeMachineIF c, int i) {
		this.c = c;
		setName("Thread-utente"+i);
	}
	
	public void run() {
		while(true) {
			c.preleva(); //bloccante
			//beve la lattina
			try {
				Thread.sleep((int)(Math.random()*5000));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
			
	}

}
