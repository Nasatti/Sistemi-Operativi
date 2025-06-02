
public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		CounterIF c = new Counter();
		
		TaskI[] ti = new TaskI[3];
		TaskD[] td = new TaskD[3];
		
		for(int i = 0; i < 3; i++) {
			ti[i] = new TaskI(c, i);
			td[i] = new TaskD(c, i);
			
			ti[i].start();
			td[i].start();
		}
	}
}
