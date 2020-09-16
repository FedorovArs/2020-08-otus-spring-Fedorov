package ru.otus.spring.service;

import org.springframework.stereotype.Service;
import ru.otus.spring.domain.Question;
import ru.otus.spring.domain.QuestionType;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class ParseServiceSimple implements ParseService {

    public static final int QUESTION_TYPE_INDEX = 0;
    public static final int QUESTION_TEXT_INDEX = 1;
    public static final int ANSWERS_TEXT_INDEX = 2;
    public static final int CORRECT_ANSWER_TEXT_INDEX = 3;

    @Override
    public List<String> parseCsvResource(InputStream resource) {
        List<String> result = new ArrayList<>();

        BufferedReader br = new BufferedReader(new InputStreamReader(resource, StandardCharsets.UTF_8));
        while (true) {
            String line;
            try {
                line = br.readLine();
            } catch (IOException e) {
                // add error info in log file
                continue;
            }

            if (line == null) {
                break;
            } else {
                result.add(line);
            }
        }

        return result;
    }

    @Override
    public Question parseQuestionRow(String[] row) {
        String type = row[QUESTION_TYPE_INDEX];
        String text = row[QUESTION_TEXT_INDEX];
        String correctAnswer = row[CORRECT_ANSWER_TEXT_INDEX];
        QuestionType questionType = QuestionType.valueOf(type.toUpperCase());

            List<String> answers = Arrays.asList(row[ANSWERS_TEXT_INDEX].split(","));
            return new Question(questionType, text, answers, correctAnswer);
    }
}
