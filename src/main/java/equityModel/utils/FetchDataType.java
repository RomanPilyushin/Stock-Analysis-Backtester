package equityModel.utils;

public enum FetchDataType {
    INTRADAY("intraday"),
    DAILY("daily"),
    MONTH("month"),
    WEEK("week");

    private final String stringValue;

    FetchDataType(String value) {
        this.stringValue = value;
    }

    public String getString() {
        return stringValue;
    }
}