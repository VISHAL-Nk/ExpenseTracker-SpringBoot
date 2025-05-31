package com.example.ExpenseTracker.Controller;

import com.example.ExpenseTracker.Model.Category;
import com.example.ExpenseTracker.Service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * REST Controller for Category entity operations
 * Uses CategoryService for business logic
 * Demonstrates proper layered architecture with Controller -> Service -> Repository
 */
@RestController
@RequestMapping("/api")
public class CategoryController {
    
    @Autowired
    private CategoryService categoryService;

    /**
     * Get all categories
     * @return Collection of all categories
     */
    @GetMapping("/categories")
    Collection<Category> categories(){
        return categoryService.getAllCategories();
    }

    /**
     * Get category by ID
     * @param id Category ID
     * @return ResponseEntity with category data or 404 if not found
     */
    @GetMapping("/categories/{id}")
    ResponseEntity<Category> getCategory(@PathVariable Long id){
        try {
            Category category = categoryService.getCategoryById(id);
            return ResponseEntity.ok(category);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    /**
     * Create new category
     * @param category Category data
     * @return ResponseEntity with created category
     * @throws URISyntaxException if URI creation fails
     */
    @PostMapping("/categories")
    ResponseEntity<Category> createCategory(@Valid @RequestBody Category category) throws URISyntaxException {
        Category result = categoryService.createCategory(category);
        return ResponseEntity.created(new URI("/api/categories/" + result.getId())).body(result);
    }
    
    /**
     * Update existing category
     * @param id Category ID to update
     * @param category Updated category data
     * @return ResponseEntity with updated category
     */
    @PutMapping("/categories/{id}")
    ResponseEntity<Category> updateCategory(@PathVariable Long id, @Valid @RequestBody Category category) {
        try {
            Category result = categoryService.updateCategory(id, category);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    /**
     * Delete category by ID with user permission check
     * @param id Category ID to delete
     * @param userEmail Email of user requesting deletion
     * @return Map indicating deletion status and message
     */
    @DeleteMapping("/categories/{id}")
    Map<String,Object> deleteCategory(@PathVariable(value = "id") Long id, @RequestParam String userEmail){
        Map<String,Object> response = new HashMap<>();
        try {
            boolean deleted = categoryService.deleteCategoryWithPermission(id, userEmail);
            response.put("deleted", deleted);
            if (deleted) {
                response.put("message", "Category deleted successfully");
            } else {
                response.put("message", "Access denied. Only admins can delete categories.");
            }
            return response;
        } catch (Exception e) {
            response.put("deleted", Boolean.FALSE);
            response.put("message", "Error deleting category: " + e.getMessage());
            return response;
        }
    }

    /**
     * Check if category name exists
     * @param name Category name to check
     * @return Map with existence status
     */
    @GetMapping("/categories/exists/name/{name}")
    Map<String, Boolean> checkCategoryNameExists(@PathVariable String name) {
        Map<String, Boolean> response = new HashMap<>();
        response.put("exists", categoryService.existsByName(name));
        return response;
    }

}
