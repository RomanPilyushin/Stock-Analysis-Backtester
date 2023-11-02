package equityModel.utils;

import com.crazzyghost.alphavantage.AlphaVantage;
import com.crazzyghost.alphavantage.Config;
import com.crazzyghost.alphavantage.parameters.DataType;
import com.crazzyghost.alphavantage.timeseries.response.StockUnit;
import com.crazzyghost.alphavantage.timeseries.response.TimeSeriesResponse;

import java.util.List;

public class CheckAPI {

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
                .forSymbol("AMZN")
                .dataType(DataType.JSON)
                .onSuccess((TimeSeriesResponse e) -> onData(e.getStockUnits()))
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
