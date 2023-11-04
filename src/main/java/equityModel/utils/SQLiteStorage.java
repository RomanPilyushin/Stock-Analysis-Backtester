package equityModel.utils;

// Imports required for SQL and handling stock data
import equityModel.data.StockData;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// Static imports for database utility functions
import static equityModel.utils.DatabaseUtility.createTable;
import static equityModel.utils.DatabaseUtility.doesTableExist;
import static equityModel.utils.DatabaseUtility.generateTableName;

// Class providing methods to store and retrieve stock data in SQLite
public class SQLiteStorage {

    // Method to store a list of stock data into the database
    public static void storeStockData(String companyTicker, String dataType, List<StockData> dataList) {
        // Generate the table name based on company ticker and data type
        String tableName = generateTableName(companyTicker, dataType);

        // Check if the table exists, and create it if it doesn't
        if (!doesTableExist(tableName)) {
            createTable(companyTicker, dataType);
        }

        // Insert the data into the table
        insertDataIntoTable(tableName, dataList);
    }

    // Private helper method to insert data into the specified table
    private static void insertDataIntoTable(String tableName, List<StockData> dataList) {
        // SQL query to insert data into the table
        String sql = "INSERT INTO \"" + tableName + "\" (date, open, high, low, close, volume) VALUES (?, ?, ?, ?, ?, ?)";

        // Use a try-with-resources statement to handle the SQL connection and prepared statement
        try (Connection conn = SQLiteConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            // Loop through each stock data object and set the prepared statement parameters
            for (StockData data : dataList) {
                pstmt.setString(1, data.getDate());
                pstmt.setDouble(2, data.getOpen());
                pstmt.setDouble(3, data.getHigh());
                pstmt.setDouble(4, data.getLow());
                pstmt.setDouble(5, data.getClose());
                pstmt.setInt(6, (int) data.getVolume());

                // Add the prepared statement to the batch for batch processing
                pstmt.addBatch();
            }

            // Execute the batch of prepared statements to insert data
            pstmt.executeBatch();
        } catch (SQLException e) {
            // Print any SQL exceptions that occur
            e.printStackTrace();
        }
    }

    // Method to check if data for a specific company and date already exists in the database
    public static boolean doesCompanyDataExist(String companyTicker, FetchDataType fetchDataType, String date) {
        // Generate the table name from company ticker and data type
        String tableName = generateTableName(companyTicker, fetchDataType.toString());
        // Check if the table exists and return the result
        return tableExists(tableName);
    }

    // Private helper method to check if a table exists by querying the SQLite master table
    private static boolean tableExists(String tableName) {
        // SQL query to check if a table exists in the database
        String sql = "SELECT name FROM sqlite_master WHERE type='table' AND name=?";

        // Try-with-resources for SQL connection and prepared statement
        try (Connection conn = SQLiteConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // Set the table name parameter in the prepared statement
            pstmt.setString(1, tableName);
            // Execute the query and return true if the table exists
            ResultSet rs = pstmt.executeQuery();
            return rs.next();

        } catch (SQLException e) {
            // Print the stack trace for any SQL exceptions
            e.printStackTrace();
            return false;
        }
    }


    // Method to retrieve stock data for a specific company from the database
    public static List<StockData> getStockDataForCompany(String companyTicker, FetchDataType dataType) {
        // Initialize an empty list to hold stock data
        List<StockData> stockDataList = new ArrayList<>();
        // Generate the table name from company ticker and data type
        String tableName = generateTableName(companyTicker, dataType.getString());

        // SQL query to select all columns from the table
        String sql = "SELECT date, open, high, low, close, volume FROM " + tableName;

        // Try-with-resources for SQL connection, statement, and result set
        try (Connection conn = SQLiteConnection.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            // Iterate over the result set and add each row as a StockData object to the list
            while (rs.next()) {
                StockData stockData = new StockData(
                        rs.getString("date"),
                        rs.getDouble("open"),
                        rs.getDouble("high"),
                        rs.getDouble("low"),
                        rs.getDouble("close"),
                        rs.getInt("volume")
                );
                stockDataList.add(stockData);
            }
        } catch (SQLException e) {
            // Print the exception message if an SQL exception occurs
            System.out.println(e.getMessage());
        }

        // Return the list of stock data retrieved from the database
        return stockDataList;
    }
}
