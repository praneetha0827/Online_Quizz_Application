package com.srgec.quiz_app.service;

import com.srgec.quiz_app.models.Question;
import com.srgec.quiz_app.models.Quiz;
import com.srgec.quiz_app.repository.QuestionRepository;
import com.srgec.quiz_app.repository.QuizRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

@Service
public class QuizService {

    private final QuizRepository quizRepository;
    private final QuestionRepository questionRepository;

    public QuizService(QuizRepository quizRepository, QuestionRepository questionRepository) {
        this.quizRepository = quizRepository;
        this.questionRepository = questionRepository;
    }

    public Quiz createQuiz(Quiz quiz) {
        return quizRepository.save(quiz);
    }

    public List<Quiz> getAllQuizzes() {
        return quizRepository.findAll();
    }

    public Quiz getQuizById(int id) {
        return quizRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Quiz not found with id: " + id));
    }

    public List<Quiz> getQuizzesByCategory(String category) {
        return quizRepository.findByCategory(category);
    }

    public String deleteQuiz(int id) {
        quizRepository.deleteById(id);
        return "Quiz deleted successfully";
    }

    public Question addQuestion(int quizId, Question question) {
        Quiz quiz = getQuizById(quizId);
        question.setQuiz(quiz);
        return questionRepository.save(question);
    }

    public List<Question> getQuestionsByQuiz(int quizId) {
        return questionRepository.findByQuizId(quizId);
    }

    public String submitQuiz(int quizId, Map<Integer, String> answers) {
        List<Question> questions = questionRepository.findByQuizId(quizId);

        int correct = 0;
        int total = questions.size();

        for (Question q : questions) {
            String userAnswer = answers.get(q.getId());
            if (userAnswer != null && userAnswer.equalsIgnoreCase(q.getCorrectAnswer())) {
                correct++;
            }
        }

        int percentage = (total > 0) ? (correct * 100 / total) : 0;
        return "Score: " + correct + "/" + total + " (" + percentage + "%)";
    }
}