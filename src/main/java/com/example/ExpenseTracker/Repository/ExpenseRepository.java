package com.example.ExpenseTracker.Repository;

import com.example.ExpenseTracker.Model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

/**
 * Repository interface for Expense entity operations
 * Extends JpaRepository to provide basic CRUD operations
 * Includes custom query methods for expense filtering
 */
@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    
    /**
     * Find expenses by user ID and date range
     * @param userId User ID
     * @param startDate Start date (inclusive)
     * @param endDate End date (inclusive)
     * @return List of expenses within the date range for the user
     */
    List<Expense> findByUserIdAndDateBetween(Long userId, LocalDate startDate, LocalDate endDate);
    
    /**
     * Find all expenses for a specific user
     * @param userId User ID
     * @return List of user's expenses
     */
    List<Expense> findByUserId(Long userId);
    
    /**
     * Find all expenses for a specific category
     * @param categoryId Category ID
     * @return List of expenses in the category
     */
    List<Expense> findByCategoryId(Long categoryId);
}
