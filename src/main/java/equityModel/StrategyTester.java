package equityModel;

import com.crazzyghost.alphavantage.AlphaVantage;
import com.crazzyghost.alphavantage.Config;
import com.crazzyghost.alphavantage.parameters.DataType;
import com.crazzyghost.alphavantage.parameters.Interval;
import com.crazzyghost.alphavantage.parameters.OutputSize;
import com.crazzyghost.alphavantage.timeseries.response.StockUnit;
import com.crazzyghost.alphavantage.timeseries.response.TimeSeriesResponse;

import java.util.List;

public class StrategyTester {

    static {
        // Initialize AlphaVantage with configuration
        Config cfg = Config.builder()
                .key("AOC28O26TBLJ0XJB")
                .timeOut(10)
                .build();
        AlphaVantage.api().init(cfg);
    }

    public static void main(String[] args) {
        AlphaVantage.api()
                .timeSeries()
                .intraday()
                .forSymbol("IBM")
                .interval(Interval.FIVE_MIN)
                .outputSize(OutputSize.FULL)
                .dataType(DataType.JSON)
                .onSuccess(response -> {
                    TimeSeriesResponse tsResponse = (TimeSeriesResponse) response;
                    onData(tsResponse.getStockUnits());
                })
                .onFailure(error -> {
                    System.out.println("Error: " + error.getMessage());
                })
                .fetch();
    }

    public static void onData(List<StockUnit> stockUnits){
        stockUnits.forEach(u -> {
            System.out.println(u.getHigh());
            System.out.println(u.getLow());
            System.out.println(u.getOpen());
            System.out.println(u.getClose());
            System.out.println(u.getVolume());
            System.out.println(u.getAdjustedClose());
            System.out.println(u.getDividendAmount());
            System.out.println(u.getSplitCoefficient());
            System.out.println(u.getDate());
        });
    }
}
