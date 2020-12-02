package com.example.ExpenseTracker.Model;

import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import lombok.*;

@Entity
@NoArgsConstructor
@Data
@Table(name="category")
public class Category {
    @Id
    private Long id;

    private String name;



}
