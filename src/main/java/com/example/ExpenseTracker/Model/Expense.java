package com.example.ExpenseTracker.Model;

import java.time.LocalDate;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.*;

/**
 * Expense entity representing a financial expense transaction
 * Extends Transaction to inherit common transaction properties
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@Table(name="expense")
public class Expense extends Transaction {

    @Column(nullable = false)
    private Double amount;

    @Column(nullable = false)
    private LocalDate date;

    private String location;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    /**
     * Constructor for creating expense with all required fields
     * @param description Expense description
     * @param amount Expense amount
     * @param date Expense date
     * @param location Expense location
     * @param category Expense category
     * @param user User who made the expense
     */
    public Expense(String description, Double amount, LocalDate date, String location, Category category, User user) {
        super(description);
        this.amount = amount;
        this.date = date;
        this.location = location;
        this.category = category;
        this.user = user;
    }
}
