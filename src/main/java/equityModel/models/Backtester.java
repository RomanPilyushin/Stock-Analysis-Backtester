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

        double startingCapital = 10000.0;
        double currentCapital = startingCapital;
        double historicalPeak = startingCapital;
        double maxDrawdown = 0.0;

        for (int i = 200; i < historicalData.size(); i++) {
            List<StockData> subset = historicalData.subList(i - 200, i);
            boolean buySignal = strategy.evaluate(subset);

            if (buySignal) {
                trades++;
                double todayClose = subset.get(subset.size() - 1).getClose();
                double tomorrowClose = historicalData.get(i).getClose();

                double profitOrLoss = tomorrowClose - todayClose;
                currentCapital += profitOrLoss;

                if (tomorrowClose > todayClose) {
                    wins++;
                }

                // Update historical peak
                if (currentCapital > historicalPeak) {
                    historicalPeak = currentCapital;
                }

                // Compute current drawdown
                double currentDrawdown = (historicalPeak - currentCapital) / historicalPeak;

                // Update max drawdown if current drawdown is greater
                if (currentDrawdown > maxDrawdown) {
                    maxDrawdown = currentDrawdown;
                }
            }
        }

        double totalReturn = (currentCapital - startingCapital) / startingCapital;
        double winRate = ((double) wins / trades) * 100;

        System.out.println("Win Rate: " + String.format("%.3f", winRate) + "%");
        System.out.println("Total Return: " + String.format("%.3f", (totalReturn * 100)) + "%");
        System.out.println("Max Drawdown: " + String.format("%.3f", (maxDrawdown * 100)) + "%");
    }

    public static void main(String[] args) {
        String companyTicker = "AAPL";
        DataFetcher.FetchDataType fetchDataType = DataFetcher.FetchDataType.MONTH;

        List<StockData> stockDataList = SQLiteStorage.getStockDataForCompany(companyTicker, fetchDataType);
        Backtester backtester = new Backtester(stockDataList);
        backtester.run();
    }
}
