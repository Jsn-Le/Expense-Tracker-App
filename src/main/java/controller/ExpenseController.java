package controller;

import java.time.LocalDate;
import java.util.List;

import model.Expense;
import service.ExpenseService;
import ui.ExpenseTable;

public class ExpenseController {

    private final ExpenseService expenseService;
    private final ExpenseTable expenseTable;

    public ExpenseController(ExpenseService expenseService, ExpenseTable expenseTable) {
        this.expenseService = expenseService;
        this.expenseTable = expenseTable;
    }

    // ExpenseService Method Calls
    public void addExpense(String itemName, String type, String category, double cost, LocalDate date) {
        expenseService.addExpense(itemName, type, category, cost, date);
        expenseTable.refreshView();
    }

    public void updateExpense(int id, Expense updatedExpense) {
        expenseService.updateExpense(id, updatedExpense);
        expenseTable.refreshView();
    }

    public void deleteExpense(int id) {
        expenseService.deleteExpense(id);
        expenseTable.refreshView();
    }

    public void deleteVisibleExpenses(List<Expense> visibleExpenses) {
        expenseService.deleteVisibleExpenses(visibleExpenses);
    }

}
