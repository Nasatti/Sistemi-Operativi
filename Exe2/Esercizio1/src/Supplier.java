
public class Supplier extends Thread{
	CokeMachineIf m;
	
	public Supplier(CokeMachineIf m) {
		this.m = m;
	}
	
	public void run() {
		while(true) {
			m.fill();
			try {
				sleep((int)(Math.random()*5000));
			}
			catch(InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
