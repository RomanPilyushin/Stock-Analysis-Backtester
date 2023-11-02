package equityModel.utils;

import com.crazzyghost.alphavantage.AlphaVantage;
import com.crazzyghost.alphavantage.Config;
import com.crazzyghost.alphavantage.parameters.DataType;
import com.crazzyghost.alphavantage.timeseries.response.StockUnit;
import com.crazzyghost.alphavantage.timeseries.response.TimeSeriesResponse;
import equityModel.data.StockData;
import java.util.List;
import java.util.stream.Collectors;

public class DataFetcher {

    static {
        // Initialize AlphaVantage with configuration
        Config cfg = Config.builder()
                .key("AOC28O26TBLJ0XJB")
                .timeOut(10)
                .build();
        AlphaVantage.api().init(cfg);
    }

    public static void fetchDataForCompany(String companyTicker, FetchDataType dataType) {
        switch (dataType) {
            case INTRADAY:
                fetchIntradayData(companyTicker);
                break;
            case MONTH:
                fetchMonthlyData(companyTicker);
                break;
            case WEEK:
                fetchWeeklyData(companyTicker);
                break;
            default:
                throw new IllegalArgumentException("Unsupported data type");
        }
    }

    private static void fetchIntradayData(String companyTicker) {
        AlphaVantage.api()
                .timeSeries()
                .intraday()
                .forSymbol(companyTicker)
                .dataType(DataType.JSON)
                .onSuccess(response -> handleData(companyTicker, FetchDataType.INTRADAY, (TimeSeriesResponse) response))
                .onFailure(DataFetcher::handleError)
                .fetch();
    }

    private static void fetchMonthlyData(String companyTicker) {
        AlphaVantage.api()
                .timeSeries()
                .monthly()
                .adjusted()
                .forSymbol(companyTicker)
                .dataType(DataType.JSON)
                .onSuccess(response -> handleData(companyTicker, FetchDataType.MONTH, (TimeSeriesResponse) response))
                .onFailure(DataFetcher::handleError)
                .fetch();
    }

    private static void fetchWeeklyData(String companyTicker) {
        AlphaVantage.api()
                .timeSeries()
                .weekly()
                .adjusted()
                .forSymbol(companyTicker)
                .dataType(DataType.JSON)
                .onSuccess(response -> handleData(companyTicker, FetchDataType.WEEK, (TimeSeriesResponse) response))
                .onFailure(DataFetcher::handleError)
                .fetch();
    }

    private static void handleData(String companyTicker, FetchDataType dataType, TimeSeriesResponse response) {
        List<StockUnit> stockUnits = response.getStockUnits();
        List<StockData> stockDataList = stockUnits.stream()
                .map(unit -> convertToStockData(unit))
                .collect(Collectors.toList());

        SQLiteStorage.storeStockData(companyTicker, dataType.name(), stockDataList);
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

    private static void handleError(Throwable error) {
        System.out.println("Error: " + error.getMessage());
    }

    public enum FetchDataType {
        INTRADAY, WEEK, MONTH
    }
}
