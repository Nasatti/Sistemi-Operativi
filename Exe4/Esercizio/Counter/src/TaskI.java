
public class TaskI extends Thread{
	Counter c;
	
	public TaskI(Counter c, int i) {
		this.c = c;
		this.setName("TaskI"+i);
	}
	
	public void run() {
		while(true){
			try {
				sleep((int)(Math.random()*5000));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			c.incrementa();
		}
	}
}
