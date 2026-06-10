import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ExpenseManager {

    private final List<Expense> expenses = new ArrayList<>();
    private int nextId = 1;

    public void addExpense(String itemName, String type, String category, double itemCost, LocalDate date) {
        Expense expense = new Expense(nextId, itemName, type, category, itemCost, date);
        expenses.add(expense);
        nextId++;
    }

    public List<Expense> findAllExpenses() {
        return expenses;
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
        expense.setType(updatedExpense.getType());
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

    public void deleteVisibleExpenses(List<Expense> visibleExpenses) {
        expenses.removeAll(visibleExpenses);
    }

    public double getTotalExpenses(List<Expense> visibleExpenses) {
        double total = 0;
        for (Expense expense : visibleExpenses) {
            total += expense.getItemCost();
        }
        return total;
    }

    public List<Expense> filterByDate(List<Expense> list, LocalDate date) {
        List<Expense> eList = new ArrayList<>();
        for (Expense expense : list) {
            if (expense.getDate().equals(date)) {
                eList.add(expense);
            }
        }
        return eList;
    }

    public List<Expense> filterByDateRange(List<Expense> list, LocalDate startDate, LocalDate endDate) {
        List<Expense> eList = new ArrayList<>();
        for (Expense expense : list) {
            if (!expense.getDate().isBefore(startDate) && !expense.getDate().isAfter(endDate)) {
                eList.add(expense);
            }
        }
        return eList;
    }

    public List<Expense> sortByDateAscending(List<Expense> list) {
        List<Expense> sorted = new ArrayList<>(list);

        sorted.sort(Comparator.comparing(e -> e.getDate()));

        return sorted;
    }

    public List<Expense> sortByDateDescending(List<Expense> list) {
        List<Expense> sorted = new ArrayList<>(list);

        sorted.sort(Comparator.comparing((Expense e) -> e.getDate()).reversed());
        
        return sorted;
    }

    public Set<String> getAllTypes() {
        HashSet<String> types = new HashSet<>();
        for (Expense expense : expenses) {
            types.add(expense.getType());
        }
        return types;
    }

    public List<Expense> filterByType(List<Expense> list, String type) {
        List<Expense> eList = new ArrayList<>();
        String cleanType = type.trim();
        for (Expense expense : list) {
            String expenseType = expense.getType().trim();
            if (expenseType.equals(cleanType)) {
                eList.add(expense);
            }
        }
        return eList;
    }

    public Set<String> getAllCategories() {
        HashSet<String> categories = new HashSet<>();
        for (Expense expense : expenses) {
            categories.add(expense.getCategory());
        }
        return categories;
    }

    public List<Expense> filterByCategory(List<Expense> list, String category) {
        List<Expense> eList = new ArrayList<>();
        String cleanCategory = category.trim();
        for (Expense expense : list) {
            String expenseCategory = expense.getCategory().trim();
            if (expenseCategory.equals(cleanCategory)) {
                eList.add(expense);
            }
        }
        return eList;
    }

    public List<Expense> filterByCostRange(List<Expense> list, double minCost, double maxCost) {
        List<Expense> eList = new ArrayList<>();
        for (Expense expense : list) {
            if (expense.getItemCost() >= minCost && expense.getItemCost() <= maxCost) {
                eList.add(expense);
            }
        }
        return eList;
    }

    public List<Expense> sortByCostAscending(List<Expense> list) {
        List<Expense> sorted = new ArrayList<>(list);

        sorted.sort(Comparator.comparing(e -> e.getItemCost()));

        return sorted;
    }

    public List<Expense> sortByCostDescending(List<Expense> list) {
        List<Expense> sorted = new ArrayList<>(list);

        sorted.sort(Comparator.comparing((Expense e) -> e.getItemCost()).reversed());

        return sorted;
    }

}
