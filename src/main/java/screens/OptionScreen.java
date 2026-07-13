package screens;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Currency;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import app.ExpenseTrackerApp;
import service.CurrencyService;

public class OptionScreen {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            // Model
            CurrencyService currencyService = new CurrencyService();

            // Initial Frame
            JFrame frame = new JFrame("Expense Tracker");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(300, 100);

            // Panels + Buttons
            JPanel dropdownPanel = new JPanel();
            JPanel buttonPanel = new JPanel();
            frame.add(dropdownPanel, BorderLayout.CENTER);
            frame.add(buttonPanel, BorderLayout.SOUTH);
            JButton confirmButton = new JButton("Confirm");
            JButton returnButton = new JButton("Return");
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
                currencyService.addCurrency(1, selectedCurrency);

                frame.dispose();
                ExpenseScreen.main(new String[] {});
            });

            returnButton.addActionListener(e -> {
                frame.dispose();
                ExpenseTrackerApp.main(new String[] {});
            });

            frame.setVisible(true);
        });
    }
        
}
