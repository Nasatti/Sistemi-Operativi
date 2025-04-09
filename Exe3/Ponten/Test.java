
public class Test {

	public static void main(String[] args) {
		//Creazione dell'oggetto condiviso
		Bridge ponte = new UnfairSafeBridgeSem();
		//Bridge ponte = new FairSafeBridgeSem();
		
		//Creazione delle auto rosse e blu
		for (int i=0; i<10; i++) {
			new RedCar(ponte,"R"+(i+1)).start();
			new BlueCar(ponte,"B"+(i+1)).start();
		}
	}

}
