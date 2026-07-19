package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import model.ExpenseFilter;

public class CostRangeDialog extends JDialog {

    public CostRangeDialog(ExpenseFilter expenseFilter) {

        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setSize(320, 220);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel rootPanel = new JPanel();
        rootPanel.setLayout(new BoxLayout(rootPanel, BoxLayout.Y_AXIS));
        rootPanel.setBackground(new Color(248, 250, 252));
        rootPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titleLabel = new JLabel("Set cost range");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 15));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitleLabel = new JLabel("Choose minimum and maximum values for your filter.");
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        subtitleLabel.setForeground(new Color(96, 104, 117));
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel costPanel = new JPanel(new GridLayout(2, 2, 8, 8));
        costPanel.setOpaque(false);
        costPanel.setBorder(BorderFactory.createEmptyBorder(12, 0, 10, 0));

        JLabel minCostLabel = new JLabel("Min");
        minCostLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        JTextField minCostField = new JTextField();
        minCostField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(203, 213, 225), 1),
                BorderFactory.createEmptyBorder(6, 8, 6, 8)));
        JLabel maxCostLabel = new JLabel("Max");
        maxCostLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        JTextField maxCostField = new JTextField();
        maxCostField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(203, 213, 225), 1),
                BorderFactory.createEmptyBorder(6, 8, 6, 8)));

        costPanel.add(minCostLabel);
        costPanel.add(minCostField);
        costPanel.add(maxCostLabel);
        costPanel.add(maxCostField);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        JButton confirmButton = ButtonStyles.createPrimaryButton("Confirm");
        confirmButton.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        JButton cancelButton = ButtonStyles.createSecondaryButton("Cancel");
        cancelButton.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        buttonPanel.add(confirmButton);
        buttonPanel.add(Box.createHorizontalStrut(8));
        buttonPanel.add(cancelButton);

        rootPanel.add(titleLabel);
        rootPanel.add(Box.createVerticalStrut(4));
        rootPanel.add(subtitleLabel);
        rootPanel.add(costPanel);
        rootPanel.add(buttonPanel);
        add(rootPanel, BorderLayout.CENTER);

        confirmButton.addActionListener(e -> {
            try {
                double getMinCost = Double.parseDouble(minCostField.getText());
                double getMaxCost = Double.parseDouble(maxCostField.getText());

                if (getMinCost > getMaxCost) {
                    JOptionPane.showMessageDialog(this, "Min cannot be greater than Max", "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (getMinCost < 0 || getMaxCost < 0) {
                    JOptionPane.showMessageDialog(this, "Min/Max cannot be lower than 0", "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                expenseFilter.setMinCost(getMinCost);
                expenseFilter.setMaxCost(getMaxCost);
                dispose();
            } catch (NumberFormatException z) {
                JOptionPane.showMessageDialog(this, "Cost must be a valid number", "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        cancelButton.addActionListener(e -> dispose());

        setVisible(true);
    }

}
