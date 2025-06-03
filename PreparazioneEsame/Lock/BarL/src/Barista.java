
public class Barista extends Thread{

	BarIF bar;
	
	public Barista(BarIF bar) {
		// TODO Auto-generated constructor stub
		this.bar = bar;
		setName("Barista");
	}
	
	public void run(){
		while(true) {
			try {
				sleep((int)(Math.random()*5000));
			}
			catch (InterruptedException e){
				e.printStackTrace();
			}
			bar.chiusura();
			try {
				sleep((int)(Math.random()*3000));
			}
			catch(InterruptedException e) {
				e.printStackTrace();
			}
			bar.apertura();
		}
	}
}
