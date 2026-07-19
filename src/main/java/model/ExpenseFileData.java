package model;

import java.io.Serializable;
import java.util.Currency;
import java.util.List;

public class ExpenseFileData implements Serializable {

    private List<Expense> expenseData;
    private Currency currencyData;

    public ExpenseFileData(List<Expense> expenseData, Currency currencyData) {
        this.expenseData = expenseData;
        this.currencyData = currencyData;
    }

    public List<Expense> getExpenseData() {
        return expenseData;
    }

    public Currency getCurrencyData() {
        return currencyData;
    }

}
