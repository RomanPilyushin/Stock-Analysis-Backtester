package equityModel.utils;

import com.crazzyghost.alphavantage.AlphaVantage;
import com.crazzyghost.alphavantage.Config;

public class AlphaVantageConfig {
    private static final String APIkey = "AOC28O26TBLJ0XJB";

    public static Config getConfig() {

        return Config.builder()
                .key(APIkey)
                .timeOut(10)
                .build();
    }
}