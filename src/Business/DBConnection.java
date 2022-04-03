package Business;

import Helper.ConsoleHelper;
import Models.Asset;
import Models.AssetType;
import Models.Platform;
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

    public final static String ADMIN = "gruppeZadmin@localhost";

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

    public void createNewInvestment(Asset asset, Platform platform, String selectedDate, double pricePerUnit, double purchasePrice, double transactionFee) throws SQLException {
        String SQL = "{call CreateNewInvestment(1, "+ asset.getId() +", "+platform.getId()+", "+ selectedDate +", "+pricePerUnit+", "+purchasePrice+", "+transactionFee+")}";
        CallableStatement callableStatement = (CallableStatement) DBConnection.getInstance().connection.prepareCall(SQL);
        callableStatement.executeQuery();
        ResultSet rs = callableStatement.getResultSet();

        ConsoleHelper.printResultSet(rs);
    }

    public String getTotalInvestment() {
        double sum = 0;
        var assets = GetAssets();
        for (Asset asset :
                assets) {
            sum += getInvestedSumForAsset(asset.getId());
        }
        return "" + sum;
    }

    public Object[][] getAssetInvestments(int id) {
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

    public Object[][] getAssetInvestmentsPresentation(int id) {
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

    public String getUser() {
        String SQL = "SELECT USER()";
        String user = "";
        try(CallableStatement callableStatement = (CallableStatement) DBConnection.getInstance().connection.prepareCall(SQL)) {
            callableStatement.executeQuery();

            ResultSet rs = callableStatement.getResultSet();

            rs.next();

            user = rs.getString(1);

            ConsoleHelper.printResultSet(rs);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    public void updateAsset(int id, String name, String shortName) {
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

    /**
     * Gibt zurück ob der Aktuelle Benutzer auf der Datenbank Admin-Rechte hat.
     * @return
     */
    public Boolean isAdmin() {
        return DBConnection.getInstance().getUser().equals(DBConnection.ADMIN);
    }

    public void deleteInvestment(int id) {
        String SQL = "{call DeleteInvestment("+ id +")}";
        try(CallableStatement callableStatement = (CallableStatement) DBConnection.getInstance().connection.prepareCall(SQL)) {
            callableStatement.executeQuery();

            // todo: hier kein ResultSet vorhanden, wie geben wir dennoch informationen aus?
            // ConsoleHelper.printResultSet(rs);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteHistory(int id) {
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

    public int getHistoryIdForInvestment(int investmentId) {
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
}
