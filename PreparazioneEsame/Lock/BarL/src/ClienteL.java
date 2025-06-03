
public class ClienteL extends Thread{

	BarIF bar;
	
	public ClienteL(BarIF bar, int i) {
		// TODO Auto-generated constructor stub
		this.bar = bar;
		setName("ClienteL" + i);
	}
	
	public void run() {
		while(true) {
			try {
				sleep((int)(Math.random()*5000));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			bar.entraL();
			try {
				sleep((int)(Math.random()*3000));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			bar.esceL();
		}
	}

}
