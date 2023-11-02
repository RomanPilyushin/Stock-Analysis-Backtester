package equityModel.utils;

import java.util.Arrays;
import java.util.List;

public enum FetchDataType {
    INTRADAY {
        @Override
        public List<String> toDataTypeList() {
            return Arrays.asList("intraday", "1min");
        }
    },
    WEEK {
        @Override
        public List<String> toDataTypeList() {
            return Arrays.asList("weekly", "adjusted");
        }
    },
    MONTH {
        @Override
        public List<String> toDataTypeList() {
            return Arrays.asList("monthly", "adjusted");
        }
    };

    public abstract List<String> toDataTypeList();
}
