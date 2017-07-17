package Controller;

import net.webservicex.Currency;

public class CurrencyConverter {

    public static void main(String[] args) {
        System.out.println(convertLKRtoUSD(1));
    }
//    

    public static double convertLKRtoUSD(double lkr) {
        return conversionRate(Currency.LKR, Currency.USD) * lkr;
    }

    private static double conversionRate(net.webservicex.Currency fromCurrency, net.webservicex.Currency toCurrency) {
        net.webservicex.CurrencyConvertor service = new net.webservicex.CurrencyConvertor();
        net.webservicex.CurrencyConvertorSoap port = service.getCurrencyConvertorSoap();
        return port.conversionRate(fromCurrency, toCurrency);
    }

    public static Double convertUSDtoLKR(double amount) {
        double rate = 0.0;
        try {
            rate = conversionRate(Currency.USD, Currency.LKR) * amount;
        } catch (Exception e) {
        }
        return rate;
    }

}
