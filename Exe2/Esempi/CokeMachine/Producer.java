
public class Producer extends Thread{
	CokeMachineIf m;
	
	public Producer(CokeMachineIf machine) {
		m = machine;
	}
	
	public void run() {
		while(true) {
			m.insert();
			try {
				sleep((int)(Math.random()*5000));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
