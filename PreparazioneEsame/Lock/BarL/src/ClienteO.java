
public class ClienteO extends Thread{

	BarIF bar;
	
	public ClienteO(BarIF bar, int i) {
		// TODO Auto-generated constructor stub
		this.bar = bar;
		setName("ClienteO" + i);
	}
	
	public void run() {
		while(true) {
			try {
				sleep((int)(Math.random()*5000));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			bar.entraO();
			try {
				sleep((int)(Math.random()*3000));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			bar.esceO();
		}
	}

}
