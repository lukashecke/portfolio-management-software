package Views;

import Business.DBConnection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Fenster zum Einloggen eines Benutzers auf der Datenbank.
 */
public class LoginWindow extends BaseWindow {

    /**
     * Startpunkt des Programms.
     * @param args Übergabeparameter über die Kommandozeile (unbenutzt)
     * @author Lukas Hecke
     * @author Namandeep Singh
     */
    public static void main(String[] args) {
        new LoginWindow();
    }

    // Controls deklarieren
    private Container container;

    private JPanel loginPanel;
    private JTextField inputUserField;
    private JPasswordField inputPasswordField;
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
        showWindow("Firestocks", 500, 300);
    }

    /**
     * Setzt alle anzuzeigenden Komponenten im Fenster.
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
        inputPasswordField = new JPasswordField();
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

        inputUserField.requestFocus();

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
        inputUserField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    inputPasswordField.requestFocus();
                }
            }
        });

        inputPasswordField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    loginUser(inputUserField.getText(), new String(inputPasswordField.getPassword()));
                }
            }
        });

        loginButton.addActionListener((e) -> loginUser(inputUserField.getText(), new String (inputPasswordField.getPassword())));
    }

    /**
     * Versucht mit den übergebenen Parametern eine Verbindung mit der Datenbank herzustellen.
     * @param user Benutzer
     * @param password Passwort
     * @author Lukas Hecke
     */
    private void loginUser(String user, String password) {
        try {
            DBConnection.getInstance().setConnection(user, password);
            setVisible(false);
            BaseWindow portfolioWindow = new PortfolioWindow();
            portfolioWindow.showWindow("Portfolio",1000,800);
            dispose();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            JOptionPane.showMessageDialog(container, "Falscher Benutzername, oder Passwort!");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(container, "Kein JDBC-Treiber gefunden!");
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(container, "Properties Datei nicht gefunden!");
        }
    }
}
