package com.example.ExpenseTracker.Service;

import com.example.ExpenseTracker.Model.Category;
import com.example.ExpenseTracker.Model.User;
import com.example.ExpenseTracker.Repository.CategoryRepository;
import com.example.ExpenseTracker.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.persistence.EntityNotFoundException;
import java.util.List;

/**
 * Service layer for Category entity operations
 * Implements business logic for category management
 */
@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;
    
    @Autowired
    private UserRepository userRepository;

    /**
     * Get all categories
     * @return List of all categories
     */
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    /**
     * Get category by ID
     * @param id Category ID
     * @return Category entity
     * @throws EntityNotFoundException if category not found
     */
    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Category not found with id: " + id));
    }

    /**
     * Create new category
     * @param category Category entity to create
     * @return Saved category entity
     */
    public Category createCategory(Category category) {
        return categoryRepository.save(category);
    }

    /**
     * Update existing category
     * @param id Category ID to update
     * @param categoryDetails Updated category details
     * @return Updated category entity
     * @throws EntityNotFoundException if category not found
     */
    public Category updateCategory(Long id, Category categoryDetails) {
        Category category = getCategoryById(id);
        category.setName(categoryDetails.getName());
        return categoryRepository.save(category);
    }

    /**
     * Delete category by ID
     * @param id Category ID to delete
     * @throws EntityNotFoundException if category not found
     */
    public void deleteCategory(Long id) {
        Category category = getCategoryById(id);
        categoryRepository.delete(category);
    }

    /**
     * Check if category exists by name
     * @param name Category name to check
     * @return true if category exists, false otherwise
     */
    public boolean existsByName(String name) {
        return categoryRepository.existsByName(name);
    }

    /**
     * Delete category with permission check
     * @param id Category ID to delete
     * @param userId User requesting the deletion
     * @param userService UserService to check permissions
     * @param allowUserDeletion Whether regular users can delete categories
     * @throws SecurityException if user doesn't have permission
     * @throws EntityNotFoundException if category not found
     */
    public void deleteCategoryWithPermission(Long id, Long userId, 
                                           com.example.ExpenseTracker.Service.UserService userService, 
                                           boolean allowUserDeletion) {
        // Check if user has permission
        boolean isAdmin = userService.isUserAdmin(userId);
        
        if (!isAdmin && !allowUserDeletion) {
            throw new SecurityException("Only administrators can delete categories");
        }
        
        deleteCategory(id);
    }

    /**
     * Delete category with permission check using email
     * @param id Category ID to delete
     * @param userEmail Email of user requesting the deletion
     * @return true if deleted, false if permission denied
     */
    public boolean deleteCategoryWithPermission(Long id, String userEmail) {
        try {
            // Find user by email
            User user = userRepository.findByEmail(userEmail);
            
            if (user == null) {
                throw new RuntimeException("User not found");
            }
            
            // Check if user is admin
            if (!user.isAdmin()) {
                return false; // Permission denied
            }
            
            deleteCategory(id);
            return true;
        } catch (Exception e) {
            throw new RuntimeException("Error deleting category: " + e.getMessage());
        }
    }
}
