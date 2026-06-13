package ui;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import model.Expense;
import model.ExpenseFilter;
import service.ExpenseFilterService;
import service.ExpenseService;

public class ExpenseTable extends AbstractTableModel {

    private final ExpenseFilterService expenseFilterService;
    private final ExpenseService expenseService;
    private final ExpenseFilter filter;
    private List<Expense> currentView;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public ExpenseTable(ExpenseFilterService expenseFilterService, ExpenseService expenseService, ExpenseFilter filter) {
        this.expenseFilterService = expenseFilterService;
        this.expenseService = expenseService;
        this.filter = filter;
        currentView = expenseService.findAllExpenses();
    }

    @Override
    public int getColumnCount() {
        return 5;
    }

    @Override
    public int getRowCount() {
        return currentView.size();
    }

    @Override
    public String getColumnName(int column) {
        return switch(column) {
            case 0 -> "Item";
            case 1 -> "Type";
            case 2 -> "Category";
            case 3 -> "Cost";
            case 4 -> "Date";
            default -> null;
        };
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Expense expense = currentView.get(rowIndex);

        return switch(columnIndex) {
            case 0 -> expense.getItemName();
            case 1 -> expense.getType();
            case 2 -> expense.getCategory();
            case 3 -> expense.getItemCost();
            case 4 -> expense.getDate() != null
                        ? expense.getDate().format(formatter)
                        : "";
            default -> null;
        };
    }

    public Expense getExpenseAt(int rowIndex) {
        return currentView.get(rowIndex);
    }

    public List<Expense> getVisibleExpenses() {
        applyFilters();
        return currentView;
    }

    public void refreshView() {
        applyFilters();
        fireTableDataChanged();
    }

    public void applyFilters() {
        currentView = expenseService.findAllExpenses();
        
        if (!filter.getSelectedFilterType().isBlank()) {
            currentView = expenseFilterService.filterByType(currentView, filter.getSelectedFilterType());
        }
        if (!filter.getSelectedFilterCategory().isBlank()) {
            currentView = expenseFilterService.filterByCategory(currentView, filter.getSelectedFilterCategory());
        }
        if (filter.getMinCost() != null && filter.getMaxCost() != null) {
            currentView = expenseFilterService.filterByCostRange(currentView, filter.getMinCost(), filter.getMaxCost());
        }
        if (filter.getStartDate() != null && filter.getEndDate() != null) {
            currentView = expenseFilterService.filterByDateRange(currentView, filter.getStartDate(), filter.getEndDate());
        }

        if (filter.getSortCost()) {
            currentView = expenseFilterService.sortByCostDescending(currentView);
        } else {
            currentView = expenseFilterService.sortByCostAscending(currentView);
        }
        if (filter.getSortDate()) {
            currentView = expenseFilterService.sortByDateDescending(currentView);
        } else {
            currentView = expenseFilterService.sortByDateAscending(currentView);
        }
    }

    public void resetFilters() {
        filter.setSelectedFilterType("");
        filter.setSelectedFilterCategory("");
        filter.setSortCost(true);
        filter.setSortDate(true);
        filter.setMinCost(null);
        filter.setMaxCost(null);
        filter.setStartDate(null);
        filter.setEndDate(null);

        refreshView();
    }

}
