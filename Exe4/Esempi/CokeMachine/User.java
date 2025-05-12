
public class User extends Thread{
	CokeMachineIf m;
	
	public User(CokeMachineIf m, int i) {
		this.m=m;
		setName("Thread-"+i);
	}
	
	public void run() {
		while(true) {
			m.remove();
			try {
				sleep((int)(Math.random()*5000));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} //dorme 0-5 secondi
		}
	}

}
