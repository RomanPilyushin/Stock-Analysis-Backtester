package equityModel.models;

import equityModel.data.StockData;
import equityModel.utils.DataFetcher;
import equityModel.utils.SQLiteStorage;

import java.util.List;

public class Backtester {

    private final MovingAverageCrossover strategy;
    private final List<StockData> historicalData;

    public Backtester(List<StockData> historicalData) {
        this.historicalData = historicalData;
        this.strategy = new MovingAverageCrossover();
    }

    public void run() {
        int wins = 0;
        int trades = 0;
        for (int i = 200; i < historicalData.size(); i++) {
            List<StockData> subset = historicalData.subList(i - 200, i);
            boolean buySignal = strategy.evaluate(subset);

            if (buySignal) {
                trades++;
                double todayClose = subset.get(subset.size() - 1).getClose();
                double tomorrowClose = historicalData.get(i).getClose();

                if (tomorrowClose > todayClose) {
                    wins++;
                }
            }
        }

        double winRate = ((double) wins / trades) * 100;
        System.out.println("Win Rate: " + winRate + "%");
    }

    public static void main(String[] args) {
        String companyTicker = "AAPL";
        DataFetcher.FetchDataType fetchDataType = DataFetcher.FetchDataType.MONTH;

        List<StockData> stockDataList = SQLiteStorage.getStockDataForCompany(companyTicker, fetchDataType);
        Backtester backtester = new Backtester(stockDataList);
        backtester.run();
    }
}
