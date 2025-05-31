package com.example.ExpenseTracker.Model;

import javax.persistence.*;
import lombok.*;

/**
 * Abstract base class for all transaction types
 * Provides common fields and behavior for financial transactions
 */
@Entity
@Table(name = "transaction")
@Inheritance(strategy = InheritanceType.JOINED)
@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class Transaction {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "transaction_seq")
    @SequenceGenerator(name = "transaction_seq", sequenceName = "transaction_sequence", initialValue = 1, allocationSize = 1)
    private Long id;
    
    @Column(nullable = false)
    private String description;
    
    /**
     * Constructor for creating transaction with description
     * @param description Transaction description
     */
    public Transaction(String description) {
        this.description = description;
    }
}
