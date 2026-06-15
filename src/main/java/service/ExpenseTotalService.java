package service;

import java.time.LocalDate;
import java.time.temporal.IsoFields;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import model.Expense;

public class ExpenseTotalService {

    // Total
    public double getTotalExpenses(List<Expense> visibleExpenses) {
        double total = 0;
        for (Expense expense : visibleExpenses) {
            total += expense.getItemCost();
        }
        return total;
    }

    // Daily Average
    public double getDailyAverage(List<Expense> visibleExpenses) {
        double total = 0;
        Set<LocalDate> dates = new HashSet<>();
        for (Expense expense : visibleExpenses) {
            total += expense.getItemCost();
            dates.add(expense.getDate());
        }
        int numOfDays = dates.size();
        if (numOfDays == 0) return 0;

        return total / numOfDays;
    }

    // Weekly Average
    public double getWeeklyAverage(List<Expense> visibleExpenses) {
        double total = 0;
        Set<String> weeks = new HashSet<>();
        for (Expense expense : visibleExpenses) {
            total += expense.getItemCost();
            LocalDate date = expense.getDate();

            int year = date.get(IsoFields.WEEK_BASED_YEAR);
            int week = date.get(java.time.temporal.IsoFields.WEEK_OF_WEEK_BASED_YEAR);

            weeks.add(year + "-W" + week);
        }
        int numOfWeeks = weeks.size();
        if (numOfWeeks == 0) return 0;
        
        return total / numOfWeeks;
    }

    // Monthly Average
    public double getMonthlyAverage(List<Expense> visibleExpenses) {
        double total = 0;
        Set<String> months = new HashSet<>();
        for (Expense expense : visibleExpenses) {
            total += expense.getItemCost();
            LocalDate date = expense.getDate();

            int year = date.getYear();
            int month = date.getMonthValue();

            months.add(year + "-M" + month);
        }
        int numOfMonths = months.size();
        if (numOfMonths == 0) return 0;

        return total / numOfMonths;
    }

    // Yearly Average
    public double getYearlyAverage(List<Expense> visibleExpenses) {
        double total = 0;
        Set<String> years = new HashSet<>();
        for (Expense expense : visibleExpenses) {
            total += expense.getItemCost();
            LocalDate date = expense.getDate();

            int year = date.getYear();

            years.add(String.valueOf(year));
        }
        int numOfYears = years.size();
        if (numOfYears == 0) return 0;

        return total / numOfYears;
    }

}
