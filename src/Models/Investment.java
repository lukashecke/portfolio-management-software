package Models;

import Business.DBConnection;
import Helper.ConsoleHelper;
import com.mysql.jdbc.CallableStatement;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Abbildung eines Investments (Einkauf eines Assets).
 */
public class Investment {
    private Platform platform;

    public static void createNewInvestment(Asset asset, Platform platform, String selectedDate, double pricePerUnit, double purchasePrice, double transactionFee) throws SQLException {
        String SQL = "{call CreateNewInvestment(1, "+ asset.getId() +", "+platform.getId()+", "+ selectedDate +", "+pricePerUnit+", "+purchasePrice+", "+transactionFee+")}";
        CallableStatement callableStatement = (CallableStatement) DBConnection.getInstance().connection.prepareCall(SQL);
        callableStatement.executeQuery();
        ResultSet rs = callableStatement.getResultSet();

        ConsoleHelper.printResultSet(rs);
    }

    public static Object[][] getAssetInvestmentsPresentation(int id) {
        String SQL = "{call GetAssetInvestmentsPresentation(1, "+ id +")}";

        ArrayList<String[]> dataRows = new ArrayList<>();

        try(CallableStatement callableStatement = (CallableStatement) DBConnection.getInstance().connection.prepareCall(SQL)) {
            callableStatement.executeQuery();

            ResultSet rs = callableStatement.getResultSet();

            var columnCount = rs.getMetaData().getColumnCount();

            // Spaltennamen
            /*
            var headers = new String[columnCount];
            for (int j = 1; j <= columnCount; j++) {
                headers[j - 1] = rs.getMetaData().getColumnName(j);
            }
            dataRows.add(headers);
             */

            // Leider müssen die Header manuell gesetzt werden, weil gesetzten Spaltennamen mittels "AS" nicht zuverlässig aufgelöst werden.
            String[] headers = new String[]{"Investitionsnummer" ,"Investitionssumme in €","Investitionsdatum","Plattform"};
            dataRows.add(headers);

            // Daten
            while (rs.next()) {
                String[] record = new String[columnCount];
                for (int i = 0; i < columnCount; i++) {
                    record[i] = rs.getString(i + 1);
                }
                dataRows.add(record);
            }

            ConsoleHelper.printResultSet(rs);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return dataRows.toArray(new Object[0][]);
    }

    public static Object[][] getAssetInvestments(int id) {
        String SQL = "{call GetAssetInvestments(1, "+ id +")}";

        ArrayList<String[]> dataRows = new ArrayList<>();

        try(CallableStatement callableStatement = (CallableStatement) DBConnection.getInstance().connection.prepareCall(SQL)) {
            callableStatement.executeQuery();

            ResultSet rs = callableStatement.getResultSet();

            var columnCount = rs.getMetaData().getColumnCount();

            // Spaltennamen
            var headers = new String[columnCount];
            for (int j = 1; j <= columnCount; j++) {
                headers[j - 1] = rs.getMetaData().getColumnName(j);
            }
            dataRows.add(headers);

            // Daten
            while (rs.next()) {
                String[] record = new String[columnCount];
                for (int i = 0; i < columnCount; i++) {
                    record[i] = rs.getString(i + 1);
                }
                dataRows.add(record);
            }

            ConsoleHelper.printResultSet(rs);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return dataRows.toArray(new Object[0][]);
    }

    public static String getTotalInvestment() {
        double sum = 0;
        var assets = Asset.getAssets();
        for (Asset asset :
                assets) {
            sum += Asset.getInvestedSumForAsset(asset.getId());
        }
        return "" + sum;
    }
}
