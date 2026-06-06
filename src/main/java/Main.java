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

            frame.setVisible(true);

        });
    }

}
