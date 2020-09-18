import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(value = "ru.otus")

// Не смог избавиться от этого класса, иначе контекст не поднимается
public class TestConfig {

}
