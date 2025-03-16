import java.util.Scanner;

public class Esercizio5 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner s = new Scanner(System.in);
		System.out.println("Quanti Thread desideri far gareggiare?");
		int n = s.nextInt();
		Thread[] t = new Thread[n];
		for(int i = 0; i < n; i++) {
			t[i] = new Corridore();
			t[i].setName("T"+i);
		}
		
		for(int i = 0; i < n; i++) {
			t[i].start();
		}

		
	}

}

class Corridore extends Thread
{
   public void run() {
	   for(int i = 0; i < 100; i++) {
		   System.out.println(getName() + ": " + i);
	   }
	   System.out.println(getName() + " FINITO CORSA");
   }
}