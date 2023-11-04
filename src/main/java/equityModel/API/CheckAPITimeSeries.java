package equityModel.API;

import com.crazzyghost.alphavantage.AlphaVantage;
import com.crazzyghost.alphavantage.Config;
import com.crazzyghost.alphavantage.parameters.DataType;
import com.crazzyghost.alphavantage.timeseries.response.StockUnit;
import com.crazzyghost.alphavantage.timeseries.response.TimeSeriesResponse;
import equityModel.utils.AlphaVantageConfig;

import java.util.List;

public class CheckAPITimeSeries {

    static {
        // Initialize AlphaVantage
        Config cfg = AlphaVantageConfig.getConfig();
        AlphaVantage.api().init(cfg);
    }

    public static void main(String[] args) {
        getTimeSeries("GS");
    }

    public static void getTimeSeries(String ticker) {
        AlphaVantage.api()
                .timeSeries()
                .intraday()
                .forSymbol(ticker)
                .dataType(DataType.JSON)
                .onSuccess((TimeSeriesResponse e) -> printData(e.getStockUnits()))
                .fetch();
    }

    public static void printData(List<StockUnit> stockUnits){
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
