package controller;

import java.time.LocalDate;
import java.util.List;

import model.Expense;
import service.ExpenseService;
import service.ExpenseTotalService;
import ui.ExpenseTable;
import ui.TotalPanel;

public class ExpenseController {

    private final ExpenseService expenseService;
    private final ExpenseTotalService expenseTotalService;
    private final ExpenseTable expenseTable;
    private final TotalPanel totalPanel;

    public ExpenseController(ExpenseService expenseService, ExpenseTotalService expenseTotalService, ExpenseTable expenseTable, TotalPanel totalPanel) {
        this.expenseService = expenseService;
        this.expenseTotalService = expenseTotalService;
        this.expenseTable = expenseTable;
        this.totalPanel = totalPanel;
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
    private void updateTotals() {
        List<Expense> expenses = expenseTable.getVisibleExpenses();
        double total = expenseTotalService.getTotalExpenses(expenses);
        double daily = expenseTotalService.getDailyAverage(expenses);
        double weekly = expenseTotalService.getWeeklyAverage(expenses);
        double monthly = expenseTotalService.getMonthlyAverage(expenses);
        double yearly = expenseTotalService.getYearlyAverage(expenses);
        totalPanel.updateTotals(daily, weekly, monthly, yearly, total);
    }

}
