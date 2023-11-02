package equityModel.utils;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseUtility {

    public static String generateTableName(String company, String typeOfData, String date) {
        return company + "_" + typeOfData + "_" + date;
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

        try (Connection conn = SQLiteConnection.connect();
             Statement stmt = conn.createStatement()) {

            stmt.execute(sql);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
