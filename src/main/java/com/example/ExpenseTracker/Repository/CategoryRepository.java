package com.example.ExpenseTracker.Repository;

import com.example.ExpenseTracker.Model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository  extends JpaRepository<Category, Long> {
    Category findByName(String name);
}

