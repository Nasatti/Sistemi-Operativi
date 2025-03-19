import java.util.Random;

public class Esercizio2_2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
		int[] array = new int[50];
		for(int i = 0; i < 50; i++) {
			int num = new Random().nextInt(101);
			array[i] = num;
		}

		Array a = new Array(array);
		
		Thread[] t = new Thread[5];
		for(int i = 0; i < 5; i++) {
			t[i] = new Sommatore2(i, a);
			t[i].start();
			try {
				t[i].join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}

class Sommatore2 extends Thread{
	int n;
	int[] array;
	Sum sum;
	
	Sommatore2(int n, Array a){
		this.n = n;
		this.array = a.getArrayThread(n);
		this.sum = new Sum();
	}
	
	public void run() {
		for(int i = 0; i < 10; i++) {
			sum.add(array[i]);
		}
		System.out.println(getName() + ": Somma parziale = " + sum.getSum());
	}
	
}
class Sum{
	int sum;
	Sum(){
		this.sum = 0;
	}
	public int getSum() {
		return sum;
	}
	
	public void add(int n) {
		sum += n;
	}
}
class Array{
	int[] array = new int[50];
	
	Array(int[] array) {
		this.array = array;
	}
	
	public int[] getArrayThread(int n) {
		int[] a = new int[10];
		switch(n){
		case 0:
			for(int i = 0; i < 10; i++) {
				a[i] = array[i];
			}
			break;
		case 1:
			for(int i = 10; i < 20; i++) {
				a[i-10] = array[i];
			}
			break;
		case 2:
			for(int i = 20; i < 30; i++) {
				a[i-20] = array[i];
			}
			break;
		case 3:
			for(int i = 30; i < 40; i++) {
				a[i-30] = array[i];
			}
			break;
		case 4:
			for(int i = 40; i < 50; i++) {
				a[i-40] = array[i];
			}
			break;
		}
		return a;
	}
}