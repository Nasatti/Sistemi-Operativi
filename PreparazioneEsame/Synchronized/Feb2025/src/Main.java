
public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ProiezioneIF p = new Proiezione();
		
		Cliente[] c = new Cliente[5];
		
		for(int i = 0; i < 5; i++) {
			c[i] = new Cliente(p, i);
			
			c[i].start();
		}
	}

}
