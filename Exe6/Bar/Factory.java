
public class Factory {

	public static void main(String[] args) {
		
		BarIf bar = new Bar(); //creazione dell'oggetto condiviso
		System.out.println("BAR APERTO!");
		//Creiamo i vari tipi di thread
		Barista barista = new Barista(bar);
		ClienteOspite [] CO = new ClienteOspite[10];
		ClienteLocale [] CL = new ClienteLocale[10];
		for (int i=0; i<10; i++) {
			CO[i]= new ClienteOspite(bar,i);
			CL[i]= new ClienteLocale(bar,i);
		}
		//Avvio dei thread
		for (int i=0; i<10; i++) {
			CO[i].start();
			CL[i].start();
		}
		barista.start();
			
	}

}
