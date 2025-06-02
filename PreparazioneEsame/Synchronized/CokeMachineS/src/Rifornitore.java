
public class Rifornitore extends Thread{

	private CokeMachIF coke;
	public Rifornitore(CokeMachIF coke) {
		// TODO Auto-generated constructor stub
		this.coke = coke;
		setName("Rifornitore");
	}

	public void run() {
		while(true) {
			coke.rifornisci();
		}
	}
}
