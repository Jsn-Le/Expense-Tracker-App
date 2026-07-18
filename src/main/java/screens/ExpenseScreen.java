package screens;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;

import controller.ExpenseController;
import model.ExpenseFilter;
import ui.ControlPanel;
import ui.ExpenseTable;
import ui.FilterPanel;
import ui.MenuBar;
import ui.TotalPanel;

public class ExpenseScreen {

    public static void open(ExpenseController expenseController, ExpenseTable expenseTable, TotalPanel totalPanel, ExpenseFilter expenseFilter) {
        SwingUtilities.invokeLater(() -> {

            // TableModel + JTable
            JTable jTable = new JTable(expenseTable);
            
            // Control + Filter Panels
            ControlPanel controlPanel = new ControlPanel(jTable, expenseTable, expenseController);
            FilterPanel filterPanel = new FilterPanel(expenseController, expenseFilter, expenseTable);

            // Initial Frame
            JFrame frame = new JFrame("Expense Tracker");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1400, 1000);

            // Menu Bar 
            MenuBar menuBar = new MenuBar(frame, expenseController);
            frame.setJMenuBar(menuBar);

            // Table
            JScrollPane scrollPane = new JScrollPane(jTable);
            JPanel expensePanel = new JPanel(new BorderLayout());
            expensePanel.add(scrollPane);

            // Panels
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
