package equityModel.models;

import equityModel.data.StockData;
import java.util.List;

public interface TradingStrategy {
    String evaluate(List<StockData> data);
}
