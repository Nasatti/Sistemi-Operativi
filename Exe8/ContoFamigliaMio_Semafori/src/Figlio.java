
public class Figlio extends Thread{

	private ContoIF conto;
	int somma;
	
	public Figlio(ContoIF conto, int i) {
		// TODO Auto-generated constructor stub
		this.conto = conto;
		this.somma=0;
		setName("Figlio" + i);
		
	}

	public void run(){
		while(true) {
			somma = (int) (Math.random()*1000);
			conto.prelevoDenaro(somma);
			try {
				sleep((int) (Math.random()*5000));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
