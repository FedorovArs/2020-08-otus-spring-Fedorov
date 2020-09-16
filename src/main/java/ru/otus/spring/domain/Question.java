package ru.otus.spring.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Question {
    private QuestionType type;
    private String text;
    private List<String> answers;
    private String correctAnswer;

    public Question(QuestionType type, String text, List<String> answers, String correctAnswer) {
        this.type = type;
        this.text = text;
        this.answers = answers;
        this.correctAnswer = correctAnswer;
    }

    private String userAnswer;
}
