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
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.toedter.calendar.JCalendar;

import model.ExpenseFilter;

public class DateRangeDialog extends JDialog {

    public DateRangeDialog(ExpenseFilter expenseFilter) {

        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setSize(420, 320);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel rootPanel = new JPanel();
        rootPanel.setLayout(new BoxLayout(rootPanel, BoxLayout.Y_AXIS));
        rootPanel.setBackground(new Color(248, 250, 252));
        rootPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titleLabel = new JLabel("Select date range");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 15));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitleLabel = new JLabel("Choose a start and end date for your filter.");
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        subtitleLabel.setForeground(new Color(96, 104, 117));
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel datePanel = new JPanel(new GridLayout(2, 3, 8, 8));
        datePanel.setOpaque(false);
        datePanel.setBorder(BorderFactory.createEmptyBorder(12, 0, 10, 0));

        JLabel startDateLabel = new JLabel("Start Date:");
        startDateLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        JLabel startDateLabel2 = new JLabel();
        startDateLabel2.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        JButton startDateButton = ButtonStyles.createSecondaryButton("Select Date");
        startDateButton.setFont(new Font("Segoe UI", Font.PLAIN, 12));

        JLabel endDateLabel = new JLabel("End Date:");
        endDateLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        JLabel endDateLabel2 = new JLabel();
        endDateLabel2.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        JButton endDateButton = ButtonStyles.createSecondaryButton("Select Date");
        endDateButton.setFont(new Font("Segoe UI", Font.PLAIN, 12));

        datePanel.add(startDateLabel);
        datePanel.add(startDateLabel2);
        datePanel.add(startDateButton);
        datePanel.add(endDateLabel);
        datePanel.add(endDateLabel2);
        datePanel.add(endDateButton);

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
        rootPanel.add(datePanel);
        rootPanel.add(buttonPanel);
        add(rootPanel, BorderLayout.CENTER);

        startDateButton.addActionListener(b -> {
            JDialog calendarDialog = new JDialog(this, "Select Date", true);
            calendarDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            calendarDialog.setSize(450, 450);
            calendarDialog.setLocationRelativeTo(this);
            calendarDialog.setResizable(false);

            JPanel calendarPanel = new JPanel(new BorderLayout());
            calendarPanel.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
            calendarPanel.setBackground(Color.WHITE);
            JPanel button2Panel = new JPanel();
            button2Panel.setOpaque(false);
            calendarDialog.add(calendarPanel, BorderLayout.CENTER);
            calendarDialog.add(button2Panel, BorderLayout.SOUTH);

            JCalendar calendar = new JCalendar();
            calendarPanel.add(calendar);

            JButton confirmButton2 = ButtonStyles.createPrimaryButton("Confirm");
            confirmButton2.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            button2Panel.add(confirmButton2);
            JButton cancelButton2 = ButtonStyles.createSecondaryButton("Cancel");
            cancelButton2.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            button2Panel.add(cancelButton2);

            confirmButton2.addActionListener(x -> {
                Date date = calendar.getDate();
                Instant instantDate = date.toInstant();
                ZonedDateTime zonedDate = instantDate.atZone(ZoneId.systemDefault());
                LocalDate convertedDate = zonedDate.toLocalDate();

                if (convertedDate.isAfter(LocalDate.now())) {
                    JOptionPane.showMessageDialog(calendarDialog, "Selected date cannot be past the current date", "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (expenseFilter.getEndDate() != null) {
                    if (convertedDate.isAfter(expenseFilter.getEndDate())) {
                        JOptionPane.showMessageDialog(calendarDialog, "Selected date cannot be after end date", "Input Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }

                expenseFilter.setStartDate(convertedDate);
                startDateLabel2.setText(String.valueOf(convertedDate));
                calendarDialog.dispose();
            });

            cancelButton2.addActionListener(y -> calendarDialog.dispose());
            calendarDialog.setVisible(true);
        });

        endDateButton.addActionListener(b -> {
            JDialog calendarDialog = new JDialog(this, "Select Date", true);
            calendarDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            calendarDialog.setSize(450, 450);
            calendarDialog.setLocationRelativeTo(this);
            calendarDialog.setResizable(false);

            JPanel calendarPanel = new JPanel(new BorderLayout());
            calendarPanel.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
            calendarPanel.setBackground(Color.WHITE);
            JPanel button2Panel = new JPanel();
            button2Panel.setOpaque(false);
            calendarDialog.add(calendarPanel, BorderLayout.CENTER);
            calendarDialog.add(button2Panel, BorderLayout.SOUTH);

            JCalendar calendar = new JCalendar();
            calendarPanel.add(calendar);

            JButton confirmButton2 = ButtonStyles.createPrimaryButton("Confirm");
            confirmButton2.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            button2Panel.add(confirmButton2);
            JButton cancelButton2 = ButtonStyles.createSecondaryButton("Cancel");
            cancelButton2.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            button2Panel.add(cancelButton2);

            confirmButton2.addActionListener(x -> {
                Date date = calendar.getDate();
                Instant instantDate = date.toInstant();
                ZonedDateTime zonedDate = instantDate.atZone(ZoneId.systemDefault());
                LocalDate convertedDate = zonedDate.toLocalDate();

                if (convertedDate.isAfter(LocalDate.now())) {
                    JOptionPane.showMessageDialog(calendarDialog, "Selected date cannot be past the current date", "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (expenseFilter.getStartDate() != null) {
                    if (convertedDate.isBefore(expenseFilter.getStartDate())) {
                        JOptionPane.showMessageDialog(calendarDialog, "Selected date cannot be before start date", "Input Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }

                expenseFilter.setEndDate(convertedDate);
                endDateLabel2.setText(String.valueOf(convertedDate));
                calendarDialog.dispose();
            });

            cancelButton2.addActionListener(y -> calendarDialog.dispose());
            calendarDialog.setVisible(true);
        });

        confirmButton.addActionListener(a -> {
            if (expenseFilter.getStartDate() == null || expenseFilter.getEndDate() == null) {
                JOptionPane.showMessageDialog(this, "Both dates must be selected", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            dispose();
        });

        cancelButton.addActionListener(a -> dispose());

        setVisible(true);
    }

}
