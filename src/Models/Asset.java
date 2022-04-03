package Models;

import Business.DBConnection;
import Helper.ConsoleHelper;
import com.mysql.jdbc.CallableStatement;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Abbildung eines Assets.
 */
public class Asset {
    private int id;
    private String name;
    private String shortName;
    private AssetType type;

    public static void updateAsset(int id, String name, String shortName) {
        String SQL = "{call UpdateAsset("+ id +", '"+ name+"', '"+ shortName+"')}";
        try(CallableStatement callableStatement = (CallableStatement) DBConnection.getInstance().connection.prepareCall(SQL)) {
            callableStatement.executeQuery();

            // todo: hier kein ResultSet vorhanden, wie geben wir dennoch informationen aus?
            // ConsoleHelper.printResultSet(rs);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Asset getAssetById(int id){
        Asset asset = new Asset();
        String SQL = "{call GetAsset("+id+")}";
        int typeId;
        try(CallableStatement callableStatement = (CallableStatement) DBConnection.getInstance().connection.prepareCall(SQL)) {
            callableStatement.executeQuery();
            ResultSet rs = callableStatement.getResultSet();

            rs.next();

            asset.setId(rs.getInt("Id"));
            asset.setName(rs.getString("Name"));
            asset.setShortName(rs.getString("ShortName"));
            typeId = rs.getInt("Type_Id");
            asset.setType(Asset.getAssetTypeById(typeId));

            ConsoleHelper.printResultSet(rs);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return asset;
    }

    /**
     * Lädt alle Assets von der Datennbank.
     * @author Lukas Hecke
     */
    public static ArrayList<Asset> getAssets() {
        ArrayList<Asset> assets = new ArrayList<>();
        String SQL = "{call GetAssets()}";
        try(CallableStatement callableStatement = (CallableStatement) DBConnection.getInstance().connection.prepareCall(SQL)) {
            callableStatement.executeQuery();
            ResultSet rs = callableStatement.getResultSet();

            while (rs.next()) {
                var asset = new Asset();
                asset.setId(rs.getInt("Id"));
                asset.setName(rs.getString("Name"));
                asset.setShortName(rs.getString("ShortName"));
                int assetTypeId = rs.getInt("Type_Id");
                asset.setType(Asset.getAssetTypeById(assetTypeId));

                assets.add(asset);
            }

            ConsoleHelper.printResultSet(rs);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return assets;
    }

    public static double getInvestedSumForAsset(int id) {
        double investedSum = 0;
        String SQL = "{call GetInvestedSumForAsset("+ id+")}";
        try(CallableStatement callableStatement = (CallableStatement) DBConnection.getInstance().connection.prepareCall(SQL)) {
            callableStatement.executeQuery();
            ResultSet rs = callableStatement.getResultSet();

            rs.next();
            try{

                investedSum = rs.getDouble(1);
            }
            catch (SQLException ex) {
                // ConsoleHelper loggt diesen Fehler bereits mit
            }

            ConsoleHelper.printResultSet(rs);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return investedSum;
    }

    public static ArrayList<Asset> getInvestedAssets() {
        ArrayList<Asset> assets = new ArrayList<>();
        String SQL = "{call GetInvestedAssets(1)}";
        try(CallableStatement callableStatement = (CallableStatement) DBConnection.getInstance().connection.prepareCall(SQL)) {
            callableStatement.executeQuery();
            ResultSet rs = callableStatement.getResultSet();

            while (rs.next()) {
                var asset = new Asset();
                asset.setId(rs.getInt("Id"));
                asset.setName(rs.getString("Name"));
                asset.setShortName(rs.getString("ShortName"));
                int assetTypeId = rs.getInt("Type_Id");
                asset.setType(Asset.getAssetTypeById(assetTypeId));

                assets.add(asset);
            }

            ConsoleHelper.printResultSet(rs);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return assets;
    }

    public static AssetType getAssetTypeById(int id){
        AssetType assetType = new AssetType();
        String SQL = "{call GetType("+id+")}";
        try(CallableStatement callableStatement = (CallableStatement) DBConnection.getInstance().connection.prepareCall(SQL)) {
            callableStatement.executeQuery();
            ResultSet rs = callableStatement.getResultSet();

            rs.next();

            assetType.setId(rs.getInt("Id"));
            assetType.setName(rs.getString("Name"));
            assetType.setShortName(rs.getString("ShortName"));
            assetType.setNeedsIncomeTax(rs.getBoolean("NeedsIncomeTax"));

            ConsoleHelper.printResultSet(rs);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        SQL = "{call GetInfo("+assetType.getId()+")}";
        try(CallableStatement callableStatement = (CallableStatement) DBConnection.getInstance().connection.prepareCall(SQL)) {
            callableStatement.executeQuery();
            ResultSet rs = callableStatement.getResultSet();

            rs.next();

            if (rs.getFetchSize() != 0) {
                assetType.setInfo(rs.getString("Info"));
                assetType.setDescription(rs.getString("Description"));
            }

            ConsoleHelper.printResultSet(rs);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return assetType;
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

    public AssetType getType() {
        return type;
    }

    public void setType(AssetType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
