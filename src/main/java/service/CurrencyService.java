package service;

import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

import model.CurrencyOption;

public class CurrencyService {

    private final List<CurrencyOption> currencies = new ArrayList<>();
    private static Currency selectedCurrency;

    // Add Currency
    public void addCurrency(int id, Currency currency) {
        currencies.add(new CurrencyOption(id, currency));
        selectedCurrency = currency;
    }

    public Currency getSelectedCurrency() {
        return selectedCurrency;
    }

}
