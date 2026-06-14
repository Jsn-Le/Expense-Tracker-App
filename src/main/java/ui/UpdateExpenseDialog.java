package ui;

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
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;

import com.toedter.calendar.JCalendar;

import controller.ExpenseController;
import model.Expense;

public class UpdateExpenseDialog extends JDialog {

    public UpdateExpenseDialog(JTable jTable, ExpenseTable expenseTable, ExpenseController expenseController) {
        // Fetch Data Values
        int rowIndex = jTable.getSelectedRow();
        if (rowIndex == -1) return;
        Expense row = expenseTable.getExpenseAt(rowIndex);
        String itemNameValue = row.getItemName();
        String typeValue = row.getType();
        String categoryValue = row.getCategory();
        double costValue = row.getItemCost();
        LocalDate dateValue = row.getDate();

        // Initial JDialog
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setSize(600, 600);

        // Panels
        JPanel updatePanel = new JPanel();
        updatePanel.setLayout(new BoxLayout(updatePanel, BoxLayout.Y_AXIS));
        add(updatePanel);
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
                        this,
                        "All fields must be filled",
                        "Input Error",
                        JOptionPane.ERROR_MESSAGE
                    );
                    return;
                }

                double cost = Double.parseDouble(getCost);

                Expense updatedExpense = new Expense(id, itemName, selectedType, selectedCategory, cost, date);

                dispose();

                expenseController.updateExpense(id, updatedExpense);
            } catch (NumberFormatException z) {
                JOptionPane.showMessageDialog(
                    this,
                    "Cost must be a valid number",
                    "Input Error",
                    JOptionPane.ERROR_MESSAGE
                );
            }
        });

        cancelButton.addActionListener(y -> {
            dispose();
        });

        setVisible(true);
    }

}
