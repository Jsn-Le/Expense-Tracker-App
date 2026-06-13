package model;
import java.time.LocalDate;

public class Expense {

    final int id;
    private String itemName;
    private String type;
    private String category;
    private double itemCost;
    private LocalDate date;

    public Expense(int id, String itemName, String type, String category, double itemCost, LocalDate date) {
        this.id = id;
        this.itemName = itemName;
        this.type = type;
        this.category = category;
        this.itemCost = itemCost;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public String getItemName() {
        return itemName;
    }

    public String getType() {
        return type;
    }

    public String getCategory() {
        return category;
    }

    public double getItemCost() {
        return itemCost;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setItemCost(double itemCost) {
        this.itemCost = itemCost;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

}
