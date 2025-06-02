
public class TaskI extends Thread{

	private CounterIF c;
	
	public TaskI(CounterIF c, int i) {
		// TODO Auto-generated constructor stub
		this.c = c;
		setName("I-"+i);
	}
	
	public void run() {
		while(true) {
			try {
				sleep((int)(Math.random()*5000));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			c.incermenta();
		}
	}
}
