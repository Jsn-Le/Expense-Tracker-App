package ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;

import controller.ExpenseController;
import model.Expense;

public class ControlPanel extends JPanel {

    public ControlPanel(JTable jTable, ExpenseTable expenseTable, ExpenseController expenseController) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        setBackground(Color.WHITE);

        JLabel titleLabel = new JLabel("Actions");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 15));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel buttonPanel = new JPanel(new GridLayout(0, 1, 0, 8));
        buttonPanel.setOpaque(false);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton createButton = createStyledButton("Create Entry");
        JButton updateButton = createStyledButton("Update Entry");
        JButton deleteButton = createStyledButton("Delete Entry");
        JButton clearButton = createStyledButton("Clear Visible");
        buttonPanel.add(createButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(clearButton);

        add(titleLabel);
        add(Box.createVerticalStrut(2));
        add(buttonPanel);

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

    private JButton createStyledButton(String text) {
        JButton button = ButtonStyles.createSecondaryButton(text);
        button.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        return button;
    }

}
