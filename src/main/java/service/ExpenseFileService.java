package service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import model.Expense;

public class ExpenseFileService {
    
    private static final Logger LOGGER = Logger.getLogger(ExpenseFileService.class.getName());

    public void saveFile(List<Expense> expenses, File file) {
        try (ObjectOutputStream save = new ObjectOutputStream(new FileOutputStream(file))) {
            save.writeObject(expenses);
        } catch (java.io.IOException e) {
            LOGGER.log(Level.SEVERE, "Error saving expenses to file: " + file.getName(), e);
        }
    }

    public List<Expense> openFile(File file) {
        try (ObjectInputStream open = new ObjectInputStream(new FileInputStream(file))) {
            Object expensesObject = open.readObject();
            @SuppressWarnings("unchecked")
            List<Expense> expensesList = (List<Expense>) expensesObject;
            return expensesList;
        } catch (java.io.IOException | ClassNotFoundException e) {
            LOGGER.log(Level.SEVERE, "Error opening expenses from file: " + file.getName(), e);
            return null;
        }
    }

}
