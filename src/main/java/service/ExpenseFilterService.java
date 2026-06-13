package service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import model.Expense;

public class ExpenseFilterService {

    private final List<Expense> expenses = new ArrayList<>();

    // Type Filter
    public Set<String> getAllTypes() {
        Set<String> types = new HashSet<>();
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

    // Category Filter
    public Set<String> getAllCategories() {
        Set<String> categories = new HashSet<>();
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

    // Cost Range  Filter
    public List<Expense> filterByCostRange(List<Expense> list, double minCost, double maxCost) {
        List<Expense> eList = new ArrayList<>();
        for (Expense expense : list) {
            if (expense.getItemCost() >= minCost && expense.getItemCost() <= maxCost) {
                eList.add(expense);
            }
        }
        return eList;
    }

    // Date Range Filter
    public List<Expense> filterByDateRange(List<Expense> list, LocalDate startDate, LocalDate endDate) {
        List<Expense> eList = new ArrayList<>();
        for (Expense expense : list) {
            if (!expense.getDate().isBefore(startDate) && !expense.getDate().isAfter(endDate)) {
                eList.add(expense);
            }
        }
        return eList;
    }

    // Cost Ascending/Descending
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

    // Date Ascending/Descending
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

}
