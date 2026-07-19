package screens;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Currency;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import app.ExpenseTrackerApp;
import controller.ExpenseController;
import model.ExpenseFilter;
import service.CurrencyService;
import ui.ButtonStyles;
import ui.ExpenseTable;
import ui.TotalPanel;

public class OptionScreen {

    public static void open(ExpenseController expenseController, ExpenseTable expenseTable, TotalPanel totalPanel, ExpenseFilter expenseFilter, CurrencyService currencyService) {

        SwingUtilities.invokeLater(() -> {

            JFrame frame = new JFrame("Expense Tracker");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(430, 260);
            frame.setLocationRelativeTo(null);
            frame.setResizable(false);

            JPanel rootPanel = new JPanel();
            rootPanel.setLayout(new BoxLayout(rootPanel, BoxLayout.Y_AXIS));
            rootPanel.setBackground(new Color(248, 250, 252));
            rootPanel.setBorder(BorderFactory.createEmptyBorder(24, 24, 24, 24));

            JLabel titleLabel = new JLabel("Choose your currency");
            titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
            titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

            JLabel subtitleLabel = new JLabel("Select the currency you want to use for your expense totals.");
            subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
            subtitleLabel.setForeground(new Color(96, 104, 117));
            subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

            JPanel formPanel = new JPanel();
            formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
            formPanel.setOpaque(false);
            formPanel.setBorder(BorderFactory.createEmptyBorder(18, 0, 16, 0));
            formPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

            JLabel currencyLabel = new JLabel("Currency");
            currencyLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            currencyLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

            List<Currency> currencies = new ArrayList<>(Arrays.asList(
                Currency.getInstance("USD"),
                Currency.getInstance("EUR"),
                Currency.getInstance("GBP"),
                Currency.getInstance("JPY"),
                Currency.getInstance("CAD"),
                Currency.getInstance("AUD"),
                Currency.getInstance("CNY"),
                Currency.getInstance("KRW"),
                Currency.getInstance("VND"),
                Currency.getInstance("INR")
            ));
            JComboBox<Currency> currencyDropdown = new JComboBox<>(currencies.toArray(new Currency[0]));
            currencyDropdown.setPreferredSize(new Dimension(220, 32));
            currencyDropdown.setMaximumSize(new Dimension(220, 32));
            currencyDropdown.setAlignmentX(Component.CENTER_ALIGNMENT);

            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
            buttonPanel.setOpaque(false);

            JButton confirmButton = ButtonStyles.createPrimaryButton("Confirm");
            JButton returnButton = ButtonStyles.createSecondaryButton("Return");
            buttonPanel.add(confirmButton);
            buttonPanel.add(returnButton);

            formPanel.add(currencyLabel);
            formPanel.add(Box.createVerticalStrut(6));
            formPanel.add(currencyDropdown);

            rootPanel.add(Box.createVerticalStrut(6));
            rootPanel.add(titleLabel);
            rootPanel.add(Box.createVerticalStrut(6));
            rootPanel.add(subtitleLabel);
            rootPanel.add(formPanel);
            rootPanel.add(buttonPanel);

            frame.setContentPane(rootPanel);

            confirmButton.addActionListener(e -> {
                Currency selectedCurrency = (Currency) currencyDropdown.getSelectedItem();
                currencyService.selectCurrency(selectedCurrency);
                expenseTable.refreshView();

                frame.dispose();
                ExpenseScreen.open(expenseController, expenseTable, totalPanel, expenseFilter);
            });

            returnButton.addActionListener(e -> {
                frame.dispose();
                ExpenseTrackerApp.main(new String[] {});
            });

            frame.setVisible(true);
        });
    }

}

