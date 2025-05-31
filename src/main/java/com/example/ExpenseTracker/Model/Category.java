package com.example.ExpenseTracker.Model;

import javax.persistence.*;
import lombok.*;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Category entity for organizing expenses into different types
 * Implements One-to-Many relationship with Expense
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name="category")
public class Category {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "category_seq")
    @SequenceGenerator(name = "category_seq", sequenceName = "category_sequence", initialValue = 4, allocationSize = 1)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @JsonIgnore
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Expense> expenses;

    /**
     * Constructor for creating category with name only
     * @param name Category name
     */
    public Category(String name) {
        this.name = name;
    }
}
