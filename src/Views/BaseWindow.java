package Views;

import Business.DBConnection;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Basisklasse mit erweiterter Funktionalität für alle Fenster.
 */
public class BaseWindow extends JFrame {
    public BaseWindow() {
        super();
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                exit();
            }
        });
    }
    /**
     * Öffnet das Fenster.
     * Alle Komponenten sollten bereits initialisiert sein.
     * @author  Lukas Hecke
     * @author  Namandeep Singh
     */
    protected void showWindow(String title, int width, int height) {
        setTitle(title);
        pack();
        setSize(width, height);
        setVisible(true);
    }

    /**
     * Kontrolliertes Schließen des Programms.
     * Schließt automatisch die Datenbankverbindung mit.
     * @author Lukas Hecke
     */
    private void exit() {
        DBConnection.getInstance().closeConnection();
        System.exit(0);
    }
}