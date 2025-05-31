package com.example.ExpenseTracker.Service;

import java.time.YearMonth;
import java.util.Map;

/**
 * Interface for generating expense reports and summaries
 * Implements the Strategy pattern for different report types
 */
public interface ReportService {
    
    /**
     * Generate monthly expense summary by category for a specific user
     * @param userId The user ID to generate report for
     * @param month The year-month to generate summary for
     * @return Map of category names to total expense amounts
     */
    Map<String, Double> getMonthlySummary(Long userId, YearMonth month);
    
    /**
     * Generate total expenses for a user in a specific month
     * @param userId The user ID
     * @param month The year-month
     * @return Total expense amount
     */
    Double getMonthlyTotal(Long userId, YearMonth month);
}
