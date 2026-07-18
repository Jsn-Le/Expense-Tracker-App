package ui;

import java.text.NumberFormat;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import model.CurrencyOption;
import model.Expense;
import model.ExpenseFilter;
import service.CurrencyService;
import service.ExpenseFilterService;
import service.ExpenseService;

public class ExpenseTable extends AbstractTableModel {

    private final ExpenseFilterService expenseFilterService;
    private final ExpenseService expenseService;
    private final ExpenseFilter filter;
    private final CurrencyService currencyService;
    private List<Expense> currentView;
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private final NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance();

    public ExpenseTable(ExpenseFilterService expenseFilterService, ExpenseService expenseService, CurrencyService currencyService, 
                        CurrencyOption currencyOption, ExpenseFilter filter) {
        this.expenseFilterService = expenseFilterService;
        this.expenseService = expenseService;
        this.filter = filter;
        this.currencyService = currencyService;
        currentView = expenseService.findAllExpenses();

        if (currencyOption != null && currencyOption.getCurrency() != null) {
            currencyFormatter.setCurrency(currencyService.getSelectedCurrency());
        }
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
            case 3 -> currencyFormatter.format(expense.getItemCost());
            case 4 -> expense.getDate() != null
                        ? expense.getDate().format(dateFormatter)
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
        if (currencyService != null && currencyService.getSelectedCurrency() != null) {
            currencyFormatter.setCurrency(currencyService.getSelectedCurrency());
        }
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
        } 
        if (filter.getSortCost() == false) {
            currentView = expenseFilterService.sortByCostAscending(currentView);
        }
        if (filter.getSortDate()) {
            currentView = expenseFilterService.sortByDateDescending(currentView);
        } 
        if (filter.getSortDate() == false) {
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
