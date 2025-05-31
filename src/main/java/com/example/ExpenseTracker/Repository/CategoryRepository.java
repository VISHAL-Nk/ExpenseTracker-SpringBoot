package com.example.ExpenseTracker.Repository;

import com.example.ExpenseTracker.Model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for Category entity operations
 * Extends JpaRepository to provide basic CRUD operations
 */
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    
    /**
     * Find category by name
     * @param name Category name
     * @return Category entity if found
     */
    Category findByName(String name);
    
    /**
     * Check if category exists by name
     * @param name Category name to check
     * @return true if category exists, false otherwise
     */
    boolean existsByName(String name);
}

