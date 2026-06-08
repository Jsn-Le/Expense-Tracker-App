import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import com.toedter.calendar.JCalendar;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {

            // Shared Filter Variables
            String[] selectedFilterType = {""};
            String[] selectedFilterCategory = {""};
            boolean[] sortCost = {true};
            boolean[] sortDate = {true};
            LocalDate[] startDate = new LocalDate[1];
            LocalDate[] endDate = new LocalDate[1];

            // Initial Frame
            JFrame frame = new JFrame("Expense Tracker");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1300, 1000);

            // Expense/Control/Filter Panel
            JPanel expensePanel = new JPanel(new BorderLayout());
            JPanel controlPanel = new JPanel();
            JPanel filterPanel = new JPanel();
            frame.add(expensePanel, BorderLayout.CENTER);
            frame.add(controlPanel, BorderLayout.SOUTH);
            frame.add(filterPanel, BorderLayout.NORTH);

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
            // createButton
            createButton.addActionListener(e -> {
                // Initial JDialog
                JDialog createDialog = new JDialog();
                createDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                createDialog.setSize(600,600);

                // Panels
                JPanel createPanel = new JPanel();
                createPanel.setLayout(new BoxLayout(createPanel, BoxLayout.Y_AXIS));
                createDialog.add(createPanel);
                JPanel itemPanel = new JPanel(new GridLayout(1, 2));
                createPanel.add(itemPanel);
                JPanel typePanel = new JPanel(new GridLayout(1, 2));
                createPanel.add(typePanel);
                JPanel categoryPanel = new JPanel(new GridLayout(1, 2));
                createPanel.add(categoryPanel);
                JPanel costPanel = new JPanel(new GridLayout(1, 2));
                createPanel.add(costPanel);
                JPanel datePanel = new JPanel(new GridLayout(1, 2));
                createPanel.add(datePanel);
                JPanel date2Panel = new JPanel(new GridLayout(1, 2));
                createPanel.add(date2Panel);
                JPanel buttonPanel = new JPanel(new GridLayout(1, 2));
                createPanel.add(buttonPanel);

                // Create Panel Labels
                itemPanel.add(new JLabel("Item: "));
                JTextField itemField = new JTextField();
                itemPanel.add(itemField);
                typePanel.add(new JLabel("Type: "));
                categoryPanel.add(new JLabel("Category: "));

                    // Category ComboBox
                String[] type = {"Personal", "Business"};
                JComboBox<String> typeBox = new JComboBox<>(type);
                typePanel.add(typeBox);

                JComboBox<String> categoryBox = new JComboBox<>();
                String[] personal = {"Housing", "Food", "Transportation", "Entertainment", "Health", "Other"};
                String[] business = {"Payroll & Compensation", "Rent & Utilities", "Advertising & Marketing", "Software & Office Supplies", "Travel & Entertainment", "Other"};

                typeBox.addActionListener(t -> {
                    String select = (String) typeBox.getSelectedItem();
                    categoryBox.removeAllItems();
                    if ("Personal".equals(select)) {
                        for (String i : personal) {
                            categoryBox.addItem(i);
                        }
                    } else if ("Business".equals(select)) {
                        for (String i : business) {
                            categoryBox.addItem(i);
                        }
                    }
                });
                categoryPanel.add(categoryBox);

                // Create Panel Labels Continued
                costPanel.add(new JLabel("Cost: "));
                JTextField costField = new JTextField();
                costPanel.add(costField);
                datePanel.add(new JLabel("Date: "));

                LocalDate[] sharedDate = new LocalDate[1];

                    // Selected Date Label
                JLabel selectedDateLabel = new JLabel("Selected Date:");
                date2Panel.add(selectedDateLabel);
                JLabel selectedDate = new JLabel("None");
                date2Panel.add(selectedDate);

                    // Date Panel Button + Function for Calendar
                JButton selectDateButton = new JButton("Select Date");
                datePanel.add(selectDateButton);

                selectDateButton.addActionListener(d -> {
                    JDialog calendarDialog = new JDialog();
                    calendarDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                    calendarDialog.setSize(450,450);

                    JPanel calendarPanel = new JPanel(new BorderLayout());
                    JPanel button2Panel = new JPanel();
                    calendarDialog.add(calendarPanel, BorderLayout.CENTER);
                    calendarDialog.add(button2Panel, BorderLayout.SOUTH);

                    JCalendar calendar = new JCalendar();
                    calendarPanel.add(calendar);

                    JButton confirmButton = new JButton("Confirm");
                    button2Panel.add(confirmButton);
                    JButton cancelButton = new JButton("Cancel");
                    button2Panel.add(cancelButton);

                    confirmButton.addActionListener(x -> {
                        Date date = calendar.getDate();
                        Instant instantDate = date.toInstant();
                        ZonedDateTime zonedDate = instantDate.atZone(ZoneId.systemDefault());
                        LocalDate convertedDate = zonedDate.toLocalDate();

                        if (convertedDate.isAfter(LocalDate.now())) {
                            JOptionPane.showMessageDialog(
                                calendarDialog,
                                "Selected date cannot be past the current date",
                                "Input Error",
                                JOptionPane.ERROR_MESSAGE
                            ); 
                            return;
                        }

                        sharedDate[0] = convertedDate;
                        selectedDate.setText(String.valueOf(sharedDate[0]));
                        calendarDialog.dispose();
                    });

                    cancelButton.addActionListener(y -> {
                        calendarDialog.dispose();
                    });

                    calendarDialog.setVisible(true);
                });

                // Create Panel Buttons
                JButton confirmButton = new JButton("Confirm");
                buttonPanel.add(confirmButton);
                JButton cancelButton = new JButton("Cancel");
                buttonPanel.add(cancelButton);

                // Create Panel Button Functions
                confirmButton.addActionListener(x -> {
                    try {
                        String itemName = itemField.getText();
                        String selectedType = (String) typeBox.getSelectedItem();
                        String selectedCategory = (String) categoryBox.getSelectedItem();
                        String getCost = costField.getText();
                        LocalDate date = sharedDate[0];

                        if (itemName.isBlank() || selectedCategory == null || getCost.isBlank() || date == null) {
                            JOptionPane.showMessageDialog(
                                createDialog,
                                "All fields must be filled",
                                "Input Error",
                                JOptionPane.ERROR_MESSAGE
                            );
                            return;
                        }

                        double cost = Double.parseDouble(getCost);

                        expenseManager.addExpense(itemName,selectedType, selectedCategory, cost, date);
                        createDialog.dispose();
                        expenseJTable.refresh();
                    } catch (NumberFormatException z) {
                        JOptionPane.showMessageDialog(
                            createDialog,
                            "Cost must be a valid number",
                            "Input Error",
                            JOptionPane.ERROR_MESSAGE
                        );
                    }
                });

                cancelButton.addActionListener(y -> {
                    createDialog.dispose();
                });

                createDialog.setVisible(true);
            });

            // updateButton
            updateButton.addActionListener(e -> {
                // Fetch Data Values
                int rowIndex = jTable.getSelectedRow();
                if (rowIndex == -1) return;
                Expense row = expenseJTable.getExpenseAt(jTable.getSelectedRow());
                String itemNameValue = row.getItemName();
                String typeValue = row.getType();
                String categoryValue = row.getCategory();
                double costValue = row.getItemCost();
                LocalDate dateValue = row.getDate();

                // Initial JDialog
                JDialog updateDialog = new JDialog();
                updateDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                updateDialog.setSize(600, 600);

                // Panels
                JPanel updatePanel = new JPanel();
                updatePanel.setLayout(new BoxLayout(updatePanel, BoxLayout.Y_AXIS));
                updateDialog.add(updatePanel);
                JPanel itemPanel = new JPanel(new GridLayout(1, 2));
                updatePanel.add(itemPanel);
                JPanel typePanel = new JPanel(new GridLayout(1, 2));
                updatePanel.add(typePanel);
                JPanel categoryPanel = new JPanel(new GridLayout(1, 2));
                updatePanel.add(categoryPanel);
                JPanel costPanel = new JPanel(new GridLayout(1, 2));
                updatePanel.add(costPanel);
                JPanel datePanel = new JPanel(new GridLayout(1, 2));
                updatePanel.add(datePanel);
                JPanel date2Panel = new JPanel(new GridLayout(1, 2));
                updatePanel.add(date2Panel);
                JPanel buttonPanel = new JPanel(new GridLayout(1, 2));
                updatePanel.add(buttonPanel);

                // Create Panel Labels
                itemPanel.add(new JLabel("Item: "));
                JTextField itemField = new JTextField();
                itemPanel.add(itemField);

                // Load itemNameValue to itemField
                itemField.setText(itemNameValue);

                // Create Panel Labels Continued
                typePanel.add(new JLabel("Type: "));
                categoryPanel.add(new JLabel("Category: "));

                    // Category ComboBox
                String[] type = {"Personal", "Business"};
                JComboBox<String> typeBox = new JComboBox<>(type);
                typePanel.add(typeBox);

                JComboBox<String> categoryBox = new JComboBox<>();
                String[] personal = {"Housing", "Food", "Transportation", "Entertainment", "Health", "Other"};
                String[] business = {"Payroll & Compensation", "Rent & Utilities", "Advertising & Marketing", "Software & Office Supplies", "Travel & Entertainment", "Other"};

                        // Prefill category list
                categoryBox.removeAllItems();
                if ("Personal".equals(typeValue)) {
                    for (String i : personal) {
                        categoryBox.addItem(i);
                    }
                } else if ("Business".equals(typeValue)) {
                    for (String i : business) {
                        categoryBox.addItem(i);
                    }
                }

                categoryPanel.add(categoryBox);

                        // Load typeValue to typeBox & categoryValue to categoryBox
                typeBox.setSelectedItem(typeValue);
                categoryBox.setSelectedItem(categoryValue);

                        // categoryBox Action Listener
                typeBox.addActionListener(t -> {
                    String select = (String) typeBox.getSelectedItem();
                    categoryBox.removeAllItems();
                    if ("Personal".equals(select)) {
                        for (String i : personal) {
                            categoryBox.addItem(i);
                        }
                    } else if ("Business".equals(select)) {
                        for (String i : business) {
                            categoryBox.addItem(i);
                        }
                    }
                });

                // Create Panel Labels Continued
                costPanel.add(new JLabel("Cost: "));
                JTextField costField = new JTextField();
                costPanel.add(costField);

                // Load costValue to costField
                costField.setText(String.valueOf(costValue));

                // Create Panel Labels Continued
                datePanel.add(new JLabel("Date: "));

                LocalDate[] sharedDate = new LocalDate[1];

                    // Selected Date Label
                JLabel selectedDateLabel = new JLabel("Selected Date:");
                date2Panel.add(selectedDateLabel);
                JLabel selectedDate = new JLabel();
                date2Panel.add(selectedDate);

                    // Load dateValue to selectedDate and sharedDate
                selectedDate.setText(String.valueOf(dateValue));
                sharedDate[0] = dateValue;

                    // Date Panel Button + Function for Calendar
                JButton selectDateButton = new JButton("Select Date");
                datePanel.add(selectDateButton);

                selectDateButton.addActionListener(d -> {
                    JDialog calendarDialog = new JDialog();
                    calendarDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                    calendarDialog.setSize(450,450);

                    JPanel calendarPanel = new JPanel(new BorderLayout());
                    JPanel button2Panel = new JPanel();
                    calendarDialog.add(calendarPanel, BorderLayout.CENTER);
                    calendarDialog.add(button2Panel, BorderLayout.SOUTH);

                    JCalendar calendar = new JCalendar();
                    calendarPanel.add(calendar);

                    JButton confirmButton = new JButton("Confirm");
                    button2Panel.add(confirmButton);
                    JButton cancelButton = new JButton("Cancel");
                    button2Panel.add(cancelButton);

                    confirmButton.addActionListener(x -> {
                        Date date = calendar.getDate();
                        Instant instantDate = date.toInstant();
                        ZonedDateTime zonedDate = instantDate.atZone(ZoneId.systemDefault());
                        LocalDate convertedDate = zonedDate.toLocalDate();

                        if (convertedDate.isAfter(LocalDate.now())) {
                            JOptionPane.showMessageDialog(
                                calendarDialog,
                                "Selected date cannot be past the current date",
                                "Input Error",
                                JOptionPane.ERROR_MESSAGE
                            ); 
                            return;
                        }

                        sharedDate[0] = convertedDate;
                        selectedDate.setText("" + sharedDate[0]);
                        calendarDialog.dispose();
                    });

                    cancelButton.addActionListener(y -> {
                        calendarDialog.dispose();
                    });

                    calendarDialog.setVisible(true);
                });

                // Create Panel Buttons
                JButton confirmButton = new JButton("Confirm");
                buttonPanel.add(confirmButton);
                JButton cancelButton = new JButton("Cancel");
                buttonPanel.add(cancelButton);

                // Create Panel Button Functions
                confirmButton.addActionListener(x -> {
                    try {
                        int id = row.getId();
                        String itemName = itemField.getText();
                        String selectedType = (String) typeBox.getSelectedItem();
                        String selectedCategory = (String) categoryBox.getSelectedItem();
                        String getCost = costField.getText();
                        LocalDate date = sharedDate[0];

                        if (itemName.isBlank() || selectedCategory == null || getCost.isBlank() || date == null) {
                            JOptionPane.showMessageDialog(
                                updateDialog,
                                "All fields must be filled",
                                "Input Error",
                                JOptionPane.ERROR_MESSAGE
                            );
                            return;
                        }

                        double cost = Double.parseDouble(getCost);

                        Expense updatedExpense = new Expense(id, itemName, selectedType, selectedCategory, cost, date);

                        expenseManager.updateExpense(id, updatedExpense);
                        updateDialog.dispose();
                        expenseJTable.refresh();
                    } catch (NumberFormatException z) {
                        JOptionPane.showMessageDialog(
                            updateDialog,
                            "Cost must be a valid number",
                            "Input Error",
                            JOptionPane.ERROR_MESSAGE
                        );
                    }
                });

                cancelButton.addActionListener(y -> {
                    updateDialog.dispose();
                });

                updateDialog.setVisible(true);
            });

            // deleteButton
            deleteButton.addActionListener(e -> {
                Expense row = expenseJTable.getExpenseAt(jTable.getSelectedRow());
                int id = row.getId();

                int result = JOptionPane.showConfirmDialog(
                    frame,
                    "Are you sure you want to delete this logged expense?",
                    "Warning",
                    JOptionPane.YES_NO_OPTION
                );

                if (result == JOptionPane.YES_OPTION) {
                    expenseManager.deleteExpense(id);
                    expenseJTable.refresh();
                } 
            });

            // clearButton
            clearButton.addActionListener(e -> {

            });

            // Filter Panel Buttons/ComboBox
            JLabel filterLabel = new JLabel("Filters: ");
            JLabel typeFilterLabel = new JLabel("Type Filter: ");
            String[] typeFilters = {"", "Personal", "Business"};
            JComboBox<String> typeFilterBox = new JComboBox<>(typeFilters);
            String[] personal = {"", "Housing", "Food", "Transportation", "Entertainment", "Health", "Other"};
            String[] business = {"", "Payroll & Compensation", "Rent & Utilities", "Advertising & Marketing", "Software & Office Supplies", "Travel & Entertainment", "Other"};
            JLabel categoryFilterLabel = new JLabel("Category Filter: ");
            JComboBox<String> categoryFilterBox = new JComboBox<>();
            JButton applyFiltersButton = new JButton("Apply Filters");
            JButton clearFiltersButton = new JButton("Clear Filters");
            JButton dateRangeFilter = new JButton("Date Range Filter");
            JButton sortCostButton = new JButton("Cost: Descending");
            JButton sortDateButton = new JButton("Date: Descending");
            JButton filterStateButton = new JButton("Filter State");
            filterPanel.add(filterLabel);
            filterPanel.add(typeFilterLabel);
            filterPanel.add(typeFilterBox);
            filterPanel.add(categoryFilterLabel);
            filterPanel.add(categoryFilterBox);
            filterPanel.add(dateRangeFilter);
            filterPanel.add(applyFiltersButton);
            filterPanel.add(clearFiltersButton);
            filterPanel.add(sortCostButton);
            filterPanel.add(sortDateButton);
            filterPanel.add(filterStateButton);

            // typeFilterBox
            typeFilterBox.addActionListener(t -> {
                String select = (String) typeFilterBox.getSelectedItem();
                categoryFilterBox.removeAllItems();
                switch (select) {
                    case "Personal" -> {
                        for (String i : personal) {
                            categoryFilterBox.addItem(i);
                            selectedFilterType[0] = "Personal";
                        }
                    }
                    case "Business" -> {
                        for (String i : business) {
                            categoryFilterBox.addItem(i);
                            selectedFilterType[0] = "Business";
                        }
                    }
                    case "" -> {
                        categoryFilterBox.removeAllItems();
                        selectedFilterType[0] = "";
                    }
                }
            });

            // categoryFilterBox
            categoryFilterBox.addActionListener(t -> {
                String category = (String) categoryFilterBox.getSelectedItem();
                selectedFilterCategory[0] = category;
            });

            // dateRangeFilter
            dateRangeFilter.addActionListener(e -> {
                //Initial JDialog
                JDialog dateRangeDialog = new JDialog();
                dateRangeDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                dateRangeDialog.setSize(400, 300);

                // Panels
                JPanel datePanel = new JPanel(new GridLayout(2, 3));
                JPanel buttonPanel = new JPanel();
                dateRangeDialog.add(datePanel, BorderLayout.CENTER);
                dateRangeDialog.add(buttonPanel, BorderLayout.SOUTH);

                // Labels/Buttons
                JLabel startDateLabel = new JLabel("Start Date: ");
                JLabel startDateLabel2 = new JLabel();
                JButton startDateButton = new JButton("Select Date");
                JLabel endDateLabel = new JLabel("End Date: ");
                JLabel endDateLabel2 = new JLabel();
                JButton endDateButton = new JButton("Select Date");
                JButton confirmButton = new JButton("Confirm");
                JButton cancelButton = new JButton("Cancel");
                datePanel.add(startDateLabel);
                datePanel.add(startDateLabel2);
                datePanel.add(startDateButton);
                datePanel.add(endDateLabel);
                datePanel.add(endDateLabel2);
                datePanel.add(endDateButton);
                buttonPanel.add(confirmButton);
                buttonPanel.add(cancelButton);

                // startDateButton
                startDateButton.addActionListener(b -> {
                    JDialog calendarDialog = new JDialog();
                    calendarDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                    calendarDialog.setSize(450,450);

                    JPanel calendarPanel = new JPanel(new BorderLayout());
                    JPanel button2Panel = new JPanel();
                    calendarDialog.add(calendarPanel, BorderLayout.CENTER);
                    calendarDialog.add(button2Panel, BorderLayout.SOUTH);

                    JCalendar calendar = new JCalendar();
                    calendarPanel.add(calendar);

                    JButton confirmButton2 = new JButton("Confirm");
                    button2Panel.add(confirmButton2);
                    JButton cancelButton2 = new JButton("Cancel");
                    button2Panel.add(cancelButton2);

                    confirmButton2.addActionListener(x -> {
                        Date date = calendar.getDate();
                        Instant instantDate = date.toInstant();
                        ZonedDateTime zonedDate = instantDate.atZone(ZoneId.systemDefault());
                        LocalDate convertedDate = zonedDate.toLocalDate();

                        if (convertedDate.isAfter(LocalDate.now())) {
                            JOptionPane.showMessageDialog(
                                calendarDialog,
                                "Selected date cannot be past the current date",
                                "Input Error",
                                JOptionPane.ERROR_MESSAGE
                            ); 
                            return;
                        }
                        if (endDate[0] != null) {
                            if (convertedDate.isAfter(endDate[0])) {
                            JOptionPane.showMessageDialog(
                                calendarDialog,
                                "Selected date cannot be after end date",
                                "Input Error",
                                JOptionPane.ERROR_MESSAGE
                            );
                            return;
                            }
                        }

                        startDate[0] = convertedDate;
                        startDateLabel2.setText(String.valueOf(startDate[0]));
                        calendarDialog.dispose();
                    });

                    cancelButton2.addActionListener(y -> {
                        calendarDialog.dispose();
                    });

                    calendarDialog.setVisible(true);
                });

                // endDateButton
                endDateButton.addActionListener(b -> {
                    JDialog calendarDialog = new JDialog();
                    calendarDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                    calendarDialog.setSize(450,450);

                    JPanel calendarPanel = new JPanel(new BorderLayout());
                    JPanel button2Panel = new JPanel();
                    calendarDialog.add(calendarPanel, BorderLayout.CENTER);
                    calendarDialog.add(button2Panel, BorderLayout.SOUTH);

                    JCalendar calendar = new JCalendar();
                    calendarPanel.add(calendar);

                    JButton confirmButton2 = new JButton("Confirm");
                    button2Panel.add(confirmButton2);
                    JButton cancelButton2 = new JButton("Cancel");
                    button2Panel.add(cancelButton2);

                    confirmButton2.addActionListener(x -> {
                        Date date = calendar.getDate();
                        Instant instantDate = date.toInstant();
                        ZonedDateTime zonedDate = instantDate.atZone(ZoneId.systemDefault());
                        LocalDate convertedDate = zonedDate.toLocalDate();

                        if (convertedDate.isAfter(LocalDate.now())) {
                            JOptionPane.showMessageDialog(
                                calendarDialog,
                                "Selected date cannot be past the current date",
                                "Input Error",
                                JOptionPane.ERROR_MESSAGE
                            ); 
                            return;
                        }
                        if (startDate[0] != null) {
                            if (convertedDate.isBefore(startDate[0])) {
                            JOptionPane.showMessageDialog(
                                calendarDialog,
                                "Selected date cannot be before start date",
                                "Input Error",
                                JOptionPane.ERROR_MESSAGE
                            );
                            return;
                            }
                        }

                        endDate[0] = convertedDate;
                        endDateLabel2.setText(String.valueOf(endDate[0]));
                        calendarDialog.dispose();
                    });

                    cancelButton2.addActionListener(y -> {
                        calendarDialog.dispose();
                    });

                    calendarDialog.setVisible(true);
                });

                // confirmButton
                confirmButton.addActionListener(a -> {
                    dateRangeDialog.dispose();
                });

                // cancelButton
                cancelButton.addActionListener(a -> {
                    dateRangeDialog.dispose();
                });

                dateRangeDialog.setVisible(true);
            });

            // sortCostButton
            sortCostButton.addActionListener(e -> {
                if (sortCost[0]) {
                    sortCost[0] = false;
                    sortCostButton.setText("Cost: Ascending");
                } else {
                    sortCost[0] = true;
                    sortCostButton.setText("Cost: Descending");
                }
            });

            // sortDateButton
            sortDateButton.addActionListener(e -> {
                if (sortDate[0]) {
                    sortDate[0] = false;
                    sortDateButton.setText("Date: Ascending");
                } else {
                    sortDate[0] = true;
                    sortDateButton.setText("Date: Descending");
                }
            });

            // applyFiltersButton
            applyFiltersButton.addActionListener(e -> {

            });

            // clearFiltersButton
            clearFiltersButton.addActionListener(e -> {
                
            });

            // filterStateButton
            filterStateButton.addActionListener(e -> {
                // Initial JDialog
                JDialog filterStateDialog = new JDialog();
                filterStateDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                filterStateDialog.setSize(400, 300);

                // Panels
                JPanel filtersPanel = new JPanel(new GridLayout(3, 2));
                JPanel buttonPanel = new JPanel();
                filterStateDialog.add(filtersPanel, BorderLayout.CENTER);
                filterStateDialog.add(buttonPanel, BorderLayout.SOUTH);

                // Labels/Buttons + Variables
                String typeText = selectedFilterType[0];
                if (typeText == null || typeText.isBlank()) {
                    typeText = "None";
                }
                String categoryText = selectedFilterCategory[0];
                if (categoryText == null || categoryText.isBlank()) {
                    categoryText = "None";
                }
                String startDateText = (startDate[0] == null) ? "None" : startDate[0].toString();
                String endDateText = (endDate[0] == null) ? "None" : endDate[0].toString();
                String sortCostText;
                if (sortCost[0]) {
                    sortCostText = "Cost: Descending";
                } else {
                    sortCostText = "Cost: Ascending";
                }
                String sortDateText;
                if (sortDate[0]) {
                    sortDateText = "Date: Descending";
                } else {
                    sortDateText = "Date: Ascending";
                }

                JLabel typeFilterLabel2 = new JLabel("Type Filter: " + typeText);
                JLabel categoryFilterLabel2 = new JLabel("Category Filter: " + categoryText);
                JLabel startDateLabel = new JLabel("Start Date: " + startDateText);
                JLabel endDateLabel = new JLabel("End Date: " + endDateText);
                JLabel sortCostLabel = new JLabel(sortCostText);
                JLabel sortDateLabel = new JLabel(sortDateText);
                JButton returnButton = new JButton("Return");
                filtersPanel.add(typeFilterLabel2);
                filtersPanel.add(categoryFilterLabel2);
                filtersPanel.add(startDateLabel);
                filtersPanel.add(endDateLabel);
                filtersPanel.add(sortCostLabel);
                filtersPanel.add(sortDateLabel);
                buttonPanel.add(returnButton);

                returnButton.addActionListener(c -> {
                    filterStateDialog.dispose();
                });

                filterStateDialog.setVisible(true);
            });

            frame.setVisible(true);
        });
    }

}
