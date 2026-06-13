package service;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import model.Expense;

public class ExpenseService {

    private final List<Expense> expenses = new ArrayList<>();
    private int nextId = 1;

    // Add Expense
    public void addExpense(String itemName, String type, String category, double itemCost, LocalDate date) {
        Expense expense = new Expense(nextId, itemName, type, category, itemCost, date);
        expenses.add(expense);
        nextId++;
    }

    //  Find All Expenses
    public List<Expense> findAllExpenses() {
        return expenses;
    }

    // Find Expense By ID
    public Expense findExpenseById(int id) {
        for (Expense expense : expenses) {
            if (expense.getId() == id) {
                return expense;
            }
        }
        return null;
    }

    // Update Expense
    public Expense updateExpense(int id, Expense updatedExpense) {
        Expense expense = findExpenseById(id);
        if (expense == null) {
            return null;
        }
        expense.setItemName(updatedExpense.getItemName());
        expense.setType(updatedExpense.getType());
        expense.setCategory(updatedExpense.getCategory());
        expense.setItemCost(updatedExpense.getItemCost());
        expense.setDate(updatedExpense.getDate());
        return expense;
    }

    // Delete Expense
    public boolean deleteExpense(int id) {
        Expense expense = findExpenseById(id);
        if (expense == null) {
            return false;
        }
        expenses.remove(expense);
        return true;
    }

    // Delete Visible Expenses
    public void deleteVisibleExpenses(List<Expense> visibleExpenses) {
        expenses.removeAll(visibleExpenses);
    }

}
