package app;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
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
import ui.ButtonStyles;
import ui.ExpenseTable;
import ui.TotalPanel;

public class ExpenseTrackerApp {

    public static void main(String[] args) {

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
            ExpenseController expenseController = new ExpenseController(currencyService, expenseFileService, expenseService, expenseTotalService, expenseTable, totalPanel);

            // Initial Frame
            JFrame frame = new JFrame("Expense Tracker");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(430, 240);
            frame.setLocationRelativeTo(null);
            frame.setResizable(false);

            JPanel rootPanel = new JPanel(new BorderLayout());
            rootPanel.setBackground(new Color(248, 250, 252));
            rootPanel.setBorder(BorderFactory.createEmptyBorder(28, 28, 28, 28));

            JPanel contentPanel = new JPanel();
            contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
            contentPanel.setOpaque(false);
            contentPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

            JLabel titleLabel = new JLabel("Expense Tracker");
            titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
            titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

            JLabel subtitleLabel = new JLabel("Choose how you'd like to start managing your expenses.");
            subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
            subtitleLabel.setForeground(new Color(96, 104, 117));
            subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 0));
            buttonPanel.setOpaque(false);

            JButton openExpenseButton = ButtonStyles.createPrimaryButton("Open Expense");
            JButton createExpenseButton = ButtonStyles.createPrimaryButton("Create Expense");
            buttonPanel.add(openExpenseButton);
            buttonPanel.add(createExpenseButton);

            contentPanel.add(Box.createVerticalStrut(8));
            contentPanel.add(titleLabel);
            contentPanel.add(Box.createVerticalStrut(8));
            contentPanel.add(subtitleLabel);
            contentPanel.add(Box.createVerticalStrut(20));
            contentPanel.add(buttonPanel);

            rootPanel.add(contentPanel, BorderLayout.CENTER);
            frame.setContentPane(rootPanel);

            // Button Functions
            openExpenseButton.addActionListener(e -> {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Open Expense File");
                fileChooser.setApproveButtonText("Open");
                fileChooser.setAcceptAllFileFilterUsed(true);

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
    }

}
