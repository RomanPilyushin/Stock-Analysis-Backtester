package equityModel;

import equityModel.data.StockData;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.List;

public class SQLiteStorage {
    private static final String DATABASE_URL = "jdbc:sqlite:stockdata.db";

    public static void storeStockData(List<StockData> stockDataList) {
        try (Connection conn = DriverManager.getConnection(DATABASE_URL)) {
            if (conn != null) {
                String sql = "INSERT INTO stock_data(date, open, high, low, close, volume) VALUES(?,?,?,?,?,?)";

                PreparedStatement pstmt = conn.prepareStatement(sql);
                for (StockData data : stockDataList) {
                    pstmt.setString(1, data.getDate());
                    pstmt.setDouble(2, data.getOpen());
                    pstmt.setDouble(3, data.getHigh());
                    pstmt.setDouble(4, data.getLow());
                    pstmt.setDouble(5, data.getClose());
                    pstmt.setLong(6, data.getVolume());
                    pstmt.executeUpdate();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
