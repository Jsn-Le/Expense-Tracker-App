import java.time.*;

public class Expense {

    final int id;
    String itemName;
    long itemCost;
    LocalDate date;

    Expense (int id, String itemName, long itemCost, LocalDate date) {
        this.id = id;
        this.itemName = itemName;
        this.itemCost = itemCost;
        this.date = date;
    }

    int getId() {
        return id;
    }

    String getItemName() {
        return itemName;
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

    void setItemCost(long itemCost) {
        this.itemCost = itemCost;
    }

    void setDate(LocalDate date) {
        this.date = date;
    }

}
