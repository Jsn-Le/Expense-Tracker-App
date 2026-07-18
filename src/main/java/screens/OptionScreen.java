package screens;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Currency;
import java.util.List;

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
import ui.ExpenseTable;
import ui.TotalPanel;

public class OptionScreen {

    public static void open(ExpenseController expenseController, ExpenseTable expenseTable, TotalPanel totalPanel, ExpenseFilter expenseFilter, CurrencyService currencyService) {

        SwingUtilities.invokeLater(() -> {

            // Initial Frame
            JFrame frame = new JFrame("Expense Tracker");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(300, 100);

            // Panels + Buttons
            JPanel dropdownPanel = new JPanel();
            JPanel buttonPanel = new JPanel();
            frame.add(dropdownPanel, BorderLayout.CENTER);
            frame.add(buttonPanel, BorderLayout.SOUTH);
            JLabel currencyLabel = new JLabel("Select Currency: ");
            JButton confirmButton = new JButton("Confirm");
            JButton returnButton = new JButton("Return");
            dropdownPanel.add(currencyLabel);
            buttonPanel.add(confirmButton);
            buttonPanel.add(returnButton);

            // Dropdown Function
            List<Currency> currencies = new ArrayList<>(Arrays.asList(
                Currency.getInstance("USD"),
                Currency.getInstance("EUR"),
                Currency.getInstance("GBP"),
                Currency.getInstance("EUR"),
                Currency.getInstance("JPY"),
                Currency.getInstance("CAD"),
                Currency.getInstance("AUD"),
                Currency.getInstance("CNY"),
                Currency.getInstance("KRW"),
                Currency.getInstance("VND"),
                Currency.getInstance("INR")
            ));
            JComboBox<Currency> currencyDropdown = new JComboBox<>(currencies.toArray(new Currency[0]));
            dropdownPanel.add(currencyDropdown);

            // Button Functions
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
