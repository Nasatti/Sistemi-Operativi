
public class Utente extends Thread{

	private CokeMachIF coke;
	public Utente(CokeMachIF coke, int i) {
		// TODO Auto-generated constructor stub
		this.coke = coke;
		setName("Utente-" + i);
	}

	public void run() {
		while(true) {
			try {
				sleep((int)(Math.random()*5000));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			coke.preleva();
		}
	}

}
