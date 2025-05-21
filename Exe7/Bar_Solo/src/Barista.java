
public class Barista extends Thread{

	private BarIF bar;

	public Barista(BarIF bar) {
		// TODO Auto-generated constructor stub
		this.bar = bar;
		setName("T-Barista");
	}

	public void run() {
		while(true) {
			System.out.println(getName() + ": bar sporco, non entra nessun");
			bar.inizio_chiusura();
			System.out.println(getName() + ": bar chiuso, sto pulendo");
			try {
				sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(getName() + ": bar pulito, riapro");
			bar.fine_chiusura();
			try {
				sleep((int)(Math.random()*15000));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
