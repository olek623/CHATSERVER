package com.mycompany.chatlabs;

import java.io.Serializable;


public class Odpowiedz implements Serializable{
    
    String imie;
    String odpowiedz;
    
    public Odpowiedz(String imie,String odpowiedz)
    {
        this.imie=imie;
        this.odpowiedz=odpowiedz;
        
    }
    
    public String getimie()
    {
        return imie;
    }

    public String getodpowiedz()
    {
        return odpowiedz;
    } 
}
