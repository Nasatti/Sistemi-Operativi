
public class CarBlu extends Thread{

	private BridgeIF b;
	
	public CarBlu(BridgeIF b, int i) {
		// TODO Auto-generated constructor stub
		this.b = b;
		setName("Blu-"+i);
	}
	
	public void run() {
		while(true) {
			try {
				sleep((int)(Math.random()*5000));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			b.bluEnter();
			try {
				sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			b.bluExit();
		}
	}

}
