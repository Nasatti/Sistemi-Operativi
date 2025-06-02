
public class TaskD extends Thread{

private CounterIF c;
	
	public TaskD(CounterIF c, int i) {
		// TODO Auto-generated constructor stub
		this.c = c;
		setName("D-"+i);
	}
	
	public void run() {
		while(true) {
			try {
				sleep((int)(Math.random()*5000));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			c.decrementa();
		}
	}
}
