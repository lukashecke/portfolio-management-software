package Views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Fenster zum einloggen eines Benutzers auf die Datenbank.
 * @author  Lukas Hecke
 * @author  Namandeep Singh
 */
public class LoginWindow extends JFrame {

    /**
     * Startpunkt des Programms.
     * @param args Übergabeparameter über die Kommandozeile (unbenutzt)
     */
    public static void main(String[] args) {
        new LoginWindow();
    }

    // Controls deklarieren
    private Container container;

    private JPanel loginPanel;
    private JTextField inputUserField;
    private JTextField inputPasswordField;
    private JButton loginButton;

    private JLabel logo;

    /**
     * Erzeugt die Komponenten für das LoginWindow.
     * @author  Lukas Hecke
     * @author  Namandeep Singh
     */
    public LoginWindow() {
        super();
        setComponents();
        addListener();
        showWindow("StonksX", 500, 300);
    }

    /**
     * Setzt alle anzuzeuigenden Komponenten im Fenster.
     * @author  Lukas Hecke
     * @author  Namandeep Singh
     *
     */
    private void setComponents() {
        // Container
        container = getContentPane();
        container.setLayout(new GridLayout(1,2));

        // Controls
        loginPanel = new JPanel();
        loginPanel.setLayout(new GridBagLayout());
        inputUserField = new JTextField();
        inputPasswordField = new JTextField();
        loginButton = new JButton("Login");

        logo = new JLabel("Hier später Logo");

        GridBagConstraints c = new GridBagConstraints();
        c.gridwidth = GridBagConstraints.RELATIVE;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1.0;

        c.gridx = 0;
        c.gridy = 1;
        loginPanel.add(inputUserField, c);

        c.gridx = 0;
        c.gridy = 2;
        loginPanel.add(inputPasswordField, c);

        c.gridx = 0;
        c.gridy = 3;
        loginPanel.add(loginButton, c);

        // Zusammenbauen der Seite
        container.add(loginPanel, 0);
        logo.setHorizontalAlignment(SwingConstants.CENTER);
        container.add(logo, 1);
    }

    /**
     * Registriert alle benötigten Listener für das Fenster.
     * @author Lukas Hecke
     */
    private void addListener() {
        // Schließen des Fenster ruft exit() auf
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                exit();
            }
        });

        loginButton.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    // TODO: Login Validierung und DB-Anmeldung anstoßen in Controller
                }
            }
        });
    }

    /** Öffnet das Fenster. Komponenten und Listener müssen bereits initialisiert sein.
     * @author  Lukas Hecke
     * @author  Namandeep Singh
     */
    private void showWindow(String title, int width, int height) {
        setTitle(title);
        pack();
        setSize(width, height);
        setVisible(true);
    }

    /**
     * Kontrolliertes Schließen des Programms.
     * @author Lukas Hecke
     */
    private void exit() {
        //listModel.save();
        System.exit(0);
    }
}
