package equityModel.utils;

import equityModel.data.StockData;

import java.sql.*;
import java.time.LocalDate;
import java.util.List;

public class SQLiteStorage {

    private static final String DB_PATH = "jdbc:sqlite:stockdata.db";  // Replace with your SQLite database path

    public static void storeStockData(String companyTicker, String dataType, List<StockData> dataList) {
        String tableName = generateTableName(companyTicker, dataType);

        if (!doesTableExist(tableName)) {
            createTable(tableName);
        }

        insertDataIntoTable(tableName, dataList);
    }

    private static String generateTableName(String companyTicker, String dataType) {
        String fetchDate = LocalDate.now().toString();
        return companyTicker + "_" + dataType.toLowerCase() + "_" + fetchDate.replace("-", "_");
    }

    private static boolean doesTableExist(String tableName) {
        try (Connection conn = DriverManager.getConnection(DB_PATH)) {
            DatabaseMetaData md = conn.getMetaData();
            ResultSet rs = md.getTables(null, null, tableName, null);
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    private static void createTable(String tableName) {
        String sql = "CREATE TABLE IF NOT EXISTS " + tableName + " (\n"
                + "    id integer PRIMARY KEY,\n"
                + "    date text NOT NULL,\n"
                + "    open real NOT NULL,\n"
                + "    high real NOT NULL,\n"
                + "    low real NOT NULL,\n"
                + "    close real NOT NULL,\n"
                + "    volume integer NOT NULL\n"
                + ");";

        try (Connection conn = DriverManager.getConnection(DB_PATH);
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void insertDataIntoTable(String tableName, List<StockData> dataList) {
        String sql = "INSERT INTO " + tableName + "(date, open, high, low, close, volume) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(DB_PATH);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            for (StockData data : dataList) {
                pstmt.setString(1, data.getDate());
                pstmt.setDouble(2, data.getOpen());
                pstmt.setDouble(3, data.getHigh());
                pstmt.setDouble(4, data.getLow());
                pstmt.setDouble(5, data.getClose());
                pstmt.setInt(6, (int) data.getVolume());

                pstmt.addBatch(); // Add the statement for batch processing
            }

            pstmt.executeBatch(); // Execute the batch of insert statements
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
