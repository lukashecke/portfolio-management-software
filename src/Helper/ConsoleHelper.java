package Helper;

import org.nocrala.tools.texttablefmt.Table;

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
        if (resultSet.getFetchSize() != 0) {
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
        }
        else   {
            System.out.println("empty result set");
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
