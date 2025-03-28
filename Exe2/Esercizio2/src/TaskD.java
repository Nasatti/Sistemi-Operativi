
public class TaskD extends Thread{
	Counter c;
	
	public TaskD(Counter c, int i) {
		this.c = c;
		this.setName("TaskD"+i);
	}
	
	public void run() {
		while(true){
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
