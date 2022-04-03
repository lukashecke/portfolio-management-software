package Models;

import Business.DBConnection;
import Helper.ConsoleHelper;
import com.mysql.jdbc.CallableStatement;

import java.sql.ResultSet;
import java.sql.SQLException;

public class History {

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
