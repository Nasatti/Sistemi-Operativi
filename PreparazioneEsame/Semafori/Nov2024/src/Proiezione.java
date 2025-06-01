import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Semaphore;

public class Proiezione implements ProiezioneIF {

	private static int CAPIENZA = 10;
	private Date data;
	private float hinzizio, hfine;
	
	private int liberi;
	private int occupati;
	private int nattesa;
	private List<String> prenotazioni;
	
	private Semaphore mutex;
	private Semaphore attesa;
	
	public Proiezione() {
		liberi = CAPIENZA;
		nattesa = occupati = 0;
		mutex = new Semaphore(1);
		attesa = new Semaphore(0, true);
		prenotazioni = new ArrayList<>();
	}
	
	@Override
	public void prenota(String nome, int numPosti) {
		// TODO Auto-generated method stub
		try {
			mutex.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while (numPosti > liberi) {
			nattesa++;
			System.out.println("  B " + nome + " attesa per " + numPosti + "\toccupati: " + occupati + "\tliberi: " + liberi + "\tlista: " + prenotazioni.size()+ "\tnattesa: " + nattesa);
			try {
				mutex.release();
				attesa.acquire();
				mutex.acquire();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		prenotazioni.add(nome+"-"+numPosti);
		occupati += numPosti;
		liberi -= numPosti;
		System.out.println("A   " +nome + " aggiunti " + numPosti + "\t\toccupati: " + occupati + "\tliberi: " + liberi + "\tlista: " + prenotazioni.size()+ "\tnattesa: " + nattesa);
		mutex.release();
	}

	@Override
	public void cancella(String nome) {
		// TODO Auto-generated method stub
		try {
			mutex.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
			occupati -= n;
			liberi += n;
			System.out.println("   X" + nome + " cancellato n " + n + "\toccupati: " + occupati + "\tliberi: " + liberi + "\tlista: " + prenotazioni.size()+ "\tnattesa: " + nattesa);
			if(nattesa>0) {	
				attesa.release();
				nattesa--;
			}
		}
		mutex.release();
	}

}
