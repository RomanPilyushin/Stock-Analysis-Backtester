package equityModel.utils;

import java.util.stream.Collectors;
import com.crazzyghost.alphavantage.AlphaVantage;
import com.crazzyghost.alphavantage.Config;
import com.crazzyghost.alphavantage.parameters.DataType;
import com.crazzyghost.alphavantage.timeseries.response.StockUnit;
import com.crazzyghost.alphavantage.timeseries.response.TimeSeriesResponse;
import equityModel.data.StockData;
import equityModel.models.MovingAverageCrossover;

import java.util.List;

public class StrategyTester {

    public static void main(String[] args) {
        DataFetcher.fetchDataForCompany("AAPL", DataFetcher.FetchDataType.MONTH);
    }

}
