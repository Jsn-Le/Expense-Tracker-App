package app;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import screens.OptionScreen;

public class ExpenseTrackerApp {

    public static void main(String[] args ) {

        SwingUtilities.invokeLater(() -> {

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
                
            });

            createExpenseButton.addActionListener(e -> {
                frame.dispose();
                OptionScreen.main(new String[] {});
            });

            frame.setVisible(true);
        });

    };

}
