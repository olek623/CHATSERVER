package com.mycompany.chatlabs;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import javax.swing.JOptionPane;

public class KlientKonf {

    String imie;
    String odpowiedz;
    Socket socket;

    public KlientKonf(String imie, String odpowiedz) {
                this.imie = imie;
        this.odpowiedz = odpowiedz;
        konfigurujKomunikacje();
        
    }

    private void konfigurujKomunikacje() {
        try {
            socket = new Socket("127.0.0.1", 5000);
            Odpowiedz POdpowiedz = new Odpowiedz(imie, odpowiedz);
            
            OutputStream outsocket = socket.getOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(outsocket);
            out.writeObject(POdpowiedz);
            
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Wlącz serwer!", "Błąd", 0);
        }

    }
}
