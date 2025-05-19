
public class ClienteLocale extends Thread{
BarIf bar;

	public ClienteLocale(BarIf bar, int i) {
		this.bar = bar;
		setName("Thread-L"+i);
	}

	public void run() {
		bar.entraL();//natura bloccante
		System.out.println(getName()+": sto consumando una birra...");
		try {
			sleep((int)(Math.random()*3000));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(getName()+": sto uscendo dal bar.");
		bar.esceL();
	}
}
