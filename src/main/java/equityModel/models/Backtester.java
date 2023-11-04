package equityModel.models;

// Import necessary classes
import equityModel.data.StockData;

import java.util.List;

// Backtester class definition
public class Backtester {

    // Field to store the trading strategy
    private final MovingAverageCrossover strategy;
    // Field to store the historical stock data
    private final List<StockData> historicalData;

    // Constructor of the backtester which initializes it with historical data
    public Backtester(List<StockData> historicalData) {
        this.historicalData = historicalData;
        // Initialize the strategy
        this.strategy = new MovingAverageCrossover();
    }

    // Method to run the backtesting process
    public void run() {
        // Count of winning trades
        int wins = 0;
        // Total number of trades
        int trades = 0;

        // Initial capital to start trading with
        double startingCapital = 10000.0;
        // Current capital which will change with trading profits/losses
        double currentCapital = startingCapital;
        // The highest amount of capital reached
        double historicalPeak = startingCapital;
        // Maximum percentage drop from the peak capital
        double maxDrawdown = 0.0;

        // Iterate through the historical data starting from the point where we have enough data to calculate indicators
        for (int i = 200; i < historicalData.size(); i++) {
            // Extract the subset of historical data needed for the current evaluation
            List<StockData> subset = historicalData.subList(i - 200, i);
            // Get the buy or sell signal from the strategy
            boolean buySignal = strategy.evaluate(subset);

            // If the strategy signals a buy
            if (buySignal) {
                // Increment the trade counter
                trades++;
                // Get the closing price for the current day from the subset
                double todayClose = subset.get(subset.size() - 1).getClose();
                // Get the closing price for the next day from the historical data
                double tomorrowClose = historicalData.get(i).getClose();

                // Calculate the profit or loss from this trade
                double profitOrLoss = tomorrowClose - todayClose;
                // Update the current capital based on the profit or loss
                currentCapital += profitOrLoss;

                // If the trade was profitable
                if (tomorrowClose > todayClose) {
                    // Increment the win counter
                    wins++;
                }

                // If the current capital is greater than the historical peak
                if (currentCapital > historicalPeak) {
                    // Update the historical peak to the new current capital
                    historicalPeak = currentCapital;
                }

                // Calculate the current drawdown as a percentage
                double currentDrawdown = (historicalPeak - currentCapital) / historicalPeak;

                // If the current drawdown is greater than the maximum drawdown recorded
                if (currentDrawdown > maxDrawdown) {
                    // Update the maximum drawdown
                    maxDrawdown = currentDrawdown;
                }
            }
        }

        // Calculate the total return as a percentage
        double totalReturn = (currentCapital - startingCapital) / startingCapital;
        // Calculate the win rate as a percentage
        double winRate = ((double) wins / trades) * 100;

        // Output the results of the backtest
        System.out.println("Win Rate: " + String.format("%.3f", winRate) + "%");
        System.out.println("Total Return: " + String.format("%.3f", (totalReturn * 100)) + "%");
        System.out.println("Max Drawdown: " + String.format("%.3f", (maxDrawdown * 100)) + "%");
    }

}
