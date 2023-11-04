package equityModel.main;

// Import necessary classes from other packages
import equityModel.data.StockData;
import equityModel.models.Backtester;
import equityModel.utils.DataFetcher;
import equityModel.utils.FetchDataType;
import equityModel.utils.SQLiteStorage;
import equityModel.utils.TableChecker;

import java.util.List;

// Main class that tests the strategy
public class StrategyTester {

    public static void main(String[] args) {
        // Ticker symbol for the company to test the strategy on
        String companyTicker = "GS";
        // Type of data to fetch (in this case, weekly data)
        FetchDataType fetchDataType = FetchDataType.WEEK;

        // Check if there is existing data in the database for the company and data type
        boolean dataExists = TableChecker.doesTableHaveData(companyTicker, fetchDataType);
        // Initialize a list to hold stock data
        List<StockData> stockDataList = null;

        // If data doesn't exist, fetch it from an API
        if (!dataExists) {
            System.out.println("Data for " + companyTicker + " not found in SQLite. Fetching from API...");
            // Call to fetch the data
            DataFetcher.fetchDataForCompany(companyTicker, fetchDataType);

            // After fetching, check again if data is now present
            if (TableChecker.doesTableHaveData(companyTicker, fetchDataType)) {
                // If yes, retrieve the data and store it in the list
                stockDataList = SQLiteStorage.getStockDataForCompany(companyTicker, fetchDataType);
            } else {
                // If data is still not present, exit the program
                System.out.println("Failed to create table or fetch data for " + companyTicker + ". Exiting...");
                return;
            }
        } else {
            // If data exists, print a message and retrieve the data
            System.out.println("Data for " + companyTicker + " exists in SQLite.");
            stockDataList = SQLiteStorage.getStockDataForCompany(companyTicker, fetchDataType);
        }

        // If there is data to work with, proceed to backtesting
        if (!stockDataList.isEmpty()) {
            // Perform backtesting with the retrieved data
            performBacktest(stockDataList);
        } else {
            // If no data is available, print an error message
            System.out.println("Something went wrong. No data available for backtesting.");
        }
    }

    // Helper method to perform backtesting
    private static void performBacktest(List<StockData> stockDataList) {
        // Instantiate a Backtester object with the data
        Backtester backtester = new Backtester(stockDataList);
        // Run the backtesting process
        backtester.run();
    }
}
