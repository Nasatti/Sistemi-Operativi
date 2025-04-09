
public class BlueCar extends Thread{

	Bridge ponte; //rieferimento all'oggetto condiviso
	
	public BlueCar(Bridge p, String name) {
		ponte = p;
		setName(name);
	}
	
	public void run() {
		
		try {
			sleep((int) (Math.random()*2000));//simula l'avvicinamento al ponte
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//l'auto vuole entrare nel ponte
		ponte.blueEnter();
		
		//l'auto sta attraversando il ponte
		try {
			sleep((int) (Math.random()*3000));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		//l'auto vuole uscire dal ponte
		ponte.blueExit();
		
	}
	
	
}
