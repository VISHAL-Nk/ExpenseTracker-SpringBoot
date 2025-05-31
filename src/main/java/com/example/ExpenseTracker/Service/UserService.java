package com.example.ExpenseTracker.Service;

import com.example.ExpenseTracker.Model.User;
import com.example.ExpenseTracker.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.persistence.EntityNotFoundException;
import java.util.List;

/**
 * Service layer for User entity operations
 * Implements business logic for user management
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    /**
     * Get all users
     * @return List of all users
     */
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Get user by ID
     * @param id User ID
     * @return User entity
     * @throws EntityNotFoundException if user not found
     */
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));
    }

    /**
     * Create new user
     * @param user User entity to create
     * @return Saved user entity
     */
    public User createUser(User user) {
        return userRepository.save(user);
    }

    /**
     * Update existing user
     * @param id User ID to update
     * @param userDetails Updated user details
     * @return Updated user entity
     * @throws EntityNotFoundException if user not found
     */
    public User updateUser(Long id, User userDetails) {
        User user = getUserById(id);
        user.setName(userDetails.getName());
        user.setEmail(userDetails.getEmail());
        return userRepository.save(user);
    }

    /**
     * Delete user by ID
     * @param id User ID to delete
     * @throws EntityNotFoundException if user not found
     */
    public void deleteUser(Long id) {
        User user = getUserById(id);
        userRepository.delete(user);
    }

    /**
     * Check if user exists by email
     * @param email Email to check
     * @return true if user exists, false otherwise
     */
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    /**
     * Find user by email and name for authentication
     * @param email User's email
     * @param name User's name
     * @return User entity if found, null otherwise
     */
    public User findByEmailAndName(String email, String name) {
        return userRepository.findByEmailAndName(email, name);
    }

    /**
     * Find user by email
     * @param email User's email
     * @return User entity if found, null otherwise
     */
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    /**
     * Check if user is admin
     * @param userId User ID to check
     * @return true if user is admin, false otherwise
     */
    public boolean isUserAdmin(Long userId) {
        try {
            User user = getUserById(userId);
            return user.isAdmin();
        } catch (EntityNotFoundException e) {
            return false;
        }
    }

    /**
     * Set admin privileges for user
     * @param userId User ID
     * @param isAdmin Admin status to set
     * @return Updated user
     */
    public User setAdminStatus(Long userId, boolean isAdmin) {
        User user = getUserById(userId);
        user.setAdmin(isAdmin);
        return userRepository.save(user);
    }
}
