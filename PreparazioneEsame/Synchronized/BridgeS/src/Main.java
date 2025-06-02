
public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		BridgeIF b = new Bridge();
		
		CarRed[] cr = new CarRed[5];
		CarBlu[] cb = new CarBlu[5];
		for(int i = 0; i < 5; i++) {
			cr[i] = new CarRed(b,i);
			cb[i] = new CarBlu(b,i);
			
			cr[i].start();
			cb[i].start();
		}
	}

}
