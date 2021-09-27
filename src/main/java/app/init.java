package app;

import Controller.CInicioSesion;

public class init {

    public static void main(String[] args) {
        
        new LookAndFeel().set();
        
        new CInicioSesion(new Vista.VLogin());
       
    }

}
