
/**
 * Esempio Somma: Programma multithread in Java che esegue una 
 * funzione di somma tra due numeri interi inseriti dall�utente. 
 * Creare un thread separato per effettuare la somma e restituire
 * il risultato in una variabile (globale) condivisa con il thread
 * principale. Il thread principale deve stampare il risultato a video.
 * @author Patrizia Scandurra, Paolo Sangregorio
 *
 *
 *Seconda soluzione: Sommatore � dichiarata come classe interna 
 *a Somma_bis e pertanto vede (e condivide) tutto ci� che � dichiarato 
 *all'interno della classe Somma_bis
 */


public class Somma_bis {
	private int result;//condiviso com t sommatore
	
	//Creazione e avvio del thread figlio "on the fly" per fare la somma.
	//Il risultato viene posto direttamente dal thread figlio 
	//nell'attributo privato result di Somma_bis (vedi sopra).
	public void doSomma(int a, int b)
	{
		//Passaggio dei soli valori interi da sommare
		Thread sommatore = new Thread(new Sommatore(a, b));
		sommatore.start();
		try {
			sommatore.join(); 
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}
	}
	
	
	//Inner class:classe annidata--> accesso a tutte le variabili della classe esterna-->sconsigliate
	public class Sommatore implements Runnable {		
		int a, b;
		
		public Sommatore(int a, int b) {
			this.a = a;
			this.b = b;
		}
		
		public void run() {
			result = a + b;
		}
	}//End classe Sommatore
	
	
	public static void main(String[] args) {
		//Creiamo un oggetto di tipo Somma_bis ed invochiamo
		//su di esso il metodo doSomma(a,b) per fare la somma 
		//ad es. di 3 e 5, e stamparla a video
		Somma_bis e = new Somma_bis();
		e.doSomma(3,5);
		System.out.println("3 + 5 = " + e.result);
	}
	
}//End classe Somma_bis