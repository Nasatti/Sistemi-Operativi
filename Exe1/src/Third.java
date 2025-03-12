public class Third {

	public static void main(String[] args) {
		System.out.println(Thread.currentThread().getName());
			 //Creazione di 10 thread di classe anonima   
		     for(int i=0; i<10; i++){
			      new Thread("" + i){//dentro { } di Thread
			        public void run(){
			          System.out.println("Thread: " + getName() + " running");
			        }
			      }.start();
			    }
			  }
			

}
