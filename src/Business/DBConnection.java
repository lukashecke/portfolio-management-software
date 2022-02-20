package Business;

import Helper.ConsoleHelper;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Singleton-Klasse für die Datenbankverbindung.
 * Verbindung mittels setConnection herstellen.
 */
public class DBConnection {
    /**
     * Singleton-Instanz
     */
    private static DBConnection instance = null;

    // public default Konstruktor verhindern
    private DBConnection() { }

    /**
     * Singleton-Aufruf.
     * @return Singleton-Instanz
     * @author Lukas Hecke
     */
    public static DBConnection getInstance()
    {
        if (instance == null) {
            instance = new DBConnection();
        }
        return instance;
    }

    /**
     * Datenbankverbindung
     */
    public Connection connection;

    /**
     * Baut eine Datenbankverbindung zur übergebenen Datenbank auf.
     * Eine .properties Datei mit dem Namen der dieser Datenbank muss dafür im Ordner properties hinterlegt sein.
     * @param user Benutzername
     * @param password Passwort
     * @throws SQLException wenn Datenbankverbindung fehlschlägt
     * @throws ClassNotFoundException wenn Klasse für JDBC-Treiber nicht vorhanden
     * @throws IOException wenn Properties Datei nicht geladen werden kann
     * @author Lukas Hecke
     */
    public void setConnection(final String user, final String password) throws SQLException, ClassNotFoundException, IOException {
        Properties properties = new Properties();
        properties.load(new FileReader("./properties/DB.properties"));

        // Laden des JDBC Treibers für MySQL
        Class.forName(properties.getProperty("DBDRIVER"));
        System.out.println("MySQL JDBC-Treiber geladen!");

        Connection connection = DriverManager.getConnection(
                properties.getProperty("DBURL"), user, password);
        if (connection != null) {
            System.out.println("Connection erfolgreich aufgebaut!");
            ConsoleHelper.printDatabaseMetaData(connection);
        } else {
            System.out.println("Connection konnte nicht aufgebaut werden!");
            connection = null;
        }

        this.connection = connection;
    }

    /**
     * Schließt Verbindung zur Datenbank.
     * @author Lukas Hecke
     */
    public void closeConnection() {
        try {
            connection.close();
            System.out.println("Datenbank-Verbindung wurde wieder geschlossen!");
        } catch (Exception e) {
            System.out.println("Datenbank-Verbindung konnte nicht geschlossen werden!");
        }
        this.connection = null;
    }
}
