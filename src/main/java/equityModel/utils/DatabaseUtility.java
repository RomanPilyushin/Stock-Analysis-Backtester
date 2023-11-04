package equityModel.utils;

// Required imports for SQL and date-time handling
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

// Utility class for database operations
public class DatabaseUtility {

    // Generates a standardized table name using the company, type of data, and current date
    public static String generateTableName(String company, String typeOfData) {
        // Gets the current formatted date as a String
        String date = DatabaseUtility.getCurrentFormattedDate();
        // Returns a concatenation of the company name, type of data, and date as a table name
        return company + "_" + typeOfData.toLowerCase() + "_" + date;
    }

    // Checks if a table already exists in the SQLite database
    public static boolean doesTableExist(String tableName) {
        // Try-with-resources to ensure the connection is closed after use
        try (Connection conn = SQLiteConnection.connect()) {
            // Get the database metadata to check existing tables
            DatabaseMetaData md = conn.getMetaData();
            // Query the metadata for tables that match the given table name
            ResultSet rs = md.getTables(null, null, tableName, null);
            // If rs.next() is true, it means the table exists
            return rs.next();
        } catch (SQLException e) {
            // Print the exception stack trace and return false indicating the check failed
            e.printStackTrace();
            return false;
        }
    }

    // Returns the current date formatted as a String for use in table names
    public static String getCurrentFormattedDate() {
        // Define the format for the date
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy_MM_dd");
        // Return the current date in the specified format
        return LocalDate.now().format(formatter);
    }

    // Creates a new table for storing stock data if it does not already exist
    public static void createTable(String company, String typeOfData) {
        // Generate a table name using company and type of data
        String tableName = generateTableName(company, typeOfData);

        // SQL statement to create a new table with the given structure, if it does not already exist
        String sql = "CREATE TABLE IF NOT EXISTS \"" + tableName + "\" (" +
                "id INTEGER PRIMARY KEY," +     // Column for the primary key
                "date TEXT NOT NULL," +         // Column for the date
                "open REAL NOT NULL," +         // Column for the opening price
                "high REAL NOT NULL," +         // Column for the highest price
                "low REAL NOT NULL," +          // Column for the lowest price
                "close REAL NOT NULL," +        // Column for the closing price
                "volume INTEGER NOT NULL" +     // Column for the trade volume
                ");";

        // Try-with-resources to execute the SQL statement using a Statement object
        try (Connection conn = SQLiteConnection.connect();
             Statement stmt = conn.createStatement()) {
            // Execute the SQL statement to create the table
            stmt.execute(sql);
        } catch (SQLException e) {
            // Print the exception stack trace if SQL execution fails
            e.printStackTrace();
        }
    }
}
