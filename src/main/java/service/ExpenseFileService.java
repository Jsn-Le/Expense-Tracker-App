package service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import model.ExpenseFileData;

public class ExpenseFileService {
    
    private static final Logger LOGGER = Logger.getLogger(ExpenseFileService.class.getName());

    // Save File
    public void saveFile(ExpenseFileData expenseFileData, File file) {
        try (ObjectOutputStream save = new ObjectOutputStream(new FileOutputStream(file))) {
            save.writeObject(expenseFileData);
        } catch (java.io.IOException e) {
            LOGGER.log(Level.SEVERE, "Error saving expenses to file: " + file.getName(), e);
        }
    }

    // Open File
    public ExpenseFileData openFile(File file) {
        try (ObjectInputStream open = new ObjectInputStream(new FileInputStream(file))) {
            Object fileObject = open.readObject();
            ExpenseFileData expenseFileData = (ExpenseFileData) fileObject;
            return expenseFileData;
        } catch (java.io.IOException | ClassNotFoundException e) {
            LOGGER.log(Level.SEVERE, "Error opening expenses from file: " + file.getName(), e);
            return null;
        }
    }

}
