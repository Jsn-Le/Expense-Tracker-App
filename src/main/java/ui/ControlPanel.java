package ui;

import java.util.List;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;

import controller.ExpenseController;
import model.Expense;

public class ControlPanel extends JPanel {

    public ControlPanel(JTable jTable, ExpenseTable expenseTable, ExpenseController expenseController) {

        // Control Panel Buttons
        JButton createButton = new JButton("Create");
        JButton updateButton = new JButton("Update");
        JButton deleteButton = new JButton("Delete");
        JButton clearButton = new JButton("Clear");
        add(createButton);
        add(updateButton);
        add(deleteButton);
        add(clearButton);

        // Action Listeners
        createButton.addActionListener(e -> {
            new CreateExpenseDialog(expenseController);
        });

        updateButton.addActionListener(e -> {
            new UpdateExpenseDialog(jTable, expenseTable, expenseController);
        });

        deleteButton.addActionListener(e -> {
            int rowIndex = jTable.getSelectedRow();
            if (rowIndex == -1) return;
            Expense row = expenseTable.getExpenseAt(rowIndex);
            int id = row.getId();

            int result = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to delete this logged expense?",
                "Warning",
                JOptionPane.YES_NO_OPTION
            );

            if (result == JOptionPane.YES_OPTION) {
                expenseController.deleteExpense(id);
            } 
        });

        clearButton.addActionListener(e -> {
            int result;
            List<Expense> expenses = expenseTable.getVisibleExpenses();

            if (expenses.isEmpty()) {
                return;
            } else {
                result = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to delete these logged expenses?",
                "Warning",
                JOptionPane.YES_NO_OPTION
                );
            }

            if (result == JOptionPane.YES_OPTION) {
                expenseController.deleteVisibleExpenses(expenses);
            }
        });
    }

}
