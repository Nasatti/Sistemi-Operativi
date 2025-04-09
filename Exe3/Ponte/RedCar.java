
public class RedCar extends Thread{

	Bridge ponte; //rieferimento all'oggetto condiviso
	
	public RedCar(Bridge p, String name) {
		ponte = p;
		setName(name);
	}
	
	public void run() {
		
	    try {
			sleep((int) (Math.random()*2000));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//l'auto vuole entrare nel ponte
		ponte.redEnter();
		
		//l'auto sta attraversando il ponte
		
		try {
			sleep((int) (Math.random()*3000));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		//l'auto vuole uscire dal ponte
		ponte.redExit();
		
	}

}
