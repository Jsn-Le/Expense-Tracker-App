package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.ExpenseFilter;

public class FilterStateDialog extends JDialog {

    public FilterStateDialog(ExpenseFilter expenseFilter) {

        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setSize(500, 320);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel rootPanel = new JPanel();
        rootPanel.setLayout(new BoxLayout(rootPanel, BoxLayout.Y_AXIS));
        rootPanel.setBackground(new Color(248, 250, 252));
        rootPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titleLabel = new JLabel("Current filter state");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 15));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitleLabel = new JLabel("Here is the current state of your active filters and sorting.");
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        subtitleLabel.setForeground(new Color(96, 104, 117));
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel filtersPanel = new JPanel(new GridLayout(4, 2, 8, 8));
        filtersPanel.setOpaque(false);
        filtersPanel.setBorder(BorderFactory.createEmptyBorder(12, 0, 10, 0));
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);

        String typeText = expenseFilter.getSelectedFilterType();
        if (typeText == null || typeText.isBlank()) {
            typeText = "None";
        }
        String categoryText = expenseFilter.getSelectedFilterCategory();
        if (categoryText == null || categoryText.isBlank()) {
            categoryText = "None";
        }
        String minCostText = (expenseFilter.getMinCost() == null) ? "None" : expenseFilter.getMinCost().toString();
        String maxCostText = (expenseFilter.getMaxCost() == null) ? "None" : expenseFilter.getMaxCost().toString();
        String startDateText = (expenseFilter.getStartDate() == null) ? "None" : expenseFilter.getStartDate().toString();
        String endDateText = (expenseFilter.getEndDate() == null) ? "None" : expenseFilter.getEndDate().toString();
        String sortCostText = expenseFilter.getSortCost() ? "Cost: Descending" : "Cost: Ascending";
        String sortDateText = expenseFilter.getSortDate() ? "Date: Descending" : "Date: Ascending";

        JLabel typeFilterLabel2 = new JLabel("Type Filter: " + typeText);
        typeFilterLabel2.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        JLabel categoryFilterLabel2 = new JLabel("Category Filter: " + categoryText);
        categoryFilterLabel2.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        JLabel minCostLabel = new JLabel("Min Cost: " + minCostText);
        minCostLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        JLabel maxCostLabel = new JLabel("Max Cost: " + maxCostText);
        maxCostLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        JLabel startDateLabel = new JLabel("Start Date: " + startDateText);
        startDateLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        JLabel endDateLabel = new JLabel("End Date: " + endDateText);
        endDateLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        JLabel sortCostLabel = new JLabel(sortCostText);
        sortCostLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        JLabel sortDateLabel = new JLabel(sortDateText);
        sortDateLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        JButton returnButton = ButtonStyles.createPrimaryButton("Return");
        returnButton.setFont(new Font("Segoe UI", Font.PLAIN, 12));

        filtersPanel.add(typeFilterLabel2);
        filtersPanel.add(categoryFilterLabel2);
        filtersPanel.add(minCostLabel);
        filtersPanel.add(maxCostLabel);
        filtersPanel.add(startDateLabel);
        filtersPanel.add(endDateLabel);
        filtersPanel.add(sortCostLabel);
        filtersPanel.add(sortDateLabel);
        buttonPanel.add(returnButton);

        rootPanel.add(titleLabel);
        rootPanel.add(Box.createVerticalStrut(4));
        rootPanel.add(subtitleLabel);
        rootPanel.add(filtersPanel);
        rootPanel.add(buttonPanel);
        add(rootPanel, BorderLayout.CENTER);

        returnButton.addActionListener(e -> dispose());

        setVisible(true);
    }

}
