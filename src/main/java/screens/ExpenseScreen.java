package screens;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
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
            frame.setSize(1500, 900);
            frame.setLocationRelativeTo(null);

            // Menu Bar 
            MenuBar menuBar = new MenuBar(frame, expenseController);
            frame.setJMenuBar(menuBar);

            JPanel rootPanel = new JPanel(new BorderLayout(14, 14));
            rootPanel.setBackground(new Color(248, 250, 252));
            rootPanel.setBorder(BorderFactory.createEmptyBorder(18, 18, 18, 18));

            JPanel headerPanel = new JPanel();
            headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
            headerPanel.setOpaque(false);
            JLabel titleLabel = new JLabel("Expense Overview");
            titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
            titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
            JLabel subtitleLabel = new JLabel("Review, filter, and manage your expenses from one place.");
            subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
            subtitleLabel.setForeground(new Color(96, 104, 117));
            subtitleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
            headerPanel.add(titleLabel);
            headerPanel.add(Box.createVerticalStrut(4));
            headerPanel.add(subtitleLabel);

            JScrollPane scrollPane = new JScrollPane(jTable);
            scrollPane.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(226, 232, 240), 1),
                    BorderFactory.createEmptyBorder(6, 6, 6, 6)));
            scrollPane.getViewport().setBackground(Color.WHITE);

            JPanel expensePanel = new JPanel(new BorderLayout());
            expensePanel.setBackground(Color.WHITE);
            expensePanel.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(226, 232, 240), 1),
                    BorderFactory.createEmptyBorder(10, 10, 10, 10)));
            expensePanel.add(scrollPane, BorderLayout.CENTER);

            JPanel centerPanel = new JPanel(new BorderLayout(10, 10));
            centerPanel.setOpaque(false);
            centerPanel.add(expensePanel, BorderLayout.CENTER);
            centerPanel.add(totalPanel, BorderLayout.SOUTH);

            filterPanel.setPreferredSize(new Dimension(260, 0));
            filterPanel.setBackground(Color.WHITE);
            filterPanel.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(226, 232, 240), 1),
                    BorderFactory.createEmptyBorder(12, 12, 12, 12)));
            filterPanel.setLayout(new BoxLayout(filterPanel, BoxLayout.Y_AXIS));

            controlPanel.setPreferredSize(new Dimension(240, 0));
            controlPanel.setBackground(Color.WHITE);
            controlPanel.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(226, 232, 240), 1),
                    BorderFactory.createEmptyBorder(12, 12, 12, 12)));
            controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.Y_AXIS));

            rootPanel.add(headerPanel, BorderLayout.NORTH);
            rootPanel.add(filterPanel, BorderLayout.WEST);
            rootPanel.add(centerPanel, BorderLayout.CENTER);
            rootPanel.add(controlPanel, BorderLayout.EAST);
            frame.setContentPane(rootPanel);

            expenseTable.refreshView();

            frame.setVisible(true);
        });
    }

}
