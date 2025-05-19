
public class Barista extends Thread{

	BarIf bar;
	
	public Barista(BarIf bar) {
		this.bar = bar;
		setName("Thread-Barista");
	}

	public void run() {
		while(true) {
			System.out.println(getName()+": il bar è sporco e voglio chiudere!");
			bar.inizio_chiusura(); //natura bloccante
			//Bar vuoto e in fase di pulizia
			System.out.println(getName()+": sto pulendo il bar...");
			try { //pulizia...
				sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(getName()+": ho finito di pulire il bar!");
			bar.fine_chiusura();
			try {
				sleep((int)(Math.random()*8000));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
}
