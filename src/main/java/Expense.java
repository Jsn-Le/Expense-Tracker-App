import java.time.LocalDate;

public class Expense {

    final int id;
    private String itemName;
    private String type;
    private String category;
    private double itemCost;
    private LocalDate date;

    Expense (int id, String itemName, String type, String category, double itemCost, LocalDate date) {
        this.id = id;
        this.itemName = itemName;
        this.type = type;
        this.category = category;
        this.itemCost = itemCost;
        this.date = date;
    }

    int getId() {
        return id;
    }

    String getItemName() {
        return itemName;
    }

    String getType() {
        return type;
    }

    String getCategory() {
        return category;
    }

    double getItemCost() {
        return itemCost;
    }

    LocalDate getDate() {
        return date;
    }

    void setItemName(String itemName) {
        this.itemName = itemName;
    }

    void setType(String type) {
        this.type = type;
    }

    void setCategory(String category) {
        this.category = category;
    }

    void setItemCost(double itemCost) {
        this.itemCost = itemCost;
    }

    void setDate(LocalDate date) {
        this.date = date;
    }

}
