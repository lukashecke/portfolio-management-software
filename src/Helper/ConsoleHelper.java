package Helper;

import Views.PortfolioWindow;
import org.nocrala.tools.texttablefmt.Table;

import java.awt.*;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Helferklasse für Konsolenausgaben.
 */
public class ConsoleHelper {
    /**
     * Gibt das resultset als formatierte Tabelle aus.
     * @param resultSet resultset mit den Ergebnissen
     * @author Lukas Hecke
     * @throws SQLException wenn Verbindung zur Datenbank fehlschlägt
     */
    public static void printResultSet(final ResultSet resultSet) throws SQLException {
        System.out.println();
        System.out.println(resultSet.getStatement());

        resultSet.beforeFirst();
        final int columnCount = resultSet.getMetaData().getColumnCount();
        final Table t = new Table(columnCount);
        for (int i = 1; i <= columnCount; i++) {
            t.addCell(resultSet.getMetaData().getColumnName(i));
        }
        while (resultSet.next()) {
            for (int i = 1; i <= columnCount; i++) {
                final Object object = resultSet.getObject(i);
                t.addCell(object != null ? object.toString() : "NULL");
            }
        }
        System.out.println(t.render());

        PortfolioWindow portfolioWindow = null;
        if (true) {
            Window[] windows = Window.getWindows();

            for (Window window : windows)
                if (window.getClass() == PortfolioWindow.class)
                    portfolioWindow = (PortfolioWindow) window;
        }
        if (portfolioWindow != null) {
            if (portfolioWindow.loggingOutput != null) {
                portfolioWindow.loggingOutput.setText(portfolioWindow.loggingOutput.getText() + "\n\n\n" + resultSet.getStatement() + "\n"+ t.render());
                portfolioWindow.loggingOutput.revalidate();
                portfolioWindow.loggingOutput.repaint();
            }
        }
    }

    /**
     * Gibt Metadaten der Datenbank aus.
     * @param connection Aufgebaute Verbindung zur Datenbank
     * @author Lukas Hecke
     * @throws SQLException wenn Verbindung zur Datenbank fehlschlägt
     */
    public static void printDatabaseMetaData(Connection connection) throws SQLException {
        DatabaseMetaData dbmd = connection.getMetaData();
        System.out.println("Metadaten der Datenbank:");
        System.out.println("DB          :" + dbmd.getDatabaseProductName());
        System.out.println("Version     :" + dbmd.getDatabaseProductVersion());
        System.out.println("Treiber     :" + dbmd.getDriverName() );
        System.out.println("            :" + dbmd.getDriverMajorVersion() + "." + dbmd.getDriverMinorVersion());
        System.out.println("            :" + dbmd.getDriverVersion());
    }
}
