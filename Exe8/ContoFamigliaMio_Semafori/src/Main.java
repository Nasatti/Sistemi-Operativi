//SEMAFORI

/*
 * Risorsa condivisa:
 * - conto
 * 
 * 
 * Thread:
 * - Padre --> apre un conto, condivide a figli, paga paghette, aggiunge denaro
 * - Figli --> prelevare denaro, se conto < 0 chiama padre
 * 
 */


public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Padre padre = new Padre();
		padre.start();
	}

}

























