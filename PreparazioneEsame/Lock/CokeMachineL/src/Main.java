
public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		CokeMachIF coke = new CokeMachine();
		Rifornitore r = new Rifornitore(coke);
		r.start();
		Utente[] u = new Utente[5];
		for(int i = 0; i < 5; i++) {
			u[i] = new Utente(coke, i);
			u[i].start();
		}
	}

}
