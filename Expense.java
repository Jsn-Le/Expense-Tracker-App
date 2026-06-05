import java.time.*;

public class Expense {

    final int id;
    private String itemName;
    private String category;
    private long itemCost;
    private LocalDate date;

    Expense (int id, String itemName, String category, long itemCost, LocalDate date) {
        this.id = id;
        this.itemName = itemName;
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

    String getCategory() {
        return category;
    }

    long getItemCost() {
        return itemCost;
    }

    LocalDate getDate() {
        return date;
    }

    void setItemName(String itemName) {
        this.itemName = itemName;
    }

    void setCategory(String category) {
        this.category = category;
    }

    void setItemCost(long itemCost) {
        this.itemCost = itemCost;
    }

    void setDate(LocalDate date) {
        this.date = date;
    }

}
