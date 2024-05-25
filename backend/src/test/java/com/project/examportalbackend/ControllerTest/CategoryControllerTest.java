package com.project.examportalbackend.ControllerTest;

import com.project.examportalbackend.controllers.CategoryController;
import com.project.examportalbackend.models.Category;
import com.project.examportalbackend.services.CategoryService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CategoryControllerTest {

    @Autowired
    private CategoryController categoryController;

    @MockBean
    private CategoryService categoryService;

    @Test
    public void testAddCategory_Success() {
        Category category = new Category();
        category.setTitle("Test Category");
        category.setDescription("This is a test category");

        Mockito.when(categoryService.addCategory(category)).thenReturn(category);

        ResponseEntity<?> response = categoryController.addCategory(category);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(category, response.getBody());
    }

    @Test
    public void testGetCategories_Success() {
        List<Category> categories = new ArrayList<>();
        Category category1 = new Category();
        category1.setTitle("Category 1");
        category1.setDescription("Description 1");
        Category category2 = new Category();
        category2.setTitle("Category 2");
        category2.setDescription("Description 2");
        categories.add(category1);
        categories.add(category2);

        Mockito.when(categoryService.getCategories()).thenReturn(categories);

        ResponseEntity<?> response = categoryController.getCategories();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody() instanceof List);
        List<Category> responseCategories = (List<Category>) response.getBody();
        assertEquals(categories.size(), responseCategories.size());
    }

    @Test
    public void testGetCategoryById_Success() {
        Long categoryId = 1L;
        Category category = new Category();
        category.setTitle("Category 1");
        category.setDescription("Description 1");

        Mockito.when(categoryService.getCategory(categoryId)).thenReturn(category);

        ResponseEntity<?> response = categoryController.getCategory(categoryId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(category, response.getBody());
    }

    @Test
    public void testGetCategoryById_NotFound() {
        Long categoryId = 1L;

        Mockito.when(categoryService.getCategory(categoryId)).thenReturn(null);

        ResponseEntity<?> response = categoryController.getCategory(categoryId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    public void testUpdateCategory_Success() {
        Long categoryId = 1L;
        Category existingCategory = new Category();
        existingCategory.setTitle("Old Category");
        existingCategory.setDescription("Old Description");
        Category updatedCategory = new Category();
        updatedCategory.setTitle("Updated Category");
        updatedCategory.setDescription("Updated Description");
        Mockito.when(categoryService.getCategory(categoryId)).thenReturn(existingCategory);
        Mockito.when(categoryService.updateCategory(updatedCategory)).thenReturn(updatedCategory);

        ResponseEntity<?> response = categoryController.updateCategory(categoryId, updatedCategory);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedCategory, response.getBody());
    }

    @Test
    public void testUpdateCategory_NotFound() {
        Long categoryId = 1L;
        Category updatedCategory = new Category();
        updatedCategory.setTitle("Updated Category");
        updatedCategory.setDescription("Updated Description");
        when(categoryService.getCategory(categoryId)).thenReturn(null);

        ResponseEntity<?> response = categoryController.updateCategory(categoryId, updatedCategory);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.getBody().toString().contains("Category with id"));
    }
}
