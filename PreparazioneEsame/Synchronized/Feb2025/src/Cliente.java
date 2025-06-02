
public class Cliente extends Thread{

	private ProiezioneIF p;
	private String name;
	
	public Cliente(ProiezioneIF p, int i) {
		// TODO Auto-generated constructor stub
		this.p = p;
		name = "Cliente" + i;
	}

	public void run() {
		while(true) {
			try {
				sleep((int)(Math.random()*5000));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			p.prenota(name, ((int)(Math.random()*4)) + 1);
			try {
				sleep((int)(Math.random()*5000));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			p.cancella(name);
		}
	}
}
