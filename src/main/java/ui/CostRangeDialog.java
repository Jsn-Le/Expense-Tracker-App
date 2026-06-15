package ui;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import model.ExpenseFilter;

public class CostRangeDialog extends JDialog {

    public CostRangeDialog(ExpenseFilter expenseFilter) {

        //Initial JDialog
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setSize(300, 150);

        // Panels
        JPanel costPanel = new JPanel(new GridLayout(2, 2));
        JPanel buttonPanel = new JPanel();
        add(costPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Labels/TextFields/Buttons
        JLabel minCostLabel = new JLabel("Min");
        JTextField minCostField = new JTextField();
        JLabel maxCostLabel = new JLabel("Max");
        JTextField maxCostField = new JTextField();
        JButton confirmButton = new JButton("Confirm");
        JButton cancelButton = new JButton("Cancel");
        costPanel.add(minCostLabel);
        costPanel.add(minCostField);
        costPanel.add(maxCostLabel);
        costPanel.add(maxCostField);
        buttonPanel.add(confirmButton);
        buttonPanel.add(cancelButton);

        // confirmButton
        confirmButton.addActionListener(e -> {
            try {
            double getMinCost = Double.parseDouble(minCostField.getText());
            double getMaxCost = Double.parseDouble(maxCostField.getText());

            if (getMinCost > getMaxCost) {
                JOptionPane.showMessageDialog(
                    this,
                    "Min cannot be greater than Max",
                    "Input Error",
                    JOptionPane.ERROR_MESSAGE
                );
                return;
            }
            if (getMinCost < 0 || getMaxCost < 0) {
                JOptionPane.showMessageDialog(
                    this,
                    "Min/Max cannot be lower than 0",
                    "Input Error",
                    JOptionPane.ERROR_MESSAGE
                );
                return;
            }

            expenseFilter.setMinCost(getMinCost);
            expenseFilter.setMaxCost(getMaxCost);
            dispose();
            } catch (NumberFormatException z){
                JOptionPane.showMessageDialog(
                    this,
                    "Cost must be a valid number",
                    "Input Error",
                    JOptionPane.ERROR_MESSAGE
                );
            }
        });

        // cancelButton
        cancelButton.addActionListener(e -> {
            dispose();
        });

        setVisible(true);
    }

}
