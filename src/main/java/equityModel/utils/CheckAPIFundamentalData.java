package equityModel.utils;

import com.crazzyghost.alphavantage.AlphaVantage;
import com.crazzyghost.alphavantage.Config;
import com.crazzyghost.alphavantage.fundamentaldata.response.*;

import java.util.List;

public class CheckAPIFundamentalData {

    static {
        // Initialize AlphaVantage with configuration
        Config cfg = Config.builder()
                .key("AOC28O26TBLJ0XJB")
                .timeOut(10)
                .build();
        AlphaVantage.api().init(cfg);
    }

    public static void main(String[] args) {
        String s = "GOOGL";

        //AlphaVantageAPICashFlow(s);
        //AlphaVantageAPIBalanceSheet(s);
        //AlphaVantageAPIIncomeStatement(s);
        //AlphaVantageAPICompanyOverview(s);

    }

    public static void AlphaVantageAPICashFlow(String company){
        AlphaVantage
                .api()
                .fundamentalData()
                .cashFlow()
                .forSymbol(company)
                .onSuccess((CashFlowResponse e) -> getCashFlow(e.getAnnualReports()))
                .fetch();
    }
    public static void AlphaVantageAPIIncomeStatement(String company){
        AlphaVantage
                .api()
                .fundamentalData()
                .incomeStatement()
                .forSymbol(company)
                .onSuccess((IncomeStatementResponse e) -> getIncomeStatement(e.getAnnualReports()))
                .fetch();
    }
    public static void AlphaVantageAPIBalanceSheet(String company){
        AlphaVantage
                .api()
                .fundamentalData()
                .balanceSheet()
                .forSymbol(company)
                .onSuccess((BalanceSheetResponse e) -> getBalanceSheet(e.getAnnualReports()))
                .fetch();
    }
    public static void AlphaVantageAPICompanyOverview(String company){
        AlphaVantage
                .api()
                .fundamentalData()
                .companyOverview()
                .forSymbol(company)
                .onSuccess((CompanyOverviewResponse e) -> getCompanyOverview(e.getOverview()))
                .fetch();
    }
    public static void getCashFlow(List<CashFlow> cashFlowResponseList) {
        cashFlowResponseList.forEach(u -> {
            System.out.println(u.getNetIncome());
            System.out.println(u.getProfitLoss());
            System.out.println(u.getDividendPayout());
            System.out.println(u.getDividendPayoutCommonStock());
            System.out.println(u.getDividendPayoutPreferredStock());
            System.out.println(u.getChangeInCashAndCashEquivalents());
            System.out.println(u.getPaymentsForOperatingActivities());
            System.out.println(u.getProceedsFromOperatingActivities());
            System.out.println(u.getProceedsFromRepurchaseOfEquity());
            System.out.println(u.getProceedsFromSaleOfTreasuryStock());
            System.out.println(u.getDepreciationDepletionAndAmortization());
            System.out.println(u.getPaymentsForRepurchaseOfCommonStock());
            System.out.println(u.getPaymentsForRepurchaseOfPreferredStock());
            System.out.println(u.getOperatingCashflow());
            System.out.println(u.getChangeInOperatingAssets());
            System.out.println(u.getCashflowFromInvestment());
            System.out.println(u.getCapitalExpenditures());
            System.out.println(u.getChangeInReceivables());
            System.out.println(u.getChangeInInventory());
        });
    }
    public static void getIncomeStatement(List<IncomeStatement> incomeStatementResponseList) {
        incomeStatementResponseList.forEach(u -> {
            System.out.println(u.getCostofGoodsAndServicesSold());
            System.out.println(u.getSellingGeneralAndAdministrative());
            System.out.println(u.getIncomeBeforeTax());
            System.out.println(u.getNetIncome());
            System.out.println(u.getCostOfRevenue());
            System.out.println(u.getInterestExpense());
            System.out.println(u.getGrossProfit());
            System.out.println(u.getInterestIncome());
            System.out.println(u.getTotalRevenue());
            System.out.println(u.getOperatingIncome());
            System.out.println(u.getDepreciation());
            System.out.println(u.getNetIncomeFromContinuingOperations());
            System.out.println(u.getEbitda());
            System.out.println(u.getEbit());
            System.out.println(u.getDepreciationAndAmortization());
            System.out.println(u.getResearchAndDevelopment());
            System.out.println(u.getInvestmentIncomeNet());
            System.out.println(u.getOtherNonOperatingIncome());
            System.out.println(u.getNetInterestIncome());
            System.out.println(u.getIncomeTaxExpense());
            System.out.println(u.getNonInterestIncome());
            System.out.println(u.getOperatingExpenses());
            System.out.println(u.getInterestAndDebtExpense());
        });
    }
    public static void getBalanceSheet(List<BalanceSheet> balanceSheetResponseList) {
        balanceSheetResponseList.forEach(u -> {
            System.out.println(u.getTreasuryStock());
            System.out.println(u.getCurrentDebt());
            System.out.println(u.getLongTermDebt());
            System.out.println(u.getShortTermDebt());
            System.out.println(u.getInvestments());
            System.out.println(u.getDeferredRevenue());
            System.out.println(u.getGoodWill());
            System.out.println(u.getCommonStock());
            System.out.println(u.getTotalAssets());
            System.out.println(u.getInventory());
            System.out.println(u.getLongTermDebtNonCurrent());
            System.out.println(u.getPropertyPlantEquipment());
            System.out.println(u.getIntangibleAssets());
            System.out.println(u.getCurrentAccountsPayable());
            System.out.println(u.getShortLongTermDebtTotal());
            System.out.println(u.getOtherNonCurrentAssets());
            System.out.println(u.getCurrentLongTermDebt());
            System.out.println(u.getOtherCurrentLiabilities());
            System.out.println(u.getTotalShareholderEquity());
            System.out.println(u.getTotalCurrentLiabilities());
            System.out.println(u.getTotalNonCurrentAssets());
            System.out.println(u.getCurrentNetReceivables());
            System.out.println(u.getOtherCurrentAssets());
            System.out.println(u.getTotalLiabilities());
            System.out.println(u.getRetainedEarnings());
            System.out.println(u.getShortTermInvestments());
            System.out.println(u.getTotalCurrentAssets());
            System.out.println(u.getCommonStockSharesOutstanding());
            System.out.println(u.getTotalNonCurrentLiabilities());
            System.out.println(u.getOtherNonCurrentLiabilities());
            System.out.println(u.getCashAndShortTermInvestments());
            System.out.println(u.getAccumulatedDepreciationAmortizationPPE());
            System.out.println(u.getCashAndCashEquivalentsAtCarryingValue());
            System.out.println(u.getIntangibleAssetsExcludingGoodwill());
        });
    }
    public static void getCompanyOverview(CompanyOverview companyOverview) {

        System.out.println(companyOverview.getForwardPE());
        System.out.println(companyOverview.getEvToRevenue());
        System.out.println(companyOverview.getExDividendDate());
        System.out.println(companyOverview.getTrailingPE());
        System.out.println(companyOverview.getEvToEBITDA());
        System.out.println(companyOverview.getFiftyTwoWeekLow());
        System.out.println(companyOverview.getIndustry());
        System.out.println(companyOverview.getGrossProfitTTM());
        System.out.println(companyOverview.getPEGRatio());
        System.out.println(companyOverview.getDividendYield());
        System.out.println(companyOverview.getDescription());
        System.out.println(companyOverview.getBookValue());
        System.out.println(companyOverview.getProfitMargin());
        System.out.println(companyOverview.getRevenueTTM());
        System.out.println(companyOverview.getTwoHundredDayMovingAverage());
        System.out.println(companyOverview.getPERatio());
        System.out.println(companyOverview.getEBITDA());
        System.out.println(companyOverview.getEPS());
        System.out.println(companyOverview.getSector());
        System.out.println(companyOverview.getBeta());
        System.out.println(companyOverview.getRevenuePerShareTTM());
        System.out.println(companyOverview.getOperatingMarginTTM());
        System.out.println(companyOverview.getReturnOnAssetsTTM());
        System.out.println(companyOverview.getReturnOnEquityTTM());
        System.out.println(companyOverview.getMarketCapitalization());
        System.out.println(companyOverview.getDividendPerShare());
        System.out.println(companyOverview.getFiftyTwoWeekHigh());
        System.out.println(companyOverview.getAnalystTargetPrice());
        System.out.println(companyOverview.getPriceToBookRatio());
        System.out.println(companyOverview.getPriceToSaleRatioTTM());
        System.out.println(companyOverview.getName());
        System.out.println(companyOverview.getAddress());
        System.out.println(companyOverview.getCurrency());
        System.out.println(companyOverview.getSymbol());
        System.out.println(companyOverview.getCountry());
    }
}
