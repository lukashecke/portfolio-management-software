package Views;

import Business.DBConnection;
import Controllers.PortfolioWindowController;
import com.formdev.flatlaf.FlatLightLaf;

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
        FlatLightLaf.setup(); // Theme
        new LoginWindow();
    }

    // Controls deklarieren
    private Container container;

    private JPanel loginPanel;
    private JPanel imagePanel;
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
        setResizable(false);
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

        imagePanel = new JPanel();
        imagePanel.setLayout(new GridLayout());

        inputUserField = new JTextField();
        inputPasswordField = new JPasswordField();
        loginButton = new JButton("Login");

        Image rawImage = new ImageIcon(getClass().getClassLoader().getResource("resources/Logo_mit_Text_Login.png")).getImage();
        Image renderedImage = rawImage.getScaledInstance(200, 189, Image.SCALE_SMOOTH);
        ImageIcon image = new ImageIcon(renderedImage);
        logo = new JLabel(image);
        logo.setHorizontalAlignment(SwingConstants.CENTER);
        imagePanel.add(logo);

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
        container.add(imagePanel, 1);
    }

    /**
     * Registriert alle benötigten Listener für das Fenster.
     * @author Lukas Hecke
     * @author Namandeep Singh
     */
    private void addListener() {
        inputUserField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    loginUser(inputUserField.getText(), new String (inputPasswordField.getPassword()));
                }
            }
        });

        inputPasswordField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    inputPasswordField.selectAll();
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

            PortfolioWindow portfolioWindow = new PortfolioWindow();
            PortfolioWindowController portfolioWindowController = new PortfolioWindowController(portfolioWindow);
            portfolioWindowController.showWindow("Portfolio",1000,800);
            dispose();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            // Genauere Unterscheidung notwendig
            var exceptionHash = throwables.getSQLState().hashCode();
            switch (exceptionHash) {
                // Unknown database 'firestocks'
                case 49560306 -> JOptionPane.showMessageDialog(container, "Bitte vergewissern Sie sich, dass die Datenbank auf Ihrem Gerät eingespielt ist.", "Datenbank nicht gefunden", JOptionPane.ERROR_MESSAGE);
                // Access denied for user ... (using password: YES)
                case 47892010 -> JOptionPane.showMessageDialog(container, "Bitte überprüfen Sie den Benutzernamen und das Passwort.", "Falsche Anmeldedaten", JOptionPane.WARNING_MESSAGE);
                // Sonstige Fehler
                default -> JOptionPane.showMessageDialog(container, throwables.getMessage(), "Schwerliegender Fehler", JOptionPane.ERROR_MESSAGE);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(container, "Kein JDBC-Treiber gefunden!");
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(container, "Properties Datei nicht gefunden!");
        }
    }
}
