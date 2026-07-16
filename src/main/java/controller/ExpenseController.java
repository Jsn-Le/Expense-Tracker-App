package controller;

import java.io.File;
import java.time.LocalDate;
import java.util.List;

import model.Expense;
import service.ExpenseFileService;
import service.ExpenseService;
import service.ExpenseTotalService;
import ui.ExpenseTable;
import ui.TotalPanel;

public class ExpenseController {

    private final ExpenseFileService expenseFileService;
    private final ExpenseService expenseService;
    private final ExpenseTotalService expenseTotalService;
    private final ExpenseTable expenseTable;
    private final TotalPanel totalPanel;

    public ExpenseController(ExpenseFileService expenseFileService, ExpenseService expenseService, ExpenseTotalService expenseTotalService, ExpenseTable expenseTable, TotalPanel totalPanel) {
        this.expenseFileService = expenseFileService;
        this.expenseService = expenseService;
        this.expenseTotalService = expenseTotalService;
        this.expenseTable = expenseTable;
        this.totalPanel = totalPanel;
    }

    // ExpenseFileService Method Calls
    public void saveFile(List<Expense> expenses, File file) {
        expenseFileService.saveFile(expenses, file);
    }

    public boolean openFile(File file) {
        List<Expense> expensesList = expenseFileService.openFile(file);

        if (expensesList == null) {
            return false;
        }

        expenseService.loadExpenses(expensesList);
        expenseTable.refreshView();
        updateTotals();

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
