class Worker extends Thread
{
   public void run() {
	   /*try {
		Thread.sleep(3000);//serve per ex3
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}*/
	   System.out.println(getName() + " Thread ausiliario: Hello world!");
   }
}

public class First
{
   public static void main(String args[]) {
      Thread runner = new Worker();
      
      runner.start();
      runner.setName("Pippo");

	System.out.println(Thread.currentThread().getName() + " Hello world!");
      
   }
}



