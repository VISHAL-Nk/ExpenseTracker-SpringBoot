package com.example.ExpenseTracker.Repository;

import com.example.ExpenseTracker.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for User entity operations
 * Extends JpaRepository to provide basic CRUD operations
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    /**
     * Check if user exists by email
     * @param email Email to check
     * @return true if user exists, false otherwise
     */
    boolean existsByEmail(String email);
    
    /**
     * Find user by email address
     * @param email Email to search for
     * @return User entity if found
     */
    User findByEmail(String email);
    
    /**
     * Find user by email and name (for basic authentication)
     * @param email Email to search for
     * @param name Name to search for
     * @return User entity if found
     */
    User findByEmailAndName(String email, String name);
}
