package ui;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import app.ExpenseTrackerApp;
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

                expenseController.openFile(selectedFile);
            }
        });

        // Save Function
        saveFile.addActionListener(e -> {
            
        });

        // Save As Function
        saveAsFile.addActionListener(e -> {
            
        });

        // Exit Function
        exit.addActionListener(e -> {
            frame.dispose();
            ExpenseTrackerApp.main(new String[] {});
        });
    }

}
