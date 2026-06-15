package ui;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import controller.ExpenseController;
import model.ExpenseFilter;

public class FilterPanel extends JPanel {

    public FilterPanel(ExpenseController expenseController, ExpenseFilter expenseFilter, ExpenseTable expenseTable) {

        // Filter Panel Buttons/ComboBox
        JLabel typeFilterLabel = new JLabel("Type Filter: ");
        String[] typeFilters = {"", "Personal", "Business"};
        JComboBox<String> typeFilterBox = new JComboBox<>(typeFilters);
        String[] personal = {"", "Housing", "Food", "Transportation", "Entertainment", "Health", "Other"};
        String[] business = {"", "Payroll & Compensation", "Rent & Utilities", "Advertising & Marketing", "Software & Office Supplies", "Travel & Entertainment", "Other"};
        JLabel categoryFilterLabel = new JLabel("Category Filter: ");
        JComboBox<String> categoryFilterBox = new JComboBox<>();
        JButton applyFiltersButton = new JButton("Apply Filters");
        JButton clearFiltersButton = new JButton("Clear Filters");
        JButton costRangeFilter = new JButton("Cost Range Filter");
        JButton dateRangeFilter = new JButton("Date Range Filter");
        JButton sortCostButton = new JButton("Cost: Descending");
        JButton sortDateButton = new JButton("Date: Descending");
        JButton filterStateButton = new JButton("Filter State");
        add(typeFilterLabel);
        add(typeFilterBox);
        add(categoryFilterLabel);
        add(categoryFilterBox);
        add(costRangeFilter);
        add(dateRangeFilter);
        add(applyFiltersButton);
        add(clearFiltersButton);
        add(sortCostButton);
        add(sortDateButton);
        add(filterStateButton);

        // Action Listeners
        // typeFilterBox
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

}
