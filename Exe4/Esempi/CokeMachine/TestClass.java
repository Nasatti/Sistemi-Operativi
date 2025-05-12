
public class TestClass {

	public static void main(String[] args) {
		//CokeMachineIf machine = new CokeMachine();
		//CokeMachineIf machine = new CokeMachine2();
		//CokeMachineIf machine = new CokeMachine3();
		//CokeMachineIf machine = new CokeMachine4();
		CokeMachineIf machine = new CokeMachine5(); 
		
		Producer p = new Producer(machine);
		p.start();
		for(int i = 0; i<5; i++) {
			User u = new User(machine,i+1);
			u.start();
		}

	}

}
