public class ClienteLocale extends Thread{

	private BarIF bar;

	public ClienteLocale(BarIF bar, int i) {
		// TODO Auto-generated constructor stub
		this.bar = bar;
		setName("T-Locale" + i);
	}

	public void run() {
		while(true) {
		bar.entral();
		System.out.println(getName() + ": sto consumando 3 trilioni di bivve...");
		try {
			sleep((int)(Math.random()*3000));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(getName() + ": sto uscendo dal bar");
		bar.escel();
		try {
			sleep((int)(Math.random()*3000));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
	}
}
