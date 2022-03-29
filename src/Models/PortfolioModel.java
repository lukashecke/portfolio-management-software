package Models;

import Business.DBConnection;
import Helper.ConsoleHelper;
import com.mysql.jdbc.CallableStatement;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PortfolioModel {
    private ArrayList<Asset> assets;

    public PortfolioModel() {
        assets = new ArrayList<>();

        String SQL = "{call GetSummedAssets(1)}";
        try(CallableStatement callableStatement = (CallableStatement)DBConnection.getInstance().connection.prepareCall(SQL)) {
            callableStatement.executeQuery();
            ResultSet rs = callableStatement.getResultSet();

            while(rs.next()) {
                int id = rs.getInt("Id");
                assets.add(DBConnection.getInstance().GetAssetById(id));
            }

            ConsoleHelper.printResultSet(rs);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Asset> getAssets() {
        return assets;
    }
}
