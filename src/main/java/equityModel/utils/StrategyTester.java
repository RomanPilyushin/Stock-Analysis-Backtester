package equityModel.utils;

import equityModel.data.StockData;
import equityModel.models.Backtester;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class StrategyTester {

    public static void main(String[] args) {
        String companyTicker = "GS";
        FetchDataType fetchDataType = FetchDataType.WEEK;


        // Check if data exists
        boolean dataExists = TableChecker.doesTableHaveData(companyTicker, fetchDataType);
        List<StockData> stockDataList = null;

        if (!dataExists) {
            System.out.println("Data for " + companyTicker + " not found in SQLite. Fetching from API...");
            DataFetcher.fetchDataForCompany(companyTicker, fetchDataType);

            // Check if the table was successfully created and data was fetched after the API call
            if (TableChecker.doesTableHaveData(companyTicker, fetchDataType)) {
                stockDataList = SQLiteStorage.getStockDataForCompany(companyTicker, fetchDataType);
            } else {
                System.out.println("Failed to create table or fetch data for " + companyTicker + ". Exiting...");
                return;
            }
        } else {
            System.out.println("Data for " + companyTicker + " exists in SQLite.");
            stockDataList = SQLiteStorage.getStockDataForCompany(companyTicker, fetchDataType);
        }

        // If data is available (either was already there or just fetched and stored), perform backtesting
        if (!stockDataList.isEmpty()) {
            performBacktest(stockDataList);
        } else {
            System.out.println("Something went wrong. No data available for backtesting.");
        }
    }

    private static void performBacktest(List<StockData> stockDataList) {
        Backtester backtester = new Backtester(stockDataList);
        backtester.run();
    }
}

