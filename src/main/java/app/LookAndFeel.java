package app;

import java.util.prefs.Preferences;
import javax.swing.UnsupportedLookAndFeelException;

public class LookAndFeel {

    public void set() {
        try {
            
            Preferences temas = Preferences.userRoot();
            String t = temas.get("tema", "");
            
            if(t.equals("")) t = "com.formdev.flatlaf.FlatLightLaf";
            
            Class clase = Class.forName(t);
            Object obj = clase.newInstance();
            javax.swing.UIManager.setLookAndFeel((javax.swing.LookAndFeel) obj);
            javax.swing.UIManager.put("Button.minimumWidth", "16");
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            System.out.println(ex);
        }
    }

}
