
public class User extends Thread{
	CokeMachineIf m;
	
	public User(CokeMachineIf machine, int i) {
		m = machine;
		setName("User"+i);
	}
	
	public void run() {
		while(true) {
			m.remove(); //si preleva una lattina alla volta
			try {
				sleep((int)(Math.random()*5000));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
