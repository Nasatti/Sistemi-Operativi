
public class Fornitore extends Thread{
	CokeMachineIF c;
	
	public Fornitore(CokeMachineIF c) {
		this.c = c;
		this.setName("Thread-fornitore");
	}

	public void run() {
		while(true)
			c.rifornisci(); //natura bloccante
	}
}
