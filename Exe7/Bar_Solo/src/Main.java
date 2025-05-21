
public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		BarIF bar = new Bar();//oggetto condiviso
		System.out.println("Bar creato e aperto");
		
		Barista b = new Barista(bar);
		ClienteOspite[] co = new ClienteOspite[10];
		ClienteLocale[] cl = new ClienteLocale[10];
		for(int i = 0; i < 10; i++) {
			co[i] = new ClienteOspite(bar, i);
			cl[i] = new ClienteLocale(bar, i);
			
			co[i].start();
			cl[i].start();
		}
		b.start();
	}

}
