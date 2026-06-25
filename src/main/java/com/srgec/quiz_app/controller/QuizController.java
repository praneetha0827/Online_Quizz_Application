package com.srgec.quiz_app.controller;

import com.srgec.quiz_app.dto.QuizAttemptRequest;
import com.srgec.quiz_app.models.Question;
import com.srgec.quiz_app.models.Quiz;
import com.srgec.quiz_app.service.QuizService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/quizzes")
public class QuizController {

    private final QuizService service;

    public QuizController(QuizService service) {
        this.service = service;
    }

    @PostMapping
    public Quiz createQuiz(@RequestBody Quiz quiz) {
        return service.createQuiz(quiz);
    }

    @GetMapping
    public List<Quiz> getAllQuizzes() {
        return service.getAllQuizzes();
    }

    @GetMapping("/{id}")
    public Quiz getQuizById(@PathVariable int id) {
        return service.getQuizById(id);
    }

    @GetMapping("/category/{category}")
    public List<Quiz> getByCategory(@PathVariable String category) {
        return service.getQuizzesByCategory(category);
    }

    @DeleteMapping("/{id}")
    public String deleteQuiz(@PathVariable int id) {
        return service.deleteQuiz(id);
    }

    @PostMapping("/{id}/questions")
    public Question addQuestion(@PathVariable int id, @RequestBody Question question) {
        return service.addQuestion(id, question);
    }

    @GetMapping("/{id}/questions")
    public List<Question> getQuestions(@PathVariable int id) {
        return service.getQuestionsByQuiz(id);
    }

    @PostMapping("/{id}/submit")
    public String submitQuiz(@PathVariable int id, @RequestBody QuizAttemptRequest request) {
        return service.submitQuiz(id, request.getAnswers());
    }
}