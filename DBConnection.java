import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Central place that opens a JDBC connection to the MySQL database.
 *
 * IMPORTANT: change DB_USER / DB_PASSWORD below to match your own
 * MySQL installation before running the project.
 *
 * You also need the MySQL Connector/J jar on your classpath
 * (mysql-connector-j-x.x.x.jar) - see README.md for download + setup.
 */
public class DBConnection {

    private static final String DB_URL =
            "jdbc:mysql://localhost:3306/blood_donor_radar?useSSL=false&serverTimezone=UTC";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";

    /**
     * Opens and returns a new connection. Caller is responsible for
     * closing it (use try-with-resources, as the DAO classes do).
     */
    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            // Wrapped as SQLException so callers only need to catch one exception type.
            throw new SQLException("MySQL JDBC Driver not found on classpath. "
                    + "Add mysql-connector-j-x.x.x.jar to your classpath.", e);
        }
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }

    /**
     * Standalone connectivity test.
     * Run this class directly (java DBConnection) and take a screenshot
     * of the console output as evidence of successful JDBC connectivity.
     */
    public static void main(String[] args) {
        System.out.println("Attempting to connect to blood_donor_radar database...");
        try (Connection con = getConnection()) {
            if (con != null && !con.isClosed()) {
                System.out.println("SUCCESS: Database connected!");
                System.out.println("Connected URL : " + con.getMetaData().getURL());
                System.out.println("DB Product    : " + con.getMetaData().getDatabaseProductName());
                System.out.println("DB Version    : " + con.getMetaData().getDatabaseProductVersion());
            }
        } catch (SQLException e) {
            System.out.println("FAILED: Could not connect to the database.");
            System.out.println("Reason: " + e.getMessage());
        }
    }
}
