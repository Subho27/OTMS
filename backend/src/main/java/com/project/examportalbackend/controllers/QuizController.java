package com.project.examportalbackend.controllers;

import com.project.examportalbackend.models.Category;
import com.project.examportalbackend.models.Quiz;
import com.project.examportalbackend.services.CategoryService;
import com.project.examportalbackend.services.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/quiz")
public class QuizController {

    @Autowired
    private QuizService quizService;

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/")
    public ResponseEntity<?> addQuiz(@RequestBody Quiz quiz) {
        try {
            return ResponseEntity.ok(quizService.addQuiz(quiz));
        } catch (Exception e) {
            return ResponseEntity.status(500).body("There is an Exception");
        }
    }

    @GetMapping("/")
    public ResponseEntity<?> getQuizzes() {
        try {
            return ResponseEntity.ok(quizService.getQuizzes());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("There is an Exception");
        }
    }

    @GetMapping("/{quizId}")
    public ResponseEntity<?> getQuiz(@PathVariable Long quizId) {
        try {
            return ResponseEntity.ok(quizService.getQuiz(quizId));
        } catch (Exception e) {
            return ResponseEntity.status(500).body("There is an Exception");
        }
    }

    @GetMapping(value = "/", params = "catId")
    public ResponseEntity<?> getQuizByCategory(@RequestParam Long catId) {
        try {
            Category category = categoryService.getCategory(catId);
            return ResponseEntity.ok(quizService.getQuizByCategory(category));
        } catch (Exception e) {
            return ResponseEntity.status(500).body("There is an Exception");
        }
    }

    @PutMapping("/{quizId}")
    public ResponseEntity<?> updateQuiz(@PathVariable Long quizId, @RequestBody Quiz quiz) {
        try {
            if (quizService.getQuiz(quizId) != null) {
                return ResponseEntity.ok(quizService.updateQuiz(quiz));
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Quiz with id : " + String.valueOf(quizId) + ", doesn't exists");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("There is an Exception");
        }
    }

    @DeleteMapping("/{quizId}")
    public ResponseEntity<?> deleteQuiz(@PathVariable Long quizId) {
        try {
            quizService.deleteQuiz(quizId);
            return ResponseEntity.ok(true);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("There is an Exception");
        }
    }
}
