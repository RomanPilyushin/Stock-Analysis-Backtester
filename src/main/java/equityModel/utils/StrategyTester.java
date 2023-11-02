package equityModel.utils;

import equityModel.data.StockData;
import equityModel.models.MovingAverageCrossover;

import java.util.List;

public class StrategyTester {

    public static void main(String[] args) {
        String companyTicker = "AAPL"; // Change this to test other companies
        DataFetcher.FetchDataType fetchDataType = DataFetcher.FetchDataType.MONTH; // Can be changed to other data types

        // Fetch data
        List<StockData> stockDataList = SQLiteStorage.getStockDataForCompany(companyTicker, fetchDataType);
        if (stockDataList.isEmpty()) {
            System.out.println("No data found for " + companyTicker + " for the given data type.");
            return;
        }

        // Analyze data using the MovingAverageCrossover strategy
        MovingAverageCrossover strategy = new MovingAverageCrossover();
        boolean buySignal = strategy.evaluate(stockDataList);

        // Print the result
        System.out.println("Buy Signal for " + companyTicker + ": " + buySignal);
    }
}
