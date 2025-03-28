
public class Factory {

	public static void main(String[] args) {
		//CokeMachineIf m = new CokeMachine1();
		CokeMachineIf m = new CokeMachine1();
		
		Producer s = new Producer(m);
		s.start();

		for(int i=0; i<5; i++) {
			User u = new User(m,i);
			u.start();
		}
	}
}
