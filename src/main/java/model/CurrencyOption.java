package model;

import java.util.Currency;

public class CurrencyOption {

    final int id;
    private final Currency currency;

    public CurrencyOption(int id, Currency currency) {
        this.id = id;
        this.currency = currency;
    } 
 
    public int getId() {
        return id;
    }

    public Currency getCurrency() {
        return currency;
    }

}
