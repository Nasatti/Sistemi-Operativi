
public class Producer extends Thread{
	CokeMachineIf m;
	
	public Producer(CokeMachineIf m) {
		this.m = m;
		setName("Producer");
	}
	
	public void run() {
		while(true) {
			/*try {
				sleep((int)(Math.random()*5000));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} //dorme 0-5 secondi*/
			
			m.insert();	//va a rifornire se necessario
		}
			
	}

}
