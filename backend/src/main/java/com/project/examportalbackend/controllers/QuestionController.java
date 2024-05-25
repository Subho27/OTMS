package com.project.examportalbackend.controllers;

import com.project.examportalbackend.models.Question;
import com.project.examportalbackend.models.Quiz;
import com.project.examportalbackend.services.QuestionService;
import com.project.examportalbackend.services.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@CrossOrigin
@RestController
@RequestMapping("/api/question")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private QuizService quizService;

    @PostMapping("/")
    public ResponseEntity<?> addQuestion(@RequestBody Question question) {
        try {
            return ResponseEntity.ok(questionService.addQuestion(question));
        } catch (Exception e) {
            return ResponseEntity.status(500).body("There is an Exception");
        }
    }

    @GetMapping("/")
    public ResponseEntity<?> getQuestions() {
        try {
            return ResponseEntity.ok(questionService.getQuestions());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("There is an Exception");
        }
    }

    @GetMapping("/{questionId}")
    public ResponseEntity<?> getQuestion(@PathVariable Long questionId) {
        try {
            return ResponseEntity.ok(questionService.getQuestion(questionId));
        } catch (Exception e) {
            return ResponseEntity.status(500).body("There is an Exception");
        }
    }

    @GetMapping(value = "/", params = "quizId")
    public ResponseEntity<?> getQuestionsByQuiz(@RequestParam Long quizId) {
        try {
            Quiz quiz = quizService.getQuiz(quizId);
            Set<Question> questions = quiz.getQuestions();
            return ResponseEntity.ok(questions);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("There is an Exception");
        }
    }

    @PutMapping("/{questionId}")
    public ResponseEntity<?> updateQuestion(@PathVariable Long questionId, @RequestBody Question question) {
        try {
            if (questionService.getQuestion(questionId) != null) {
                return ResponseEntity.ok(questionService.updateQuestion(question));
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Question with id : " + String.valueOf(questionId) + ", doesn't exists");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("There is an Exception");
        }
    }

    @DeleteMapping("/{questionId}")
    public ResponseEntity<?> deleteQuestion(@PathVariable Long questionId) {
        try {
            questionService.deleteQuestion(questionId);
            return ResponseEntity.ok(true);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("There is an Exception");
        }
    }
}
