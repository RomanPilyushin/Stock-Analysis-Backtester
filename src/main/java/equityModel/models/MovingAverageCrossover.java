package equityModel.models;

// Importing required classes
import equityModel.data.StockData;
import com.tictactec.ta.lib.Core;
import com.tictactec.ta.lib.MInteger;
import com.tictactec.ta.lib.RetCode;
import java.util.List;

// Class definition for MovingAverageCrossover strategy
public class MovingAverageCrossover {

    // Define constants for the short-term and long-term moving average periods
    private static final int SHORT_TERM_PERIOD = 50;
    private static final int LONG_TERM_PERIOD = 200;

    // Method to evaluate a moving average crossover signal
    public boolean evaluate(List<StockData> stockDataList) {
        // Convert the list of stock data to an array of closing prices
        double[] closePrices = stockDataList.stream().mapToDouble(StockData::getClose).toArray();

        // Throw an exception if there are not enough data points to calculate the long-term moving average
        if (closePrices.length < LONG_TERM_PERIOD) {
            throw new IllegalArgumentException("Not enough data to calculate moving averages");
        }

        // Initialize mutable integer references for the start index and length after calculation
        MInteger begin = new MInteger();
        MInteger length = new MInteger();

        // Create arrays to store the computed short-term and long-term moving averages
        double[] outSmaShortTerm = new double[closePrices.length - SHORT_TERM_PERIOD + 1];
        double[] outSmaLongTerm = new double[closePrices.length - LONG_TERM_PERIOD + 1];

        // Instantiate an object of TA-Lib's core class to use its functions
        Core core = new Core();

        // Calculate the short-term moving average using TA-Lib
        RetCode shortTermRes = core.sma(0, closePrices.length - 1, closePrices, SHORT_TERM_PERIOD, begin, length, outSmaShortTerm);
        // Calculate the long-term moving average using TA-Lib
        RetCode longTermRes = core.sma(0, closePrices.length - 1, closePrices, LONG_TERM_PERIOD, begin, length, outSmaLongTerm);

        // Throw an exception if the calculation of moving averages fails
        if (shortTermRes != RetCode.Success || longTermRes != RetCode.Success) {
            throw new RuntimeException("Failed to calculate moving averages using TA-Lib");
        }

        // Determine the index of the last element in the short-term moving average array
        int lastIndexShortTerm = outSmaShortTerm.length - 1;
        // Determine the index of the last element in the long-term moving average array
        int lastIndexLongTerm = outSmaLongTerm.length - 1;

        // Return true if the last short-term moving average is greater than the last long-term moving average
        return outSmaShortTerm[lastIndexShortTerm] > outSmaLongTerm[lastIndexLongTerm];
    }
}
