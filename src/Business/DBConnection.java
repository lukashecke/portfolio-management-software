package Business;

import Helper.ConsoleHelper;
import Models.Asset;
import Models.AssetType;
import com.mysql.jdbc.CallableStatement;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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

    public Asset GetAssetById(int id){
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
            asset.setType(GetAssetTypeById(typeId));

            ConsoleHelper.printResultSet(rs);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return asset;
    }

    public AssetType GetAssetTypeById(int id){
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

    public ArrayList<AssetType> GetInvestedAssetTypes() {
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

    public ArrayList<Asset> GetInvestedAssets() {
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
                asset.setType(DBConnection.getInstance().GetAssetTypeById(assetTypeId));

                assets.add(asset);
            }

            ConsoleHelper.printResultSet(rs);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return assets;
    }

    public double getInvestedSumForAsset(int id) {
        double investedSum = -1;
        String SQL = "{call GetInvestedSumForAsset("+ id+")}";
        try(CallableStatement callableStatement = (CallableStatement) DBConnection.getInstance().connection.prepareCall(SQL)) {
            callableStatement.executeQuery();
            ResultSet rs = callableStatement.getResultSet();

            rs.next();

            investedSum = rs.getDouble(1);

            ConsoleHelper.printResultSet(rs);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return investedSum;
    }

    public ArrayList<Asset> GetAssets() {
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
                asset.setType(DBConnection.getInstance().GetAssetTypeById(assetTypeId));

                assets.add(asset);
            }

            ConsoleHelper.printResultSet(rs);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return assets;
    }

    public void createNewInvestment(Asset selectedAsset, int platformId, String selectedDate, double pricePerUnit, double purchasePrice, double transactionFee) {
        String SQL = "{call CreateNewInvestment(1, "+ selectedAsset.getId() +", "+platformId+", "+ selectedDate +", "+pricePerUnit+", "+purchasePrice+", "+transactionFee+")}";
        try(CallableStatement callableStatement = (CallableStatement) DBConnection.getInstance().connection.prepareCall(SQL)) {
            callableStatement.executeQuery();
            ResultSet rs = callableStatement.getResultSet();

            ConsoleHelper.printResultSet(rs);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
