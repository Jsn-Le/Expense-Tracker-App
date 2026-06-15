package ui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.toedter.calendar.JCalendar;

import model.ExpenseFilter;

public class DateRangeDialog extends JDialog {

    public DateRangeDialog(ExpenseFilter expenseFilter) {

        //Initial JDialog
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setSize(400, 300);

        // Panels
        JPanel datePanel = new JPanel(new GridLayout(2, 3));
        JPanel buttonPanel = new JPanel();
        add(datePanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Labels/Buttons
        JLabel startDateLabel = new JLabel("Start Date: ");
        JLabel startDateLabel2 = new JLabel();
        JButton startDateButton = new JButton("Select Date");
        JLabel endDateLabel = new JLabel("End Date: ");
        JLabel endDateLabel2 = new JLabel();
        JButton endDateButton = new JButton("Select Date");
        JButton confirmButton = new JButton("Confirm");
        JButton cancelButton = new JButton("Cancel");
        datePanel.add(startDateLabel);
        datePanel.add(startDateLabel2);
        datePanel.add(startDateButton);
        datePanel.add(endDateLabel);
        datePanel.add(endDateLabel2);
        datePanel.add(endDateButton);
        buttonPanel.add(confirmButton);
        buttonPanel.add(cancelButton);

        // startDateButton
        startDateButton.addActionListener(b -> {
            JDialog calendarDialog = new JDialog();
            calendarDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            calendarDialog.setSize(450,450);

            JPanel calendarPanel = new JPanel(new BorderLayout());
            JPanel button2Panel = new JPanel();
            calendarDialog.add(calendarPanel, BorderLayout.CENTER);
            calendarDialog.add(button2Panel, BorderLayout.SOUTH);

            JCalendar calendar = new JCalendar();
            calendarPanel.add(calendar);

            JButton confirmButton2 = new JButton("Confirm");
            button2Panel.add(confirmButton2);
            JButton cancelButton2 = new JButton("Cancel");
            button2Panel.add(cancelButton2);

            confirmButton2.addActionListener(x -> {
                Date date = calendar.getDate();
                Instant instantDate = date.toInstant();
                ZonedDateTime zonedDate = instantDate.atZone(ZoneId.systemDefault());
                LocalDate convertedDate = zonedDate.toLocalDate();

                if (convertedDate.isAfter(LocalDate.now())) {
                    JOptionPane.showMessageDialog(
                        calendarDialog,
                        "Selected date cannot be past the current date",
                        "Input Error",
                        JOptionPane.ERROR_MESSAGE
                    ); 
                    return;
                }
                if (expenseFilter.getEndDate() != null) {
                    if (convertedDate.isAfter(expenseFilter.getEndDate())) {
                    JOptionPane.showMessageDialog(
                        calendarDialog,
                        "Selected date cannot be after end date",
                        "Input Error",
                        JOptionPane.ERROR_MESSAGE
                    );
                    return;
                    }
                }

                expenseFilter.setStartDate(convertedDate);
                startDateLabel2.setText(String.valueOf(convertedDate));
                calendarDialog.dispose();
            });

            cancelButton2.addActionListener(y -> {
                calendarDialog.dispose();
            });

            calendarDialog.setVisible(true);
        });

        // endDateButton
        endDateButton.addActionListener(b -> {
            JDialog calendarDialog = new JDialog();
            calendarDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            calendarDialog.setSize(450,450);

            JPanel calendarPanel = new JPanel(new BorderLayout());
            JPanel button2Panel = new JPanel();
            calendarDialog.add(calendarPanel, BorderLayout.CENTER);
            calendarDialog.add(button2Panel, BorderLayout.SOUTH);

            JCalendar calendar = new JCalendar();
            calendarPanel.add(calendar);

            JButton confirmButton2 = new JButton("Confirm");
            button2Panel.add(confirmButton2);
            JButton cancelButton2 = new JButton("Cancel");
            button2Panel.add(cancelButton2);

            confirmButton2.addActionListener(x -> {
                Date date = calendar.getDate();
                Instant instantDate = date.toInstant();
                ZonedDateTime zonedDate = instantDate.atZone(ZoneId.systemDefault());
                LocalDate convertedDate = zonedDate.toLocalDate();

                if (convertedDate.isAfter(LocalDate.now())) {
                    JOptionPane.showMessageDialog(
                        calendarDialog,
                        "Selected date cannot be past the current date",
                        "Input Error",
                        JOptionPane.ERROR_MESSAGE
                    ); 
                    return;
                }
                if (expenseFilter.getStartDate() != null) {
                    if (convertedDate.isBefore(expenseFilter.getStartDate())) {
                    JOptionPane.showMessageDialog(
                        calendarDialog,
                        "Selected date cannot be before start date",
                        "Input Error",
                        JOptionPane.ERROR_MESSAGE
                    );
                    return;
                    }
                }

                expenseFilter.setEndDate(convertedDate);
                endDateLabel2.setText(String.valueOf(convertedDate));
                calendarDialog.dispose();
            });

            cancelButton2.addActionListener(y -> {
                calendarDialog.dispose();
            });

            calendarDialog.setVisible(true);
        });

        // confirmButton
        confirmButton.addActionListener(a -> {
            if (expenseFilter.getStartDate() == null || expenseFilter.getEndDate() == null) {
                JOptionPane.showMessageDialog(
                    this,
                    "Both dates must be selected",
                    "Input Error",
                    JOptionPane.ERROR_MESSAGE
                );
                return;
            }

            dispose();
        });

        // cancelButton
        cancelButton.addActionListener(a -> {
            dispose();
        });

        setVisible(true);
    }

}
