package com.mycompany.chatlabs;


public class Main {
    
    public static void main(String[] args) throws InterruptedException {

        //run
//        java.awt.EventQueue.invokeLater(() -> {
//            new NewJFrame().setVisible(true);
//        });
              
        SerwerKonf serwer = new SerwerKonf();
        serwer.start();
        

        for (int i = 0; i < 3; i++) {
            Thread thread = new Thread(() -> {         
                KlientFrame klient = new KlientFrame();      
            });          
            Thread.sleep(500);
            thread.start();
        }

    }

}
//