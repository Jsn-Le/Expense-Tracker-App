package service;

import java.util.Currency;

import model.CurrencyOption;

public class CurrencyService {

    private final CurrencyOption currencyOption;

    public CurrencyService(CurrencyOption currencyOption) {
        this.currencyOption = currencyOption;
    }

    private Currency selectedCurrency = Currency.getInstance("USD");

    // Add Currency
    public void selectCurrency(Currency currency) {
        currencyOption.setCurrency(currency);
        selectedCurrency = currency;
    }

    public Currency getSelectedCurrency() {
        return selectedCurrency;
    }

}
