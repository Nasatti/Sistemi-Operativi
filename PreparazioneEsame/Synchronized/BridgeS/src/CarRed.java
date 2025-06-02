
public class CarRed extends Thread{

	private BridgeIF b;
	
	public CarRed(BridgeIF b, int i) {
		// TODO Auto-generated constructor stub
		this.b = b;
		setName("Red-"+i);
	}
	
	public void run() {
		while(true) {
			try {
				sleep((int)(Math.random()*5000));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			b.redEnter();
			try {
				sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			b.redExit();
		}
	}

}
