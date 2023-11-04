package org.example;

import equityModel.utils.FetchDataType;
import equityModel.utils.SQLiteStorage;
import junit.framework.TestCase;
import equityModel.data.StockData;
import java.util.Collections;
import java.util.List;

public class SQLiteStorageTest extends TestCase {

    // Before each test, set up the database or mock it
    protected void setUp() throws Exception {
        super.setUp();
        // Perform initialization like connecting to the test database or setting up mocks
    }

    // After each test, tear down the test environment
    protected void tearDown() throws Exception {
        // Clean up like closing database connections or resetting mocks
        super.tearDown();
    }

    // Test storing stock data
    public void testStoreStockData() {
        String companyTicker = "GS";
        String dataType = "week";
        List<StockData> dataList = Collections.singletonList(
                new StockData("2023_11_03", 293.45, 329.33, 293.29, 327.62, 14360569)
                // Add more StockData objects as needed for the test
        );

        // Call the method to test
        SQLiteStorage.storeStockData(companyTicker, dataType, dataList);

        // Verify the result, possibly by querying the test database or checking mocks
        assertTrue(SQLiteStorage.doesCompanyDataExist(companyTicker, FetchDataType.WEEK, "2023_11_04"));
    }

    // Test retrieving stock data for a company
    public void testGetStockDataForCompany() {
        String companyTicker = "GS";
        FetchDataType dataType = FetchDataType.WEEK;

        // Assuming data is already in the database or you've mocked the database response
        List<StockData> result = SQLiteStorage.getStockDataForCompany(companyTicker, dataType);

        // Validate the result
        assertNotNull(result);
        assertFalse(result.isEmpty());
        // Further assertions to validate the actual data
    }

    // Additional tests can be added here to cover doesCompanyDataExist, etc.
}
