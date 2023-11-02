package equityModel.utils;

import equityModel.data.StockData;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static equityModel.utils.DatabaseUtility.createTable;
import static equityModel.utils.DatabaseUtility.doesTableExist;
import static equityModel.utils.DatabaseUtility.generateTableName;

public class SQLiteStorage {

    private static final String DB_PATH = "jdbc:sqlite:stockdata.db";

    public static void storeStockData(String companyTicker, String dataType, List<StockData> dataList) {
        String fetchDate = LocalDate.now().toString();
        String tableName = generateTableName(companyTicker, dataType, fetchDate);

        if (!doesTableExist(tableName)) {
            createTable(companyTicker, dataType, fetchDate);
        }

        insertDataIntoTable(tableName, dataList);
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

    public static List<StockData> getStockDataForCompany(String companyTicker, DataFetcher.FetchDataType dataType) {
        List<StockData> stockDataList = new ArrayList<>();

        LocalDate now = LocalDate.now();
        String dateSuffix = now.format(DateTimeFormatter.ofPattern("yyyy_MM_dd"));
        String tableName = generateTableName(companyTicker, dataType.name().toLowerCase(), dateSuffix);

        String sql = "SELECT date, open, high, low, close, volume FROM " + tableName;

        try (Connection conn = DriverManager.getConnection(DB_PATH);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

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
            System.out.println(e.getMessage());
        }

        return stockDataList;
    }
}
