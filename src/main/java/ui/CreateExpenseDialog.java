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
import javax.swing.JTextField;

import com.toedter.calendar.JCalendar;

import controller.ExpenseController;

public class CreateExpenseDialog extends JDialog {

    public CreateExpenseDialog(ExpenseController expenseController) {

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
        JLabel titleLabel = new JLabel("Create expense");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 15));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel subtitleLabel = new JLabel("Add a new expense entry with your preferred details.");
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        subtitleLabel.setForeground(new Color(96, 104, 117));
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        headerPanel.add(titleLabel);
        headerPanel.add(Box.createVerticalStrut(4));
        headerPanel.add(subtitleLabel);

        JPanel createPanel = new JPanel();
        createPanel.setLayout(new BoxLayout(createPanel, BoxLayout.Y_AXIS));
        createPanel.setOpaque(false);
        createPanel.setBorder(BorderFactory.createEmptyBorder(12, 0, 0, 0));

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

        createPanel.add(itemPanel);
        createPanel.add(Box.createVerticalStrut(8));
        createPanel.add(typePanel);
        createPanel.add(Box.createVerticalStrut(8));
        createPanel.add(categoryPanel);
        createPanel.add(Box.createVerticalStrut(8));
        createPanel.add(costPanel);
        createPanel.add(Box.createVerticalStrut(8));
        createPanel.add(datePanel);
        createPanel.add(Box.createVerticalStrut(8));
        createPanel.add(date2Panel);
        createPanel.add(Box.createVerticalStrut(14));
        createPanel.add(buttonPanel);

        JLabel itemLabel = new JLabel("Item: ");
        itemLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        JTextField itemField = new JTextField();
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

        String[] type = {"", "Personal", "Business"};
        JComboBox<String> typeBox = new JComboBox<>(type);
        typeBox.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        typeBox.setBorder(BorderFactory.createLineBorder(new Color(203, 213, 225), 1));
        typePanel.add(typeBox);

        JComboBox<String> categoryBox = new JComboBox<>();
        categoryBox.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        categoryBox.setBorder(BorderFactory.createLineBorder(new Color(203, 213, 225), 1));
        String[] personal = {"Housing", "Food", "Transportation", "Entertainment", "Health", "Other"};
        String[] business = {"Payroll & Compensation", "Rent & Utilities", "Advertising & Marketing", "Software & Office Supplies", "Travel & Entertainment", "Other"};

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
        categoryPanel.add(categoryBox);

        JLabel costLabel = new JLabel("Cost: ");
        costLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        JTextField costField = new JTextField();
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
        JLabel selectedDate = new JLabel("None");
        selectedDate.setFont(new Font("Segoe UI", Font.PLAIN, 12));
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
                selectedDate.setText(String.valueOf(sharedDate[0]));
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
                dispose();
                expenseController.addExpense(itemName, selectedType, selectedCategory, cost, date);
            } catch (NumberFormatException z) {
                JOptionPane.showMessageDialog(this, "Cost must be a valid number", "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        cancelButton.addActionListener(e -> dispose());

        rootPanel.add(headerPanel, BorderLayout.NORTH);
        rootPanel.add(createPanel, BorderLayout.CENTER);
        add(rootPanel);

        setVisible(true);
    }

}
