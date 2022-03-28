package Models;

import Business.DBConnection;
import Helper.ConsoleHelper;
import com.mysql.jdbc.CallableStatement;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PortfolioModel {
    private Object[][] assets;

    public PortfolioModel() {
        String SQL = "{call GetSummedAssets(1)}";
        try(CallableStatement callableStatement = (CallableStatement)DBConnection.getInstance().connection.prepareCall(SQL)) {
            callableStatement.executeQuery();
            ResultSet rs = callableStatement.getResultSet();

            setAssets(GetData(rs));

            ConsoleHelper.printResultSet(rs);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gibt ein zweidimensionales Array aus Daten aus dem übergebenen ResultSet zurück.
     * @param rs ResultSet aus JDBC-Abfrage
     * @return Zweidimensionales Array, das z.B. für JTables verwendet werden kann
     * @author Lukas Hecke
     */
    public Object[][] GetData(ResultSet rs) throws SQLException {
        ArrayList<String[]> dataRows = new ArrayList<>();

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

        return dataRows.toArray(new Object[0][]);
    }

    public Object[][] getAssets() {
        return assets;
    }

    private void setAssets(Object[][] assets) {
        this.assets = assets;
    }
}
