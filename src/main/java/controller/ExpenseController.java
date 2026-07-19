package controller;

import java.io.File;
import java.time.LocalDate;
import java.util.Currency;
import java.util.List;

import model.Expense;
import model.ExpenseFileData;
import service.CurrencyService;
import service.ExpenseFileService;
import service.ExpenseService;
import service.ExpenseTotalService;
import ui.ExpenseTable;
import ui.TotalPanel;

public class ExpenseController {

    private final CurrencyService currencyService;
    private final ExpenseFileService expenseFileService;
    private final ExpenseService expenseService;
    private final ExpenseTotalService expenseTotalService;
    private final ExpenseTable expenseTable;
    private final TotalPanel totalPanel;

    public ExpenseController(CurrencyService currencyService, ExpenseFileService expenseFileService, ExpenseService expenseService, ExpenseTotalService expenseTotalService, ExpenseTable expenseTable, TotalPanel totalPanel) {
        this.currencyService = currencyService;
        this.expenseFileService = expenseFileService;
        this.expenseService = expenseService;
        this.expenseTotalService = expenseTotalService;
        this.expenseTable = expenseTable;
        this.totalPanel = totalPanel;
    }

    private File currentFile = null;

    // ExpenseFileService Method Calls
    public File getCurrentFile() {
        return currentFile;
    }

    public void newFile() {
        expenseTable.resetFilters();
        deleteVisibleExpenses(expenseService.findAllExpenses());
        currentFile = null;
    }

    public void saveAsFile(File file) {
        List<Expense> expenses = expenseService.findAllExpenses();
        Currency currency = currencyService.getSelectedCurrency();
        ExpenseFileData expenseFileData = new ExpenseFileData(expenses, currency);
        currentFile = file;
        expenseFileService.saveFile(expenseFileData, file);
    }

    public boolean openFile(File file) {
        ExpenseFileData expenseFileData = expenseFileService.openFile(file);

        if (expenseFileData == null) {
            return false;
        }

        currentFile = file;
        currencyService.selectCurrency(expenseFileData.getCurrencyData());
        expenseService.loadExpenses(expenseFileData.getExpenseData());
        expenseTable.refreshView();
        updateTotals();

        return true;
    }

    public boolean saveFile() {
        if (currentFile == null) {
            return false;
        }

        List<Expense> expenses = expenseService.findAllExpenses();
        Currency currency = currencyService.getSelectedCurrency();
        ExpenseFileData expenseFileData = new ExpenseFileData(expenses, currency);
        expenseFileService.saveFile(expenseFileData, currentFile);

        return true;
    }

    // ExpenseService Method Calls
    public void addExpense(String itemName, String type, String category, double cost, LocalDate date) {
        expenseService.addExpense(itemName, type, category, cost, date);
        expenseTable.refreshView();
        updateTotals();
    }

    public void updateExpense(int id, Expense updatedExpense) {
        expenseService.updateExpense(id, updatedExpense);
        expenseTable.refreshView();
        updateTotals();
    }

    public void deleteExpense(int id) {
        expenseService.deleteExpense(id);
        expenseTable.refreshView();
        updateTotals();
    }

    public void deleteVisibleExpenses(List<Expense> visibleExpenses) {
        expenseService.deleteVisibleExpenses(visibleExpenses);
        expenseTable.refreshView();
        updateTotals();
    }

    // Update Totals
    public void updateTotals() {
        List<Expense> expenses = expenseTable.getVisibleExpenses();
        String total = expenseTotalService.getTotalExpenses(expenses);
        String daily = expenseTotalService.getDailyAverage(expenses);
        String weekly = expenseTotalService.getWeeklyAverage(expenses);
        String monthly = expenseTotalService.getMonthlyAverage(expenses);
        String yearly = expenseTotalService.getYearlyAverage(expenses);
        totalPanel.updateTotals(daily, weekly, monthly, yearly, total);
    }

}
