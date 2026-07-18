package ui;

import java.awt.BorderLayout;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import controller.ExpenseController;

public class MenuBar extends JMenuBar {

    public MenuBar(JFrame frame, ExpenseController expenseController) {

        // File JMenu
        JMenu fileMenu = new JMenu("File");
        this.add(fileMenu);

        // Menu Items
        JMenuItem newFile = new JMenuItem("New File");
        JMenuItem openFile = new JMenuItem("Open File");
        JMenuItem saveFile = new JMenuItem("Save");
        JMenuItem saveAsFile = new JMenuItem("Save As");
        JMenuItem exit = new JMenuItem("Exit");

        fileMenu.add(newFile);
        fileMenu.add(openFile);
        fileMenu.add(saveFile);
        fileMenu.add(saveAsFile);
        fileMenu.add(exit);

        // New File Function
        newFile.addActionListener(e -> {
            if (!expenseController.saveFile()) {
                JDialog saveOption = new JDialog(frame, true);

                saveOption.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                saveOption.setSize(625, 100);

                JPanel labelPanel = new JPanel();
                JPanel buttonPanel = new JPanel();

                saveOption.add(labelPanel, BorderLayout.NORTH);
                saveOption.add(buttonPanel, BorderLayout.SOUTH);

                JLabel saveLabel = new JLabel("Your existing expenses are not saved to a file... Would you like to save it to a file before creating a new file?");
                JButton yesButton = new JButton("Yes");
                JButton noButton = new JButton("No");

                labelPanel.add(saveLabel);
                buttonPanel.add(yesButton);
                buttonPanel.add(noButton);

                yesButton.addActionListener(a -> {
                    saveAsFile.doClick();

                    if (expenseController.getCurrentFile() != null) {
                        saveOption.dispose();
                        expenseController.newFile();
                    } else {
                        saveOption.dispose();
                    }
                });

                noButton.addActionListener(a -> {
                    saveOption.dispose();
                    expenseController.newFile();
                });

                saveOption.setVisible(true);
            } else {
                expenseController.newFile();
            }
        });

        // Open File Function
        openFile.addActionListener(e -> {
            // JFileChooser
            JFileChooser fileChooser = new JFileChooser();

            int result = fileChooser.showOpenDialog(fileMenu);

            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();

                if (!expenseController.openFile(selectedFile)) {
                    JOptionPane.showMessageDialog(
                        fileMenu,
                        "The selected file is not a valid expense file",
                        "Open File Error",
                        JOptionPane.ERROR_MESSAGE
                    );
                }
            }
        });

        // Save Function
        saveFile.addActionListener(e -> {
            if (!expenseController.saveFile()) {
                saveAsFile.doClick();
            }
        });

        // Save As Function
        saveAsFile.addActionListener(e -> {
            // JFileChooser
            JFileChooser fileChooser = new JFileChooser();
            
            int result = fileChooser.showSaveDialog(fileMenu);

            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();

                expenseController.saveAsFile(selectedFile);
            }
        });

        // Exit Function
        exit.addActionListener(e -> {
            frame.dispose();
        });
    }

}
