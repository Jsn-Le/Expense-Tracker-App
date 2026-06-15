package app;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;

import controller.ExpenseController;
import model.ExpenseFilter;
import service.ExpenseFilterService;
import service.ExpenseService;
import service.ExpenseTotalService;
import ui.ControlPanel;
import ui.ExpenseTable;
import ui.FilterPanel;
import ui.TotalPanel;

public class ExpenseTrackerApp {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {

            // Services + Model
            ExpenseService expenseService = new ExpenseService();
            ExpenseFilterService expenseFilterService = new ExpenseFilterService();
            ExpenseTotalService expenseTotalService = new ExpenseTotalService();
            ExpenseFilter expenseFilter = new ExpenseFilter("", "", true, true, null, null, null, null);

            // TableModel + JTable
            ExpenseTable expenseTable = new ExpenseTable(expenseFilterService, expenseService, expenseFilter);
            JTable jTable = new JTable(expenseTable);

            // Total Panel + Controller
            TotalPanel totalPanel = new TotalPanel();
            ExpenseController expenseController = new ExpenseController(expenseService, expenseTotalService, expenseTable, totalPanel);
            
            // Control + Filter Panels
            ControlPanel controlPanel = new ControlPanel(jTable, expenseTable, expenseController);
            FilterPanel filterPanel = new FilterPanel(expenseController, expenseFilter, expenseTable);

            // Initial Frame
            JFrame frame = new JFrame("Expense Tracker");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1400, 1000);

            JScrollPane scrollPane = new JScrollPane(jTable);
            JPanel expensePanel = new JPanel(new BorderLayout());
            expensePanel.add(scrollPane);

            JPanel centerPanel = new JPanel(new BorderLayout());
            centerPanel.add(expensePanel, BorderLayout.CENTER);
            centerPanel.add(totalPanel, BorderLayout.SOUTH);

            frame.add(filterPanel, BorderLayout.NORTH);
            frame.add(centerPanel, BorderLayout.CENTER);
            frame.add(controlPanel, BorderLayout.SOUTH);

            expenseTable.refreshView();

            frame.setVisible(true);
        });
    }

}
