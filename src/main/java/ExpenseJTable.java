import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.swing.table.AbstractTableModel;

public class ExpenseJTable extends AbstractTableModel {

    private final ExpenseManager expenseManager;
    private List<Expense> currentView;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public ExpenseJTable(ExpenseManager expenseManager) {
        this.expenseManager = expenseManager;
        currentView = expenseManager.findAllExpenses();
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
        currentView = expenseManager.findAllExpenses();
        
        if (!Main.selectedFilterType.isBlank()) {
            currentView = expenseManager.filterByType(currentView, Main.selectedFilterType);
        }
        if (!Main.selectedFilterCategory.isBlank()) {
            currentView = expenseManager.filterByCategory(currentView, Main.selectedFilterCategory);
        }
        if (Main.sortCost) {
            currentView = expenseManager.sortByCostDescending(currentView);
        }
        if (Main.sortCost == false) {
            currentView = expenseManager.sortByCostAscending(currentView);
        }
        if (Main.sortDate) {
            currentView = expenseManager.sortByDateDescending(currentView);
        }
        if (Main.sortDate == false) {
            currentView = expenseManager.sortByDateAscending(currentView);
        }
        if (Main.minCost != null && Main.maxCost != null) {
            currentView = expenseManager.filterByCostRange(currentView, Main.minCost, Main.maxCost);
        }
        if (Main.startDate != null && Main.endDate != null) {
            currentView = expenseManager.filterByDateRange(currentView, Main.startDate, Main.endDate);
        }

        return currentView;
    }

    public void refreshView() {
        currentView = expenseManager.findAllExpenses();
        
        if (!Main.selectedFilterType.isBlank()) {
            currentView = expenseManager.filterByType(currentView, Main.selectedFilterType);
        }
        if (!Main.selectedFilterCategory.isBlank()) {
            currentView = expenseManager.filterByCategory(currentView, Main.selectedFilterCategory);
        }
        if (Main.sortCost) {
            currentView = expenseManager.sortByCostDescending(currentView);
        }
        if (Main.sortCost == false) {
            currentView = expenseManager.sortByCostAscending(currentView);
        }
        if (Main.sortDate) {
            currentView = expenseManager.sortByDateDescending(currentView);
        }
        if (Main.sortDate == false) {
            currentView = expenseManager.sortByDateAscending(currentView);
        }
        if (Main.minCost != null && Main.maxCost != null) {
            currentView = expenseManager.filterByCostRange(currentView, Main.minCost, Main.maxCost);
        }
        if (Main.startDate != null && Main.endDate != null) {
            currentView = expenseManager.filterByDateRange(currentView, Main.startDate, Main.endDate);
        }
        fireTableDataChanged();
    }

    public void resetFilters() {
        Main.selectedFilterType = "";
        Main.selectedFilterCategory = "";
        Main.sortCost = true;
        Main.sortDate = true;
        Main.minCost = null;
        Main.maxCost = null;
        Main.startDate = null;
        Main.endDate = null;

        refreshView();
    }

}
