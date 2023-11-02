package equityModel.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class TableChecker {

    public static boolean doesTableExist(String companyTicker, FetchDataType fetchDataType) {
        String tableName = DatabaseUtility.generateTableName(companyTicker, fetchDataType.toString());
        return DatabaseUtility.doesTableExist(tableName);
    }

    public static boolean doesTableHaveData(String companyTicker, FetchDataType fetchDataType) {
        String currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        return SQLiteStorage.doesCompanyDataExist(companyTicker, fetchDataType, currentDate);
    }
}
