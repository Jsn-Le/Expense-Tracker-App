import java.time.format.DateTimeFormatter;

import javax.swing.table.AbstractTableModel;

public class ExpenseJTable extends AbstractTableModel {

    private final ExpenseManager expenseManager;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public ExpenseJTable(ExpenseManager expenseManager) {
        this.expenseManager = expenseManager;
    }

    @Override
    public int getColumnCount() {
        return 5;
    }

    @Override
    public int getRowCount() {
        return expenseManager.findAllExpenses().size();
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
        Expense expense = expenseManager.findAllExpenses().get(rowIndex);

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
        return expenseManager.findAllExpenses().get(rowIndex);
    }

    public void refresh() {
        fireTableDataChanged();
    }

}
