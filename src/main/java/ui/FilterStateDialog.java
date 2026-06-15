package ui;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.ExpenseFilter;

public class FilterStateDialog extends JDialog {

    public FilterStateDialog(ExpenseFilter expenseFilter) {

        // Initial JDialog
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setSize(500, 300);

        // Panels
        JPanel filtersPanel = new JPanel(new GridLayout(4, 2));
        JPanel buttonPanel = new JPanel();
        add(filtersPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Labels/Buttons + Variables
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
        String sortCostText;
        if (expenseFilter.getSortCost()) {
            sortCostText = "Cost: Descending";
        } else {
            sortCostText = "Cost: Ascending";
        }
        String sortDateText;
        if (expenseFilter.getSortDate()) {
            sortDateText = "Date: Descending";
        } else {
            sortDateText = "Date: Ascending";
        }

        JLabel typeFilterLabel2 = new JLabel("Type Filter: " + typeText);
        JLabel categoryFilterLabel2 = new JLabel("Category Filter: " + categoryText);
        JLabel minCostLabel = new JLabel("Min Cost: " + minCostText);
        JLabel maxCostLabel = new JLabel("Max Cost: " + maxCostText);
        JLabel startDateLabel = new JLabel("Start Date: " + startDateText);
        JLabel endDateLabel = new JLabel("End Date: " + endDateText);
        JLabel sortCostLabel = new JLabel(sortCostText);
        JLabel sortDateLabel = new JLabel(sortDateText);
        JButton returnButton = new JButton("Return");
        filtersPanel.add(typeFilterLabel2);
        filtersPanel.add(categoryFilterLabel2);
        filtersPanel.add(minCostLabel);
        filtersPanel.add(maxCostLabel);
        filtersPanel.add(startDateLabel);
        filtersPanel.add(endDateLabel);
        filtersPanel.add(sortCostLabel);
        filtersPanel.add(sortDateLabel);
        buttonPanel.add(returnButton);

        returnButton.addActionListener(e -> {
            dispose();
        });

        setVisible(true);
    }

}
