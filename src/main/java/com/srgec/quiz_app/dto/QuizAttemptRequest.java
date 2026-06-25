package com.srgec.quiz_app.dto;

import lombok.Data;
import java.util.Map;

@Data
public class QuizAttemptRequest {
    private Map<Integer, String> answers;
}