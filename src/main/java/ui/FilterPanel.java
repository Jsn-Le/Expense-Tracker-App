package ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import controller.ExpenseController;
import model.ExpenseFilter;

public class FilterPanel extends JPanel {

    public FilterPanel(ExpenseController expenseController, ExpenseFilter expenseFilter, ExpenseTable expenseTable) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        setBackground(Color.WHITE);

        JLabel titleLabel = new JLabel("Filters");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 15));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setOpaque(false);
        formPanel.setBorder(BorderFactory.createEmptyBorder(8, 0, 0, 0));
        formPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel typeFilterLabel = new JLabel("Type");
        typeFilterLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        String[] typeFilters = {"", "Personal", "Business"};
        JComboBox<String> typeFilterBox = new JComboBox<>(typeFilters);
        String[] personal = {"", "Housing", "Food", "Transportation", "Entertainment", "Health", "Other"};
        String[] business = {"", "Payroll & Compensation", "Rent & Utilities", "Advertising & Marketing", "Software & Office Supplies", "Travel & Entertainment", "Other"};

        JLabel categoryFilterLabel = new JLabel("Category");
        categoryFilterLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        JComboBox<String> categoryFilterBox = new JComboBox<>();

        addSectionRow(formPanel, typeFilterLabel, typeFilterBox);
        addSectionRow(formPanel, categoryFilterLabel, categoryFilterBox);

        JPanel buttonPanel = new JPanel(new GridLayout(0, 1, 0, 8));
        buttonPanel.setOpaque(false);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton applyFiltersButton = createStyledButton("Apply Filters");
        JButton clearFiltersButton = createStyledButton("Clear Filters");
        JButton costRangeFilter = createStyledButton("Cost Range");
        JButton dateRangeFilter = createStyledButton("Date Range");
        JButton sortCostButton = createStyledButton("Cost: Descending");
        JButton sortDateButton = createStyledButton("Date: Descending");
        JButton filterStateButton = createStyledButton("Filter State");

        buttonPanel.add(costRangeFilter);
        buttonPanel.add(dateRangeFilter);
        buttonPanel.add(applyFiltersButton);
        buttonPanel.add(clearFiltersButton);
        buttonPanel.add(sortCostButton);
        buttonPanel.add(sortDateButton);
        buttonPanel.add(filterStateButton);

        add(titleLabel);
        add(Box.createVerticalStrut(2));
        add(formPanel);
        add(buttonPanel);

        // Action Listeners
        typeFilterBox.addActionListener(e -> {
            String select = (String) typeFilterBox.getSelectedItem();
            categoryFilterBox.removeAllItems();
            switch (select) {
                case "Personal" -> {
                    for (String i : personal) {
                        categoryFilterBox.addItem(i);
                        expenseFilter.setSelectedFilterType("Personal");
                    }
                }
                case "Business" -> {
                    for (String i : business) {
                        categoryFilterBox.addItem(i);
                        expenseFilter.setSelectedFilterType("Business");
                    }
                }
                case "" -> {
                    categoryFilterBox.removeAllItems();
                    expenseFilter.setSelectedFilterType("");
                }
            }
        });

        // categoryFilterBox
        categoryFilterBox.addActionListener(e -> {
            String category = (String) categoryFilterBox.getSelectedItem();
            expenseFilter.setSelectedFilterCategory(category);
        });

        // costRangeFilter
        costRangeFilter.addActionListener(e -> {
            new CostRangeDialog(expenseFilter);
        });

        // dateRangeFilter
        dateRangeFilter.addActionListener(e -> {
            new DateRangeDialog(expenseFilter);
        });

        // sortCostButton
        sortCostButton.addActionListener(e -> {
            if (expenseFilter.getSortCost()) {
                expenseFilter.setSortCost(false);
                sortCostButton.setText("Cost: Ascending");
                expenseTable.refreshView(); 
            } else if (expenseFilter.getSortCost() == false) {
                expenseFilter.setSortCost(true);
                sortCostButton.setText("Cost: Descending");
                expenseTable.refreshView(); 
            }
        });

        // sortDateButton
        sortDateButton.addActionListener(e -> {
            if (expenseFilter.getSortDate()) {
                expenseFilter.setSortDate(false);
                sortDateButton.setText("Date: Ascending");
                expenseTable.refreshView(); 
            } else if (expenseFilter.getSortDate() == false) {
                expenseFilter.setSortDate(true);
                sortDateButton.setText("Date: Descending");
                expenseTable.refreshView(); 
            }
        });

        // applyFiltersButton
        applyFiltersButton.addActionListener(e -> {
            expenseController.updateTotals();
            expenseTable.refreshView();
        });

        // clearFiltersButton
        clearFiltersButton.addActionListener(e -> {
            sortCostButton.setText("Cost: Descending");
            sortDateButton.setText("Date: Descending");
            typeFilterBox.setSelectedItem("");
            categoryFilterBox.removeAllItems();

            expenseTable.resetFilters();
            expenseController.updateTotals();
        });

        // filterStateButton
        filterStateButton.addActionListener(e -> {
            new FilterStateDialog(expenseFilter);
        });
    }

    private void addSectionRow(JPanel panel, JLabel label, JComboBox<String> comboBox) {
        JPanel row = new JPanel();
        row.setLayout(new BoxLayout(row, BoxLayout.Y_AXIS));
        row.setOpaque(false);
        row.setAlignmentX(Component.CENTER_ALIGNMENT);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        comboBox.setMaximumSize(new Dimension(200, 28));
        comboBox.setPreferredSize(new Dimension(200, 28));
        row.add(label);
        row.add(Box.createVerticalStrut(3));
        row.add(comboBox);
        panel.add(row);
        panel.add(Box.createVerticalStrut(8));
    }

    private JButton createStyledButton(String text) {
        JButton button = ButtonStyles.createSecondaryButton(text);
        button.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setHorizontalAlignment(SwingConstants.CENTER);
        return button;
    }

}
