package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.Box;
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

        int rowIndex = jTable.getSelectedRow();
        if (rowIndex == -1) return;
        Expense row = expenseTable.getExpenseAt(rowIndex);
        String itemNameValue = row.getItemName();
        String typeValue = row.getType();
        String categoryValue = row.getCategory();
        double costValue = row.getItemCost();
        LocalDate dateValue = row.getDate();

        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setSize(600, 620);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel rootPanel = new JPanel(new BorderLayout());
        rootPanel.setBackground(new Color(248, 250, 252));
        rootPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.setOpaque(false);
        JLabel titleLabel = new JLabel("Update expense");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 15));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel subtitleLabel = new JLabel("Edit the selected expense entry below.");
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        subtitleLabel.setForeground(new Color(96, 104, 117));
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        headerPanel.add(titleLabel);
        headerPanel.add(Box.createVerticalStrut(4));
        headerPanel.add(subtitleLabel);

        JPanel updatePanel = new JPanel();
        updatePanel.setLayout(new BoxLayout(updatePanel, BoxLayout.Y_AXIS));
        updatePanel.setOpaque(false);
        updatePanel.setBorder(BorderFactory.createEmptyBorder(12, 0, 0, 0));

        JPanel itemPanel = new JPanel(new GridLayout(1, 2, 8, 0));
        itemPanel.setOpaque(false);
        JPanel typePanel = new JPanel(new GridLayout(1, 2, 8, 0));
        typePanel.setOpaque(false);
        JPanel categoryPanel = new JPanel(new GridLayout(1, 2, 8, 0));
        categoryPanel.setOpaque(false);
        JPanel costPanel = new JPanel(new GridLayout(1, 2, 8, 0));
        costPanel.setOpaque(false);
        JPanel datePanel = new JPanel(new GridLayout(1, 2, 8, 0));
        datePanel.setOpaque(false);
        JPanel date2Panel = new JPanel(new GridLayout(1, 2, 8, 0));
        date2Panel.setOpaque(false);
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 8, 0));
        buttonPanel.setOpaque(false);

        updatePanel.add(itemPanel);
        updatePanel.add(Box.createVerticalStrut(8));
        updatePanel.add(typePanel);
        updatePanel.add(Box.createVerticalStrut(8));
        updatePanel.add(categoryPanel);
        updatePanel.add(Box.createVerticalStrut(8));
        updatePanel.add(costPanel);
        updatePanel.add(Box.createVerticalStrut(8));
        updatePanel.add(datePanel);
        updatePanel.add(Box.createVerticalStrut(8));
        updatePanel.add(date2Panel);
        updatePanel.add(Box.createVerticalStrut(14));
        updatePanel.add(buttonPanel);

        JLabel itemLabel = new JLabel("Item: ");
        itemLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        JTextField itemField = new JTextField(itemNameValue);
        itemField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(203, 213, 225), 1),
                BorderFactory.createEmptyBorder(6, 8, 6, 8)));
        itemPanel.add(itemLabel);
        itemPanel.add(itemField);

        JLabel typeLabel = new JLabel("Type: ");
        typeLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        JLabel categoryLabel = new JLabel("Category: ");
        categoryLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        typePanel.add(typeLabel);
        categoryPanel.add(categoryLabel);

        String[] type = {"Personal", "Business"};
        JComboBox<String> typeBox = new JComboBox<>(type);
        typeBox.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        typeBox.setBorder(BorderFactory.createLineBorder(new Color(203, 213, 225), 1));
        typePanel.add(typeBox);

        JComboBox<String> categoryBox = new JComboBox<>();
        categoryBox.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        categoryBox.setBorder(BorderFactory.createLineBorder(new Color(203, 213, 225), 1));
        String[] personal = {"Housing", "Food", "Transportation", "Entertainment", "Health", "Other"};
        String[] business = {"Payroll & Compensation", "Rent & Utilities", "Advertising & Marketing", "Software & Office Supplies", "Travel & Entertainment", "Other"};

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

        typeBox.setSelectedItem(typeValue);
        categoryBox.setSelectedItem(categoryValue);

        typeBox.addActionListener(e -> {
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

        JLabel costLabel = new JLabel("Cost: ");
        costLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        JTextField costField = new JTextField(String.valueOf(costValue));
        costField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(203, 213, 225), 1),
                BorderFactory.createEmptyBorder(6, 8, 6, 8)));
        costPanel.add(costLabel);
        costPanel.add(costField);

        JLabel dateLabel = new JLabel("Date: ");
        dateLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        datePanel.add(dateLabel);

        LocalDate[] sharedDate = new LocalDate[1];
        JLabel selectedDateLabel = new JLabel("Selected Date:");
        selectedDateLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        JLabel selectedDate = new JLabel();
        selectedDate.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        selectedDate.setText(String.valueOf(dateValue));
        sharedDate[0] = dateValue;
        date2Panel.add(selectedDateLabel);
        date2Panel.add(selectedDate);

        JButton selectDateButton = ButtonStyles.createSecondaryButton("Select Date");
        selectDateButton.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        datePanel.add(selectDateButton);

        selectDateButton.addActionListener(e -> {
            JDialog calendarDialog = new JDialog(this, "Select Date", true);
            calendarDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            calendarDialog.setSize(450, 450);
            calendarDialog.setLocationRelativeTo(this);
            calendarDialog.setResizable(false);

            JPanel calendarPanel = new JPanel(new BorderLayout());
            calendarPanel.setBackground(Color.WHITE);
            calendarPanel.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
            JPanel button2Panel = new JPanel();
            button2Panel.setOpaque(false);
            calendarDialog.add(calendarPanel, BorderLayout.CENTER);
            calendarDialog.add(button2Panel, BorderLayout.SOUTH);

            JCalendar calendar = new JCalendar();
            calendarPanel.add(calendar);

            JButton confirmButton = ButtonStyles.createPrimaryButton("Confirm");
            confirmButton.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            button2Panel.add(confirmButton);
            JButton cancelButton = ButtonStyles.createSecondaryButton("Cancel");
            cancelButton.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            button2Panel.add(cancelButton);

            confirmButton.addActionListener(a -> {
                Date date = calendar.getDate();
                Instant instantDate = date.toInstant();
                ZonedDateTime zonedDate = instantDate.atZone(ZoneId.systemDefault());
                LocalDate convertedDate = zonedDate.toLocalDate();

                if (convertedDate.isAfter(LocalDate.now())) {
                    JOptionPane.showMessageDialog(calendarDialog, "Selected date cannot be past the current date", "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                sharedDate[0] = convertedDate;
                selectedDate.setText("" + sharedDate[0]);
                calendarDialog.dispose();
            });

            cancelButton.addActionListener(a -> calendarDialog.dispose());
            calendarDialog.setVisible(true);
        });

        JButton confirmButton = ButtonStyles.createPrimaryButton("Confirm");
        confirmButton.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        buttonPanel.add(confirmButton);
        JButton cancelButton = ButtonStyles.createSecondaryButton("Cancel");
        cancelButton.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        buttonPanel.add(cancelButton);

        confirmButton.addActionListener(e -> {
            try {
                int id = row.getId();
                String itemName = itemField.getText();
                String selectedType = (String) typeBox.getSelectedItem();
                String selectedCategory = (String) categoryBox.getSelectedItem();
                String getCost = costField.getText();
                LocalDate date = sharedDate[0];

                if (itemName.isBlank() || selectedCategory == null || getCost.isBlank() || date == null) {
                    JOptionPane.showMessageDialog(this, "All fields must be filled", "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                double cost = Double.parseDouble(getCost);
                Expense updatedExpense = new Expense(id, itemName, selectedType, selectedCategory, cost, date);
                dispose();
                expenseController.updateExpense(id, updatedExpense);
            } catch (NumberFormatException z) {
                JOptionPane.showMessageDialog(this, "Cost must be a valid number", "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        cancelButton.addActionListener(e -> dispose());

        rootPanel.add(headerPanel, BorderLayout.NORTH);
        rootPanel.add(updatePanel, BorderLayout.CENTER);
        add(rootPanel);

        setVisible(true);
    }

}
