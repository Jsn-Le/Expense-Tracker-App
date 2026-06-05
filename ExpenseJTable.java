import java.time.format.DateTimeFormatter;
import javax.swing.table.AbstractTableModel;

public class ExpenseJTable extends AbstractTableModel {

    private final ExpenseManager expenseManager;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public ExpenseJTable(ExpenseManager expenseManager) {
        this.expenseManager = expenseManager;
    }

    @Override
    public int getColumnCount() {
        return 4;
    }

    @Override
    public int getRowCount() {
        return expenseManager.findAllExpenses().size();
    }

    @Override
    public String getColumnName(int column) {
        return switch(column) {
            case 0 -> "Item";
            case 1 -> "Category";
            case 2 -> "Cost";
            case 3 -> "Date";
            default -> null;
        };
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Expense expense = expenseManager.findAllExpenses().get(rowIndex);

        return switch (columnIndex) {
            case 0 -> expense.getItemName();
            case 1 -> expense.getCategory();
            case 2 -> expense.getItemCost();
            case 3 -> expense.getDate() != null
                        ? expense.getDate().format(formatter)
                        : "";
            default -> null;
        };
    }

    public Expense getExpenseAt(int rowIndex) {
        return expenseManager.findAllExpenses().get(rowIndex);
    }

}
