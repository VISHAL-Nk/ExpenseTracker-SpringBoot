package com.example.ExpenseTracker.Controller;

import com.example.ExpenseTracker.Model.Expense;
import com.example.ExpenseTracker.Model.User;
import com.example.ExpenseTracker.Model.Category;
import com.example.ExpenseTracker.Service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.*;

/**
 * REST Controller for Expense entity operations
 * Uses ExpenseService for business logic and monthly reporting
 * Demonstrates proper layered architecture and ReportService interface usage
 */
@RestController
@RequestMapping("/api")
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;

    /**
     * Get all expenses
     * @return List of all expenses
     */
    @GetMapping("/expenses")
    List<Expense> getExpenses() {
        return expenseService.getAllExpenses();
    }

    /**
     * Get expense by ID
     * @param id Expense ID
     * @return ResponseEntity with expense data or 404 if not found
     */
    @GetMapping("/expenses/{id}")
    ResponseEntity<Expense> getExpenseById(@PathVariable Long id) {
        try {
            Expense expense = expenseService.getExpenseById(id);
            return ResponseEntity.ok(expense);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Create new expense
     * @param expense Expense data
     * @return ResponseEntity with created expense
     * @throws URISyntaxException if URI creation fails
     */
    @PostMapping("/expenses")
    ResponseEntity<Expense> createExpense(@Valid @RequestBody Expense expense) throws URISyntaxException {
        Expense result = expenseService.createExpense(expense);
        return ResponseEntity.created(new URI("/api/expenses/" + result.getId())).body(result);
    }

    /**
     * Update existing expense
     * @param id Expense ID to update
     * @param expense Updated expense data
     * @return ResponseEntity with updated expense
     */
    @PutMapping("/expenses/{id}")
    ResponseEntity<Expense> updateExpense(@PathVariable Long id, @Valid @RequestBody Expense expense) {
        try {
            Expense result = expenseService.updateExpense(id, expense);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Delete expense by ID
     * @param id Expense ID to delete
     * @return ResponseEntity indicating success
     */
    @DeleteMapping("/expenses/{id}")
    ResponseEntity<?> deleteExpense(@PathVariable Long id){
        try {
            expenseService.deleteExpense(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Get expenses by user ID
     * @param userId User ID
     * @return List of user's expenses
     */
    @GetMapping("/expenses/user/{userId}")
    List<Expense> getExpensesByUserId(@PathVariable Long userId) {
        return expenseService.getExpensesByUserId(userId);
    }

    /**
     * Get expenses by category ID
     * @param categoryId Category ID
     * @return List of expenses in the category
     */
    @GetMapping("/expenses/category/{categoryId}")
    List<Expense> getExpensesByCategoryId(@PathVariable Long categoryId) {
        return expenseService.getExpensesByCategoryId(categoryId);
    }

    /**
     * Get monthly expense summary by category for a user
     * Implementation of ReportService interface functionality
     * @param userId User ID
     * @param year Year (e.g., 2024)
     * @param month Month (1-12)
     * @return Map of category names to total amounts
     */
    @GetMapping("/expenses/summary/{userId}/{year}/{month}")
    Map<String, Double> getMonthlySummary(@PathVariable Long userId, 
                                        @PathVariable int year, 
                                        @PathVariable int month) {
        YearMonth yearMonth = YearMonth.of(year, month);
        return expenseService.getMonthlySummary(userId, yearMonth);
    }

    /**
     * Get total monthly expenses for a user
     * Implementation of ReportService interface functionality
     * @param userId User ID
     * @param year Year (e.g., 2024)
     * @param month Month (1-12)
     * @return Total expense amount for the month
     */
    @GetMapping("/expenses/total/{userId}/{year}/{month}")
    Map<String, Double> getMonthlyTotal(@PathVariable Long userId, 
                                      @PathVariable int year, 
                                      @PathVariable int month) {
        YearMonth yearMonth = YearMonth.of(year, month);
        Double total = expenseService.getMonthlyTotal(userId, yearMonth);
        Map<String, Double> response = new HashMap<>();
        response.put("total", total);
        return response;
    }

    /**
     * Get monthly expenses for a user
     * @param userId User ID
     * @param year Year (e.g., 2024)
     * @param month Month (1-12)
     * @return List of expenses for the specified month
     */
    @GetMapping("/expenses/monthly/{userId}/{year}/{month}")
    List<Expense> getMonthlyExpenses(@PathVariable Long userId, 
                                   @PathVariable int year, 
                                   @PathVariable int month) {
        return expenseService.getMonthlyExpenses(userId, year, month);
    }

    /**
     * Create new expense with separate parameters (for testing)
     * @param description Expense description
     * @param amount Expense amount
     * @param date Expense date
     * @param location Expense location
     * @param userId User ID
     * @param categoryId Category ID
     * @return ResponseEntity with created expense
     */
    @PostMapping("/expenses/create")
    ResponseEntity<Expense> createExpenseWithParams(
            @RequestParam String description,
            @RequestParam Double amount,
            @RequestParam String date,
            @RequestParam String location,
            @RequestParam Long userId,
            @RequestParam Long categoryId) throws URISyntaxException {
        
        User user = new User();
        user.setId(userId);
        
        Category category = new Category();
        category.setId(categoryId);
        
        Expense expense = new Expense();
        expense.setDescription(description);
        expense.setAmount(amount);
        expense.setDate(LocalDate.parse(date));
        expense.setLocation(location);
        expense.setUser(user);
        expense.setCategory(category);
        
        Expense result = expenseService.createExpense(expense);
        return ResponseEntity.created(new URI("/api/expenses/" + result.getId())).body(result);
    }
}
