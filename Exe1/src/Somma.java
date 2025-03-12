
import java.io.*;

/**
 * Esempio Somma: Programma multithread in Java che esegue una 
 * funzione di somma tra due numeri interi inseriti dall’utente. 
 * Creare un thread separato per effettuare la somma e restituire
 * il risultato in una variabile (globale) condivisa con il thread
 * principale. Il thread principale deve stampare il risultato a video.
 * @author Patrizia Scandurra, Paolo Sangregorio
 *
 *
 *Prima soluzione: Somma e Sommatore sono due classi distinte e separate
 */

public class Somma {
	
	public static void main(String[] args) {
		//Definizione dello shared object che conterrà un intero
		//(vedi definizione sotto della classe involucro HoldInteger)
		HoldInteger result = new HoldInteger(); 
		
		//La lettura di a e b può avvenire in modo più o meno elegante, ad
		//es. da linea di comando con:
		//int a = Integer.parseInt(args[0]);
	    //int b = Integer.parseInt(args[1]);
		//oppure tramite lo stream System.in.
		//Quì ci limitiamo ad assegnarli direttamente con:
		int a = 3;
		int b = 5; 
		
		//Creazione e avvio del thread sommatore. I valori int da 
		//sommare ed il riferimento allo shared object in cui ci si aspetta 
		//il risultato vengono passati come parametri al costruttore del 
		//thread Sommatore 
		Thread doSum = new Thread(new Sommatore(result, a, b));
		doSum.start();
		try {
			//Il main thread si sincronizza sulla terminazione del processo 
			//sommatore
			doSum.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("Somma "+ a + " + " + b + " = " + result.getVal());
	}
}//End main class Somma

class Sommatore implements Runnable {
	private HoldInteger result; //copia privata del riferimento allo shared object
	int a, b;
	
	//Metodo costruttore del thread Sommatore
	public Sommatore(HoldInteger result, int a, int b) {
		this.result = result;
		this.a = a;
		this.b = b;
	}
	
	public void run() {
		result.setVal(a+b);
	}
}//End class Sommatore

//Definizione della classe involucro con metodi get/set
//per la condivisione di un intero (senza sincronizzazione!)
class HoldInteger {
	int val;
	public void setVal(int newVal)
	{
		val = newVal;
	}
	public int getVal()
	{
		return val;
	}
}//End class HoldInteger