package app;

import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import controller.ExpenseController;
import model.CurrencyOption;
import model.ExpenseFilter;
import screens.ExpenseScreen;
import screens.OptionScreen;
import service.CurrencyService;
import service.ExpenseFileService;
import service.ExpenseFilterService;
import service.ExpenseService;
import service.ExpenseTotalService;
import ui.ExpenseTable;
import ui.TotalPanel;

public class ExpenseTrackerApp {

    public static void main(String[] args ) {

        SwingUtilities.invokeLater(() -> {

            // Services + Model
            ExpenseFileService expenseFileService = new ExpenseFileService();
            ExpenseService expenseService = new ExpenseService();
            ExpenseFilterService expenseFilterService = new ExpenseFilterService();
            CurrencyOption currencyOption = new CurrencyOption(null);
            CurrencyService currencyService = new CurrencyService(currencyOption);
            ExpenseTotalService expenseTotalService = new ExpenseTotalService(currencyService);
            ExpenseFilter expenseFilter = new ExpenseFilter("", "", true, true, null, null, null, null);

            // TableModel + JTable
            ExpenseTable expenseTable = new ExpenseTable(expenseFilterService, expenseService, currencyService, currencyOption, expenseFilter);

            // Total Panel + Controller
            TotalPanel totalPanel = new TotalPanel();
            ExpenseController expenseController = new ExpenseController(expenseFileService, expenseService, expenseTotalService, expenseTable, totalPanel);

            // Initial Frame
            JFrame frame = new JFrame("Expense Tracker");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(300, 75);

            // Button Panel
            JPanel buttonPanel = new JPanel();
            frame.add(buttonPanel);
            JButton openExpenseButton = new JButton("Open Expense");
            JButton createExpenseButton = new JButton("Create Expense");
            buttonPanel.add(openExpenseButton);
            buttonPanel.add(createExpenseButton);

            // Button Functions
            openExpenseButton.addActionListener(e -> {
                // JFileChooser
                JFileChooser fileChooser = new JFileChooser();

                int result = fileChooser.showOpenDialog(openExpenseButton);

                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();

                    if (!expenseController.openFile(selectedFile)) {
                        JOptionPane.showMessageDialog(
                            frame,
                            "The selected file is not a valid expense file",
                            "Open File Error",
                            JOptionPane.ERROR_MESSAGE
                        );
                        return;
                    }
                    frame.dispose();
                    ExpenseScreen.open(expenseController, expenseTable, totalPanel, expenseFilter);
                }
            });

            createExpenseButton.addActionListener(e -> {
                frame.dispose();
                OptionScreen.open(expenseController, expenseTable, totalPanel, expenseFilter, currencyService);
            });

            frame.setVisible(true);
        });

    };

}
