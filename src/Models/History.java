package Models;

import Business.DBConnection;
import Helper.ConsoleHelper;
import com.mysql.jdbc.CallableStatement;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Abbildung eines Asset-Kurses.
 */
public class History {

    /**
     * Gibt die Kurs-Id für das übergeben Investment zurück.
     * @param investmentId Investment-ID
     * @return History-ID
     */
    public static int getHistoryIdForInvestment(int investmentId) {
        String SQL = "{call GetHistoryIdForInvestment("+ investmentId +")}";
        int historyId = -1;
        try(CallableStatement callableStatement = (CallableStatement) DBConnection.getInstance().connection.prepareCall(SQL)) {
            callableStatement.executeQuery();

            ResultSet rs = callableStatement.getResultSet();

            rs.next();

            historyId = rs.getInt(1);

            ConsoleHelper.printResultSet(rs);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return historyId;
    }

    /**
     * Löscht den Kurs-Eintrag mit der übergebenen ID.
     * @param id Kurs-ID
     */
    public static void deleteHistory(int id) {
        String SQL = "{call DeleteHistory("+ id +")}";
        try(CallableStatement callableStatement = (CallableStatement) DBConnection.getInstance().connection.prepareCall(SQL)) {
            callableStatement.executeQuery();

            // todo: hier kein ResultSet vorhanden, wie geben wir dennoch informationen aus?
            // ConsoleHelper.printResultSet(rs);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
