package ru.otus.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;
import ru.otus.spring.config.SpringBootAppProps;
import ru.otus.spring.domain.Question;
import ru.otus.spring.service.DataProducerComponent;
import ru.otus.spring.service.TestServiceSimple;

import java.util.List;

@SpringBootApplication
@EnableConfigurationProperties(SpringBootAppProps.class)
public class Main {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(Main.class, args);
        DataProducerComponent questionService = context.getBean(DataProducerComponent.class);
        TestServiceSimple testService = context.getBean(TestServiceSimple.class);

        List<Question> questions = questionService.getQuestionsList();
        testService.runTest(questions);
    }
}
