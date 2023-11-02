package equityModel.utils;

import equityModel.data.StockData;
import equityModel.models.MovingAverageCrossover;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class StrategyTester {

    public static void main(String[] args) {
        String companyTicker = "GD";
        FetchDataType fetchDataType = FetchDataType.WEEK;
        String currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        // Check if data exists
        if (!SQLiteStorage.doesCompanyDataExist(companyTicker, fetchDataType, currentDate)) {
            System.out.println("Data for " + companyTicker + " not found in SQLite. Fetching from API...");
            DataFetcher.fetchDataForCompany(companyTicker, fetchDataType); // This should insert data into the SQLite DB

            // Explicitly check if table has been created
            if (!DatabaseUtility.doesTableExist(DatabaseUtility.generateTableName(companyTicker, fetchDataType.toString()))) {
                System.out.println("Failed to create table for " + companyTicker + ". Exiting...");
                return;
            }

            List<StockData> stockDataList = SQLiteStorage.getStockDataForCompany(companyTicker, fetchDataType);

            if (stockDataList.isEmpty()) {
                System.out.println("Something went wrong. Data for " + companyTicker + " was not fetched or stored correctly.");
                return;
            }

            // Perform backtesting using stockDataList...

        } else {
            System.out.println("Data for " + companyTicker + " exists in SQLite.");
            // If data exists, and you want to perform backtesting on existing data, fetch it and then do the backtesting.
        }
    }
}
