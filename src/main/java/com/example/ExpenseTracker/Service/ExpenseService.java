package com.example.ExpenseTracker.Service;

import com.example.ExpenseTracker.Model.Expense;
import com.example.ExpenseTracker.Model.User;
import com.example.ExpenseTracker.Model.Category;
import com.example.ExpenseTracker.Repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Service layer for Expense entity operations
 * Implements ReportService interface for generating expense reports
 * Demonstrates Interface Implementation OOP principle
 */
@Service
public class ExpenseService implements ReportService {

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private CategoryService categoryService;

    /**
     * Get all expenses
     * @return List of all expenses
     */
    public List<Expense> getAllExpenses() {
        return expenseRepository.findAll();
    }

    /**
     * Get expense by ID
     * @param id Expense ID
     * @return Expense entity
     * @throws EntityNotFoundException if expense not found
     */
    public Expense getExpenseById(Long id) {
        return expenseRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Expense not found with id: " + id));
    }

    /**
     * Create new expense
     * @param expense Expense entity to create
     * @return Saved expense entity
     */
    public Expense createExpense(Expense expense) {
        // Validate and fetch user entity
        if (expense.getUser() != null && expense.getUser().getId() != null) {
            User user = userService.getUserById(expense.getUser().getId());
            expense.setUser(user);
        } else {
            throw new IllegalArgumentException("User is required for expense creation");
        }
        
        // Validate and fetch category entity
        if (expense.getCategory() != null && expense.getCategory().getId() != null) {
            Category category = categoryService.getCategoryById(expense.getCategory().getId());
            expense.setCategory(category);
        } else {
            throw new IllegalArgumentException("Category is required for expense creation");
        }
        
        return expenseRepository.save(expense);
    }

    /**
     * Update existing expense
     * @param id Expense ID to update
     * @param expenseDetails Updated expense details
     * @return Updated expense entity
     * @throws EntityNotFoundException if expense not found
     */
    public Expense updateExpense(Long id, Expense expenseDetails) {
        Expense expense = getExpenseById(id);
        expense.setDescription(expenseDetails.getDescription());
        expense.setAmount(expenseDetails.getAmount());
        expense.setDate(expenseDetails.getDate());
        expense.setLocation(expenseDetails.getLocation());
        
        if (expenseDetails.getCategory() != null && expenseDetails.getCategory().getId() != null) {
            Category category = categoryService.getCategoryById(expenseDetails.getCategory().getId());
            expense.setCategory(category);
        }
        
        return expenseRepository.save(expense);
    }

    /**
     * Delete expense by ID
     * @param id Expense ID to delete
     * @throws EntityNotFoundException if expense not found
     */
    public void deleteExpense(Long id) {
        Expense expense = getExpenseById(id);
        expenseRepository.delete(expense);
    }

    /**
     * Get expenses by user ID
     * @param userId User ID
     * @return List of user's expenses
     */
    public List<Expense> getExpensesByUserId(Long userId) {
        return expenseRepository.findByUserId(userId);
    }

    /**
     * Get expenses by category ID
     * @param categoryId Category ID
     * @return List of expenses in the category
     */
    public List<Expense> getExpensesByCategoryId(Long categoryId) {
        return expenseRepository.findByCategoryId(categoryId);
    }

    /**
     * Implementation of ReportService interface
     * Generate monthly expense summary by category for a specific user
     * @param userId The user ID to generate report for
     * @param month The year-month to generate summary for
     * @return Map of category names to total expense amounts
     */
    @Override
    public Map<String, Double> getMonthlySummary(Long userId, YearMonth month) {
        LocalDate startDate = month.atDay(1);
        LocalDate endDate = month.atEndOfMonth();
        
        List<Expense> monthlyExpenses = expenseRepository.findByUserIdAndDateBetween(userId, startDate, endDate);
        
        return monthlyExpenses.stream()
                .collect(Collectors.groupingBy(
                    expense -> expense.getCategory().getName(),
                    Collectors.summingDouble(Expense::getAmount)
                ));
    }

    /**
     * Implementation of ReportService interface
     * Generate total expenses for a user in a specific month
     * @param userId The user ID
     * @param month The year-month
     * @return Total expense amount
     */
    @Override
    public Double getMonthlyTotal(Long userId, YearMonth month) {
        LocalDate startDate = month.atDay(1);
        LocalDate endDate = month.atEndOfMonth();
        
        List<Expense> monthlyExpenses = expenseRepository.findByUserIdAndDateBetween(userId, startDate, endDate);
        
        return monthlyExpenses.stream()
                .mapToDouble(Expense::getAmount)
                .sum();
    }

    /**
     * Get expenses for a specific month and year
     * @param userId User ID
     * @param year Year
     * @param month Month (1-12)
     * @return List of expenses for the specified month
     */
    public List<Expense> getMonthlyExpenses(Long userId, int year, int month) {
        YearMonth yearMonth = YearMonth.of(year, month);
        LocalDate startDate = yearMonth.atDay(1);
        LocalDate endDate = yearMonth.atEndOfMonth();
        
        return expenseRepository.findByUserIdAndDateBetween(userId, startDate, endDate);
    }
}
