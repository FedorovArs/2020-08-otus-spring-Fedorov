package ru.otus.spring_integration.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.scheduling.PollerMetadata;
import ru.otus.spring_integration.service.AnimalHelper;

@Configuration
@RequiredArgsConstructor
public class FlowConfig {

    private final AnimalHelper animalHelper;

    @Bean(name = PollerMetadata.DEFAULT_POLLER)
    public PollerMetadata poller() {
        return Pollers.fixedRate(100).maxMessagesPerPoll(2).get();
    }

    @Bean
    public IntegrationFlow animalFlow() {
        return IntegrationFlows.from("p2pQueueChannel")
                .handle(animalHelper, "setRandomAge")
                .handle(animalHelper, "setRandomAnimalClass")
                .handle(animalHelper, "setRandomMeetType")
                .channel("publishSubscribeChannel")
                .get();
    }
}
