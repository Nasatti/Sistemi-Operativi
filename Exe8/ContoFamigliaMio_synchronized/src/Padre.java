
public class Padre extends Thread{

	private ContoIF conto;
	private Figlio[] figlio = new Figlio[10];
	private int somma;
	
	public Padre() {
		// TODO Auto-generated constructor stub
		this.somma = 0;
		setName("Padre");

		conto = new Conto();
		System.out.println("Conto creato");
		
		for(int i = 0; i < 10; i++) {
			figlio[i] = new Figlio(conto, i);
			figlio[i].start();
		}
	}

	public void run() {		
		while(true) {
			somma = (int) (Math.random()*10000);
			conto.aggiungiDenaro(somma);
			try {
				sleep((int)Math.random()*5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
