class Worker2 implements Runnable
{
   public void run() {
      System.out.println(Thread.currentThread().getName() + " Thread ausiliario: hello world!");
   }
}
 
public class Second
{
   public static void main(String args[]) {
      //Runnable runner = new Worker2();
      //Thread thrd = new Thread(runner); --> stessa cosa, più economico quello sotto
	   Thread thrd = new Thread(new Worker2());
       thrd.setName("Coca");
	   thrd.start();

	System.out.println("Thread principale: Hello world!");
   }
}
