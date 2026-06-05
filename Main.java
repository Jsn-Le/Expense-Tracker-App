import java.awt.*;
import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {

            // Initial Frame
            JFrame frame = new JFrame("Expense Tracker");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(750, 1000);

            // Expense/Control Panel
            JPanel expensePanel = new JPanel(new BorderLayout());
            JPanel controlPanel = new JPanel();
            frame.add(expensePanel, BorderLayout.CENTER);
            frame.add(controlPanel, BorderLayout.SOUTH);

                // Control Panel Buttons
                JButton createButton = new JButton("Create");
                JButton updateButton = new JButton("Update");
                JButton deleteButton = new JButton("Delete");
                JButton clearButton = new JButton("Clear");
                controlPanel.add(createButton);
                controlPanel.add(updateButton);
                controlPanel.add(deleteButton);
                controlPanel.add(clearButton);

            // JTable
            ExpenseManager expenseManager = new ExpenseManager();
            ExpenseJTable expenseJTable = new ExpenseJTable(expenseManager);

            JTable jTable = new JTable(expenseJTable);
            JScrollPane scrollPane = new JScrollPane(jTable);
            expensePanel.add(scrollPane);

            // Control Panel Button Functions

            // Visibility
            frame.setVisible(true);

        });
    }

}
