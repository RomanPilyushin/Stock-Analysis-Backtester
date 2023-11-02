package equityModel.utils;

import com.crazzyghost.alphavantage.AlphaVantage;
import com.crazzyghost.alphavantage.Config;
import com.crazzyghost.alphavantage.parameters.Interval;
import com.crazzyghost.alphavantage.parameters.OutputSize;
import com.crazzyghost.alphavantage.timeseries.TimeSeries;
import com.crazzyghost.alphavantage.timeseries.response.TimeSeriesResponse;

public class AlphaVantageDataFetcher {

    public static void setupAlphaVantage() {
        // Initialize Alpha Vantage with your API key
        Config config = Config
                .builder()
                .key("AOC28O26TBLJ0XJB")
                .timeOut(10)
                .build();
        AlphaVantage.api().init(config);
    }

    public static void fetchDailyAdjusted(String symbol) {
        TimeSeriesResponse response = AlphaVantage.api()
                .timeSeries()
                .intraday()
                .forSymbol("IBM")
                .interval(Interval.FIVE_MIN)
                .outputSize(OutputSize.FULL)
                .fetchSync();
    }

    public static void main(String[] args) {
        setupAlphaVantage();
        fetchDailyAdjusted("AAPL");
    }
}
