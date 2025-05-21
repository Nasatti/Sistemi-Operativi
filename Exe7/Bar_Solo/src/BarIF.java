/*
 * Ruoli:
 * 0 - tifosi ospiti
 * 1 - tifosi locali
 * barista
 */
public interface BarIF {
public void entraO();//entra ospite
public void entral();//entra locale
public void esceo();//esce ospite
public void escel();//esce locale
public void inizio_chiusura();//barista chiude il locale per pulire
public void fine_chiusura();//barista riapre il bar
}
