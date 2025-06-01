package com.example.ExpenseTracker.Model;

import javax.persistence.*;
import lombok.*;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * User entity representing application users
 * Implements One-to-Many relationship with Expense
 */
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
@Table(name="app_user")
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
    @SequenceGenerator(name = "user_seq", sequenceName = "user_sequence", initialValue = 5, allocationSize = 1)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private boolean isAdmin = false;

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Expense> expenses;

    /**
     * Constructor for creating user with name and email
     * @param name User's name
     * @param email User's email address
     */
    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }

    /**
     * Constructor for creating user with name, email and admin status
     * @param name User's name
     * @param email User's email address
     * @param isAdmin Whether user has admin privileges
     */
    public User(String name, String email, boolean isAdmin) {
        this.name = name;
        this.email = email;
        this.isAdmin = isAdmin;
    }
}
