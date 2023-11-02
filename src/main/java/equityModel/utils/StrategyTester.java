package equityModel.utils;

import equityModel.data.StockData;
import equityModel.models.MovingAverageCrossover;

import java.util.List;

public class StrategyTester {

    public static void main(String[] args) {
        String companyTicker = "AMZN"; // Change this to test other companies
        DataFetcher.FetchDataType fetchDataType = DataFetcher.FetchDataType.INTRADAY; // Can be changed to other data types

        // Fetch data from the AlphaVantage API if not in SQLite
        List<StockData> stockDataList = SQLiteStorage.getStockDataForCompany(companyTicker, fetchDataType);
        if (stockDataList.isEmpty()) {
            System.out.println("Fetching data for " + companyTicker + "...");
            DataFetcher.fetchDataForCompany(companyTicker, fetchDataType);
            stockDataList = SQLiteStorage.getStockDataForCompany(companyTicker, fetchDataType);
            if (stockDataList.isEmpty()) {
                System.out.println("Failed to fetch data for " + companyTicker + ".");
                return;
            }
        }

        // Analyze data using the MovingAverageCrossover strategy
        MovingAverageCrossover strategy = new MovingAverageCrossover();
        boolean buySignal = strategy.evaluate(stockDataList);

        // Print the result
        System.out.println("Buy Signal for " + companyTicker + ": " + buySignal);
    }
}
