
public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		BarIF bar = new Bar();
		
		Barista b = new Barista(bar);
		b.start();
		
		ClienteO[] co = new ClienteO[5];
		ClienteL[] cl = new ClienteL[5];
		
		for(int i = 0; i < 5; i++) {
			co[i] = new ClienteO(bar, i);
			cl[i] = new ClienteL(bar, i);
			
			co[i].start();
			cl[i].start();
		}
	}

}
