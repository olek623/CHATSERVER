package com.mycompany.chatlabs;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.StringTokenizer;

public class OdczytZPliku {
    
    public LinkedHashMap<String, String> Odczyt() {

        LinkedHashMap<String,String> hm=new LinkedHashMap<String,String>();
             
        
        try { 
            BufferedReader br = new BufferedReader(new FileReader("pytania.txt"));
            String line = br.readLine();
            
         
            while (line != null) {             
              StringTokenizer token = new StringTokenizer (line,";");
           
              String pytanie=token.nextToken();
              String odpowiedz=token.nextToken();
                                    
              System.out.println("Pytanie: "+pytanie+" Odpowiedz: "+odpowiedz);
              
              hm.put(pytanie,odpowiedz );       
              line = br.readLine(); 
            } 
            
                br.close(); //zamknięcie bufora.
                 
        } catch (IOException e) { //błąd wejśćia/wyjścia.
            System.out.println("błąd I O");
        }
        return hm;
    }
    
    
    
}
