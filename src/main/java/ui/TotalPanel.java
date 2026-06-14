package ui;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class TotalPanel extends JPanel {

    private final JLabel dailyLabel;
    private final JLabel weeklyLabel;
    private final JLabel monthlyLabel;
    private final JLabel yearlyLabel;
    private final JLabel totalLabel;

    public TotalPanel() {
        setLayout(new GridLayout(1, 5, 10, 0));

        dailyLabel = new JLabel("Daily Average: $0.00");
        weeklyLabel = new JLabel("Weekly Average: $0.00");
        monthlyLabel = new JLabel("Monthly Average: $0.00");
        yearlyLabel = new JLabel("Yearly Average: $0.00");
        totalLabel = new JLabel("Total: $0.00");

        add(dailyLabel);
        add(weeklyLabel);
        add(monthlyLabel);
        add(yearlyLabel);
        add(totalLabel);
    }

    public void updateTotals(double daily, double weekly, double monthly, double yearly, double total) {
        totalLabel.setText("Total: $" + total);
        dailyLabel.setText("Daily Average: $" + daily);
        weeklyLabel.setText("Weekly Average: $" + weekly);
        monthlyLabel.setText("Monthly Average: $" + monthly);
        yearlyLabel.setText("Yearly Average: $" + yearly);
    }

}
