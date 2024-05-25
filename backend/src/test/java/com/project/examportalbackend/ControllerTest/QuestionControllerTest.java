package com.project.examportalbackend.ControllerTest;

import com.project.examportalbackend.controllers.QuestionController;
import com.project.examportalbackend.models.Question;
import com.project.examportalbackend.models.Quiz;
import com.project.examportalbackend.services.QuestionService;
import com.project.examportalbackend.services.QuizService;
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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class QuestionControllerTest {

    @Autowired
    private QuestionController questionController;

    @MockBean
    private QuestionService questionService;

    @MockBean
    private QuizService quizService;

    @Test
    public void testAddQuestion_Success() {
        Question question = new Question();
        question.setContent("This is a test question");
        question.setImage(null);
        question.setOption1("Option 1");
        question.setOption2("Option 2");
        question.setOption3("Option 3");
        question.setOption4("Option 4");
        question.setAnswer("Option 1");
        Mockito.when(questionService.addQuestion(question)).thenReturn(question);

        ResponseEntity<?> response = questionController.addQuestion(question);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(question, response.getBody());
    }

    @Test
    public void testGetQuestions_Success() {
        List<Question> questions = new ArrayList<>();
        Question question1 = new Question();
        question1.setContent("This is a test question1");
        question1.setImage(null);
        question1.setOption1("Option 1");
        question1.setOption2("Option 2");
        question1.setOption3("Option 3");
        question1.setOption4("Option 4");
        question1.setAnswer("Option 1");
        Question question2 = new Question();
        question2.setContent("This is a test question2");
        question2.setImage(null);
        question2.setOption1("Option 1");
        question2.setOption2("Option 2");
        question2.setOption3("Option 3");
        question2.setOption4("Option 4");
        question2.setAnswer("Option 3");
        questions.add(question1);
        questions.add(question2);
        Mockito.when(questionService.getQuestions()).thenReturn(questions);

        ResponseEntity<?> response = questionController.getQuestions();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody() instanceof List);
        List<Question> responseQuestions = (List<Question>) response.getBody();
        assertEquals(questions.size(), responseQuestions.size());
    }

    @Test
    public void testGetQuestionById_Success() {
        Long questionId = 1L;
        Question question = new Question();
        question.setContent("This is a test question");
        question.setImage(null);
        question.setOption1("Option 1");
        question.setOption2("Option 2");
        question.setOption3("Option 3");
        question.setOption4("Option 4");
        question.setAnswer("Option 1");
        Mockito.when(questionService.getQuestion(questionId)).thenReturn(question);

        ResponseEntity<?> response = questionController.getQuestion(questionId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(question, response.getBody());
    }

    @Test
    public void testGetQuestionById_NotFound() {
        Long questionId = 1L;
        Mockito.when(questionService.getQuestion(questionId)).thenReturn(null);

        ResponseEntity<?> response = questionController.getQuestion(questionId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    public void testGetQuestionsByQuiz_Success() {
        Long quizId = 1L;
        Quiz quiz = new Quiz();
        quiz.setTitle("Test Quiz");
        quiz.setDescription("...");
        quiz.setIActive(true);
        quiz.setNumOfQuestions(2);
        Set<Question> questions = new HashSet<>();
        Question question1 = new Question();
        question1.setContent("This is a test question1");
        question1.setImage(null);
        question1.setOption1("Option 1");
        question1.setOption2("Option 2");
        question1.setOption3("Option 3");
        question1.setOption4("Option 4");
        question1.setAnswer("Option 1");
        Question question2 = new Question();
        question2.setContent("This is a test question2");
        question2.setImage(null);
        question2.setOption1("Option 1");
        question2.setOption2("Option 2");
        question2.setOption3("Option 3");
        question2.setOption4("Option 4");
        question2.setAnswer("Option 3");
        questions.add(question1);
        questions.add(question2);
        quiz.setQuestions(questions);
        Mockito.when(quizService.getQuiz(quizId)).thenReturn(quiz);

        ResponseEntity<?> response = questionController.getQuestionsByQuiz(quizId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody() instanceof Set);
        Set<Question> responseQuestions = (Set<Question>) response.getBody();
        assertEquals(questions.size(), responseQuestions.size());
    }
}