package equityModel.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StockData {
    private String date;
    private double open;
    private double high;
    private double low;
    private double close;
    private long volume;
}
