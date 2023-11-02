package equityModel;

import java.util.stream.Collectors;
import com.crazzyghost.alphavantage.AlphaVantage;
import com.crazzyghost.alphavantage.Config;
import com.crazzyghost.alphavantage.parameters.DataType;
import com.crazzyghost.alphavantage.parameters.Interval;
import com.crazzyghost.alphavantage.parameters.OutputSize;
import com.crazzyghost.alphavantage.timeseries.response.StockUnit;
import com.crazzyghost.alphavantage.timeseries.response.TimeSeriesResponse;
import equityModel.data.StockData;
import equityModel.models.MovingAverageCrossover;

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
                    System.exit(0);
                })
                .onFailure(error -> {
                    System.out.println("Error: " + error.getMessage());
                    System.exit(1);
                })
                .fetch();
    }

    private static StockData convertToStockData(StockUnit unit) {
        return new StockData(
                unit.getDate().toString(),
                unit.getOpen(),
                unit.getHigh(),
                unit.getLow(),
                unit.getClose(),
                unit.getVolume()
        );
    }


    public static void onData(List<StockUnit> stockUnits) {
        List<StockData> stockDataList = stockUnits.stream()
                .map(StrategyTester::convertToStockData)
                .collect(Collectors.toList());

        SQLiteStorage.storeStockData(stockDataList);

        MovingAverageCrossover strategy = new MovingAverageCrossover();
        boolean buySignal = strategy.evaluate(stockDataList);

        System.out.println("Buy Signal: " + buySignal);
    }

}
