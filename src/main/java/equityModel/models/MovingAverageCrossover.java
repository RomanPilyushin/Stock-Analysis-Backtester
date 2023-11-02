package equityModel.models;

import equityModel.data.StockData;
import com.tictactec.ta.lib.Core;
import com.tictactec.ta.lib.MInteger;
import com.tictactec.ta.lib.RetCode;
import java.util.List;

public class MovingAverageCrossover {

    private static final int SHORT_TERM_PERIOD = 50;
    private static final int LONG_TERM_PERIOD = 200;

    public boolean evaluate(List<StockData> stockDataList) {
        double[] closePrices = stockDataList.stream().mapToDouble(StockData::getClose).toArray();

        if (closePrices.length < LONG_TERM_PERIOD) {
            throw new IllegalArgumentException("Not enough data to calculate moving averages");
        }

        MInteger begin = new MInteger();
        MInteger length = new MInteger();

        double[] outSmaShortTerm = new double[closePrices.length - SHORT_TERM_PERIOD + 1];
        double[] outSmaLongTerm = new double[closePrices.length - LONG_TERM_PERIOD + 1];

        Core core = new Core();

        RetCode shortTermRes = core.sma(0, closePrices.length - 1, closePrices, SHORT_TERM_PERIOD, begin, length, outSmaShortTerm);
        RetCode longTermRes = core.sma(0, closePrices.length - 1, closePrices, LONG_TERM_PERIOD, begin, length, outSmaLongTerm);

        if (shortTermRes != RetCode.Success || longTermRes != RetCode.Success) {
            throw new RuntimeException("Failed to calculate moving averages using TA-Lib");
        }

        int lastIndexShortTerm = outSmaShortTerm.length - 1;
        int lastIndexLongTerm = outSmaLongTerm.length - 1;

        return outSmaShortTerm[lastIndexShortTerm] > outSmaLongTerm[lastIndexLongTerm];
    }
}
