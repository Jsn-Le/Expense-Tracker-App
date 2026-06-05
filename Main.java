import java.awt.*;
import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Expense Tracker");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(750,1000);

            JPanel expensesPanel = new JPanel(new BorderLayout());
            JPanel controlPanel = new JPanel();
            JButton createButton = new JButton("Create");
            JButton updateButton = new JButton("Update");
            JButton deleteButton = new JButton("Delete");
            JButton clearButton = new JButton("Clear");
            frame.add(expensesPanel, BorderLayout.CENTER);
            frame.add(controlPanel, BorderLayout.SOUTH);
            controlPanel.add(createButton);
            controlPanel.add(updateButton);
            controlPanel.add(deleteButton);
            controlPanel.add(clearButton);

            ExpenseManager expenseManager = new ExpenseManager();
            ExpenseJTable expenseJTable = new ExpenseJTable(expenseManager);
            
            JTable jTable = new JTable(expenseJTable);
            JScrollPane scrollPane = new JScrollPane(jTable);
            expensesPanel.add(scrollPane);

            frame.setVisible(true);
        });
    }

}
