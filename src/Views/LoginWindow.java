package Views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LoginWindow extends JFrame {

    public static void main(String[] args) {
        new LoginWindow();
    }

    // Controls
    private JPanel loginPanel;
    private JTextField inputUserField;
    private JTextField inputPasswordField;
    private JButton loginButton;

    private JLabel logo;

    public LoginWindow() {
        super();
        setTitle("StonksX");

        // Container
        Container container = getContentPane();
        container.setLayout(new GridLayout(1,2));

        // Controls
        loginPanel = new JPanel();
        loginPanel.setLayout(new GridBagLayout());
        inputUserField = new JTextField();
        inputPasswordField = new JTextField();
        loginButton = new JButton("Login");

        logo = new JLabel("Hier später Logo");

        GridBagConstraints c = new GridBagConstraints();
        c.gridwidth = java.awt.GridBagConstraints.RELATIVE;
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

        // Zusammenbau der Seite
        container.add(loginPanel, 0);
        logo.setHorizontalAlignment(SwingConstants.CENTER);
        container.add(logo, 1);

        // Listener
        addListener(loginButton);

        // Anzeigen
        pack();
        setSize(500, 300);
        setVisible(true);
    }

    private void addListener(JButton loginButton) {
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

    private void exit() {
        //listModel.save();
        System.exit(0);
    }
}
