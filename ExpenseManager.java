import java.time.*;
import java.util.*;

public class ExpenseManager {

    private final List<Expense> expenses = new ArrayList<>();
    private int nextId = 1;

    public void addExpense(String itemName, String category, long itemCost, LocalDate date) {
        Expense expense = new Expense(nextId, itemName, category, itemCost, date);
        expenses.add(expense);
        nextId++;
    }

    public List<Expense> findAllExpenses() {
        return new ArrayList<>(expenses);
    }

    public Expense findExpenseById(int id) {
        for (Expense expense : expenses) {
            if (expense.getId() == id) {
                return expense;
            }
        }
        return null;
    }

    public Expense updateExpense(int id, Expense updatedExpense) {
        Expense expense = findExpenseById(id);
        if (expense == null) {
            return null;
        }
        expense.setItemName(updatedExpense.getItemName());
        expense.setCategory(updatedExpense.getCategory());
        expense.setItemCost(updatedExpense.getItemCost());
        expense.setDate(updatedExpense.getDate());
        return expense;
    }

    public boolean deleteExpense(int id) {
        Expense expense = findExpenseById(id);
        if (expense == null) {
            return false;
        }
        expenses.remove(expense);
        return true;
    }

    public void deleteAllExpenses() {
        expenses.clear();
    }

    public long getTotalExpenses() {
        long total = 0;
        for (Expense expense : expenses) {
            total += expense.getItemCost();
        }
        return total;
    }

    public List<Expense> filterByDate(LocalDate date) {
        List<Expense> eList = new ArrayList<>();
        for (Expense expense : expenses) {
            if (expense.getDate().equals(date)) {
                eList.add(expense);
            }
        }
        return eList;
    }

    public List<Expense> filterByDateRange(LocalDate startDate, LocalDate endDate) {
        List<Expense> eList = new ArrayList<>();
        for (Expense expense : expenses) {
            if (!expense.getDate().isBefore(startDate) && !expense.getDate().isAfter(endDate)) {
                eList.add(expense);
            }
        }
        return eList;
    }

    public List<Expense> sortByDateAscending() {
        expenses.sort(Comparator.comparing(e -> e.getDate()));
        return expenses;
    }

    public List<Expense> sortByDateDescending() {
        expenses.sort(Comparator.comparing((Expense e) -> e.getDate()).reversed());
        return expenses;
    }

    public Set<String> getAllCategories() {
        HashSet<String> categories = new HashSet<>();
        for (Expense expense : expenses) {
            categories.add(expense.getCategory());
        }
        return categories;
    }

    public List<Expense> filterByCategory(String category) {
        List<Expense> eList = new ArrayList<>();
        String cleanCategory = category.trim().toUpperCase();
        for (Expense expense : expenses) {
            String expenseCategory = expense.getCategory().trim().toUpperCase();
            if (expenseCategory.equals(cleanCategory)) {
                eList.add(expense);
            }
        }
        return eList;
    }

}
