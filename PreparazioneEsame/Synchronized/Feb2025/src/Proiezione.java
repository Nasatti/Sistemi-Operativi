import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class Proiezione implements ProiezioneIF {

	private static int CAPIENZA = 10;
	private Date data;
	private float hinzizio, hfine;
	
	private int liberi;
	private int nattesa;
	private List<String> prenotazioni;
	
	
	public Proiezione() {
		liberi = CAPIENZA;
		nattesa = 0;
		prenotazioni = new ArrayList<>();
	}
	
	@Override
	public synchronized void prenota(String nome, int numPosti) {
		// TODO Auto-generated method stub
		nattesa++;
		while(numPosti > liberi) {
			try {
				System.out.println("  B " + nome + " attesa per " + numPosti + "\tliberi: " + liberi + "\tlista: " + prenotazioni.size()+ "\tnattesa: " + nattesa);
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		nattesa--;
		liberi -= numPosti;
		prenotazioni.add(nome+"-"+numPosti);
		System.out.println("A   " +nome + " aggiunti " + numPosti + "\t\tliberi: " + liberi + "\tlista: " + prenotazioni.size()+ "\tnattesa: " + nattesa);
	}

	@Override
	public synchronized void cancella(String nome) {
		// TODO Auto-generated method stub
		Iterator<String> it = prenotazioni.iterator();
		int n = -1;
		boolean verifica = false;
		while(it.hasNext()) {
			String s = it.next();
			if (s.contains(nome)) {
				String[] split = s.split("-");
				n = Integer.parseInt(split[1]);
                it.remove();
                verifica = true;
                break;
            }
		}
		if(verifica) {
			liberi += n;
			System.out.println("   X" + nome + " cancellato n " + n + "\tliberi: " + liberi + "\tlista: " + prenotazioni.size()+ "\tnattesa: " + nattesa);
			if(nattesa > 0)
				notifyAll();
		}
	}

}
