
public class CokeTest {

	public static void main(String[] args) {
		CokeMachineIF c = new CokeMachine();
		Fornitore f = new Fornitore(c);
		f.start();
		for ( int i=0; i<5; i++) {
			Utente u = new Utente(c,i);
			u.start();
		}
	}

}
