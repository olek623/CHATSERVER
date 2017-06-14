package com.mycompany.chatlabs;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public final class SerwerKonf extends Thread {

    BlockingQueue<Socket> kolejka = new ArrayBlockingQueue<>(1);
    LinkedHashMap<String, String> hm = new LinkedHashMap<String, String>();
    OdczytZPliku odczyt = new OdczytZPliku();

    ArrayList<String> pytania;
    int iloscpytan;
    int licznik = 0;
    Boolean koniec = false;

    String imie;
    String odpowiedz;
    String pytanie;
    SerwerFrame fs = new SerwerFrame();

    @Override
    public void run() {
       // fs.setVisible(true);
        System.out.println("serwer uruchomiony");

        Konsument konsument = new Konsument(kolejka);
        new Thread(konsument).start();

        OdczytZHM();
        SERWER();
    }

    private void OdczytZHM() {
        hm = odczyt.Odczyt();
        pytania = new ArrayList<String>(hm.keySet());
        iloscpytan = pytania.size();

        pytanie = pytania.get(licznik);
        fs.TextArea.append("Pytanie brzmi: " + pytanie);
    }

    public void SERWER() {
        try {
            ServerSocket serverSock = new ServerSocket(5000);
            while (true) {

                Socket gniazdoKlienta = serverSock.accept();
                kolejka.put(gniazdoKlienta);

            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Błąd serwera", "Błąd", 0);
        } catch (InterruptedException ex) {
            Logger.getLogger(SerwerKonf.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public class Konsument implements Runnable {

        private BlockingQueue<Socket> kolejka;
        private Odpowiedz odp;
        private Socket soc;

        Konsument(BlockingQueue<Socket> kolejka) {
            this.kolejka = kolejka;
        }

        @Override
        public void run() {

            while (true) {
                try {
                    soc = kolejka.take();

                    InputStream insocket = soc.getInputStream();
                    ObjectInputStream in = new ObjectInputStream(insocket);

                    Odpowiedz POdpowiedz = (Odpowiedz) in.readObject();

                    imie = POdpowiedz.getimie();
                    odpowiedz = POdpowiedz.getodpowiedz().toUpperCase();

                    if (koniec == false) {
                        if (odpowiedz.equals(hm.get(pytanie))) {
                            licznik++;
                            fs.TextArea.append("\nPrawidłowo odpowiedział: " + imie + " Odpowiedz:" + odpowiedz);
                            fs.TextArea.append("\n-----------------------------------------------------------------------");
                            if (licznik < iloscpytan) {
                                pytanie = pytania.get(licznik);
                                fs.TextArea.append("\nKolejne pytanie brzmi: " + pytanie);
                            } else {
                                fs.TextArea.append("\nGra zakończona!");
                                koniec = true;
                            }

                        } else {
                            fs.TextArea.append("\nBłędnie odpowiedział: " + imie);
                        }
                    } else {
                        fs.TextArea.append("\nGra zakończona!");
                    }

                } catch (InterruptedException | IOException | ClassNotFoundException ex) {
                    Logger.getLogger(SerwerKonf.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

    }
}
