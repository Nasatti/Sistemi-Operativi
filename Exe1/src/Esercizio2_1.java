import java.util.Random;
import java.util.Scanner;

public class Esercizio2_1 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Counter c = new Counter();
		Scanner s = new Scanner(System.in);
		
		System.out.println("Inserisci un numero");
		int n = s.nextInt();
		
		Thread t = new TaskCounter(c, n);
		t.start();
		try {
			t.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}

class Counter{
	private int count;
	
	public Counter(){
		count = 0;
	}
	
	public void next() {
		count++;
	}
	
	public int getCount() {
		return count;
	}
}

class TaskCounter extends Thread{
	private Counter c;
	private int n;
	
	public TaskCounter(Counter c, int n){
		this.c = c;
		this.n = n;
	}
	
	public void run() {
		int num = new Random().nextInt(n - n/2 + 1) + n/2;
		System.out.println("numero randomico: " + num);
		for(int i = 0; i < num; i++) {
			c.next();
		}
		System.out.println("count finale" + c.getCount());
	}
	
	public Counter getc() {
		return c;
	}
}