package com.example.ExpenseTracker.Controller;

import com.example.ExpenseTracker.Model.Category;
import com.example.ExpenseTracker.Repository.CategoryRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class CategoryController {
    private CategoryRepository categoryRepository;

    public CategoryController(CategoryRepository categoryRepository){
        super();
        this.categoryRepository = categoryRepository;
    }

    @GetMapping("/categories")
    Collection<Category>categories(){
        return categoryRepository.findAll();
        // select * from category
    }

    @GetMapping("/category/{id}")
    ResponseEntity<?> getCategory(@PathVariable Long id){
        Optional<Category> category = categoryRepository.findById(id);
        return category.map(response -> ResponseEntity.ok().body(response))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }
    @PostMapping("/category")
    ResponseEntity<Category>createCategory(@Valid @RequestBody Category category) throws URISyntaxException {
        Category result = categoryRepository.save(category);
        // insert into table...
        return ResponseEntity.created(new URI("/api/category" + result.getId())).body(result);
    }
    @PutMapping("/category/{id}")
    ResponseEntity<Category> updateCategory(@Valid @RequestBody Category category) {
        Category result = categoryRepository.save(category);
        return ResponseEntity.ok().body(result);
    }
    @DeleteMapping("/category/{id}")
    Map<String,Boolean> deleteCategory(@PathVariable(value = "id") Long id){
        categoryRepository.deleteById(id);
        Map<String,Boolean>response = new HashMap<>();
        response.put("deleted",Boolean.TRUE);
        return response;
    }

}
