package model;

import java.util.Currency;

public class CurrencyOption {

    private Currency currency = null;

    public CurrencyOption(Currency currency) {
        this.currency = currency;
    } 

    public Currency getCurrency() {
        return currency;
    }

    public  void setCurrency(Currency currency) {
        this.currency = currency;
    }

}
