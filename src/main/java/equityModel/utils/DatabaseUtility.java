package equityModel.utils;

import java.sql.*;

public class DatabaseUtility {

    private static final String DB_PATH = "jdbc:sqlite:stockdata.db";

    public static String generateTableName(String company, String typeOfData, String date) {
        return company + "_" + typeOfData + "_" + date;
    }

    public static boolean doesTableExist(String tableName) {
        try (Connection conn = DriverManager.getConnection(DB_PATH)) {
            DatabaseMetaData md = conn.getMetaData();
            ResultSet rs = md.getTables(null, null, tableName, null);
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void createTable(String company, String typeOfData, String date) {
        String tableName = generateTableName(company, typeOfData, date);

        String sql = "CREATE TABLE IF NOT EXISTS " + tableName + " (" +
                "id INTEGER PRIMARY KEY," +
                "date TEXT NOT NULL," +
                "open REAL NOT NULL," +
                "high REAL NOT NULL," +
                "low REAL NOT NULL," +
                "close REAL NOT NULL," +
                "volume INTEGER NOT NULL" +
                ");";

        try (Connection conn = DriverManager.getConnection(DB_PATH);
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
