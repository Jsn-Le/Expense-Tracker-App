package model;

import java.time.LocalDate;

public class ExpenseFilter {

    String selectedFilterType = "";
    String selectedFilterCategory = "";
    boolean sortCost = true;
    boolean sortDate = true;
    Double minCost;
    Double maxCost;
    LocalDate startDate;
    LocalDate endDate;

    public ExpenseFilter(String selectedFilterType, String selectedFilterCategory, boolean sortCost, boolean sortDate, 
                        Double minCost, Double maxCost, LocalDate startDate, LocalDate endDate) {
        this.selectedFilterType = selectedFilterType;
        this.selectedFilterCategory = selectedFilterCategory;
        this.sortCost = sortCost;
        this.sortDate = sortDate;
        this.minCost = minCost;
        this.maxCost = maxCost;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getSelectedFilterType() {
        return selectedFilterType;
    }

    public String getSelectedFilterCategory() {
        return selectedFilterCategory;
    }

    public boolean getSortCost() {
        return sortCost;
    }

    public boolean getSortDate() {
        return sortDate;
    }

    public Double getMinCost() {
        return minCost;
    }

    public Double getMaxCost() {
        return maxCost;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }
    
    public void setSelectedFilterType(String selectedFilterType) {
        this.selectedFilterType = selectedFilterType;
    }

    public void setSelectedFilterCategory(String selectedFilterCategory) {
        this.selectedFilterCategory = selectedFilterCategory;
    }

    public void setSortCost(boolean sortCost) {
        this.sortCost = sortCost;
    }

    public void setSortDate(boolean sortDate) {
        this.sortDate = sortDate;
    }

    public void setMinCost(Double minCost) {
        this.minCost = minCost;
    }

    public void setMaxCost(Double maxCost) {
        this.maxCost = maxCost;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

}
