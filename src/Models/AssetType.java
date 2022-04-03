package Models;

import Business.DBConnection;
import Helper.ConsoleHelper;
import com.mysql.jdbc.CallableStatement;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
Abbildung eines Asset Typs (z.B. Einzelaktien, Krypto, Schwermetalle).
 */
public class AssetType {
    private int id;
    private String name;
    private String shortName;
    private Boolean needsIncomeTax;
    private String info;
    private String description;

    public static ArrayList<AssetType> GetInvestedAssetTypes() {
        ArrayList<AssetType> assetTypes = new ArrayList<>();
        String SQL = "{call GetInvestedAssetTypes(1)}";
        try(CallableStatement callableStatement = (CallableStatement) DBConnection.getInstance().connection.prepareCall(SQL)) {
            callableStatement.executeQuery();
            ResultSet rs = callableStatement.getResultSet();

            while (rs.next()) {
                var assetType = new AssetType();
                assetType.setId(rs.getInt("Id"));
                assetType.setName(rs.getString("Name"));
                assetType.setShortName(rs.getString("ShortName"));
                assetType.setNeedsIncomeTax(rs.getBoolean("NeedsIncomeTax"));
                assetTypes.add(assetType);
            }

            ConsoleHelper.printResultSet(rs);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return assetTypes;
    }




    @Override
    public String toString() {
        return this.getName();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public Boolean getNeedsIncomeTax() {
        return needsIncomeTax;
    }

    public void setNeedsIncomeTax(Boolean needsIncomeTax) {
        this.needsIncomeTax = needsIncomeTax;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
