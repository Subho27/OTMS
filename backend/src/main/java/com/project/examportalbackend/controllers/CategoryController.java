package com.project.examportalbackend.controllers;

import com.project.examportalbackend.models.Category;
import com.project.examportalbackend.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/category")

public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/")
    public ResponseEntity<?> addCategory(@RequestBody Category category) {
        try{
            return ResponseEntity.ok(categoryService.addCategory(category));
        } catch (Exception e) {
            return ResponseEntity.status(500).body("There is an Exception");
        }
    }

    @GetMapping("/")
    public ResponseEntity<?> getCategories() {
        try {
            return ResponseEntity.ok(categoryService.getCategories());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("There is an Exception");
        }
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<?> getCategory(@PathVariable Long categoryId) {
        try {
            return ResponseEntity.ok(categoryService.getCategory(categoryId));
        } catch (Exception e) {
            return ResponseEntity.status(500).body("There is an Exception");
        }
    }

    @PutMapping("/{categoryId}")
    public ResponseEntity<?> updateCategory(@PathVariable Long categoryId, @RequestBody Category category) {
        try {
            if (categoryService.getCategory(categoryId) != null) {
                return ResponseEntity.ok(categoryService.updateCategory(category));
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Category with id : " + String.valueOf(categoryId) + ", doesn't exists");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("There is an Exception");
        }
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<?> deleteCategory(@PathVariable Long categoryId) {
        try {
            categoryService.deleteCategory(categoryId);
            return ResponseEntity.ok(true);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("There is an Exception");
        }
    }
}
