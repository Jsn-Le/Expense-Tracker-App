package controller;

import service.ExpenseService;
import service.ExpenseFilterService;
import service.ExpenseTotalService
import model.ExpenseFilter;

public class ExpenseController {

    private final ExpenseService expenseService;
    private final ExpenseFilterService expenseFilterService;
    private final ExpenseTotalService expenseTotalService;
    private final ExpenseFilter filter;

    public ExpenseController(ExpenseService expenseService, ExpenseFilterService expenseFilterService, 
                            ExpenseTotalService expenseTotalService, ExpenseFilter filter) {
        this.expenseService = expenseService;
    }

}
