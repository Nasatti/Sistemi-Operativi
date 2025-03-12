public class Simboli { 
 /**
 * @param args
 */
public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Thread t1= new ThreadSimbolo('*');
		Thread t2= new ThreadSimbolo('#');

	    t1.start();
	    t2.start();
	}
}

class ThreadSimbolo extends Thread
{
	char s;
	
	public ThreadSimbolo(char s){this.s=s; }
	
	public void run() {
	int i=1; //Ogni thread mantiene una propria copia della variabile i; in questo modo possiamo avere su 
	          //una stessa riga 49 simboli di un thread + 50 (99 in totale) dell'altro al pi�, e al minimo un simbolo solo.
	 while(true) { 
		 if (i==50) {System.out.println(s); i=1;}
		 else { System.out.print(s); i++;}
		 
	 }
   }
}
