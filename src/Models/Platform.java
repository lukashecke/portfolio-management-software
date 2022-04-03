package Models;

import Business.DBConnection;
import Helper.ConsoleHelper;
import com.mysql.jdbc.CallableStatement;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Abbildung einer Plattform auf die Assets eingekauft werden.
 */
public class Platform {
    private int id;
    private String name;

    public Platform() {
    }

    public Platform(int id, String name) {
        this.id = id;
        this.name = name;
    }

    // Datenbankaufrufe
    public static ArrayList<Platform> GetPlatforms() {
        ArrayList<Platform> platforms = new ArrayList<>();
        String SQL = "{call GetPlatforms()}";
        try (CallableStatement callableStatement = (CallableStatement) DBConnection.getInstance().connection.prepareCall(SQL)) {
            callableStatement.executeQuery();
            ResultSet rs = callableStatement.getResultSet();

            while (rs.next()) {
                var platform = new Platform();
                platform.setId(rs.getInt("Id"));
                platform.setName(rs.getString("Name"));

                platforms.add(platform);
            }

            ConsoleHelper.printResultSet(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return platforms;
    }

    // Getter und Setter
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

    // ToString
    @Override
    public String toString() {
        return this.name;
    }
}
