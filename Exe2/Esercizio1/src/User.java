
public class User extends Thread{

	CokeMachineIf m;

	public User(CokeMachineIf m, int i) {
		this.m = m;
		setName("Utente " + i);
	}
	
	public void run() {
		while(true) {
			m.remove();
			try {
				sleep((int)(Math.random()*5000));
			}
			catch(InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
