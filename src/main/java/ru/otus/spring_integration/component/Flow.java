package ru.otus.spring_integration.component;

import org.springframework.context.annotation.Bean;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.scheduling.PollerMetadata;
import org.springframework.stereotype.Component;

@Component
public class Flow {

    @Bean(name = PollerMetadata.DEFAULT_POLLER)
    public PollerMetadata poller() {
        return Pollers.fixedRate(100).maxMessagesPerPoll(2).get();
    }

    @Bean
    public IntegrationFlow animalFlow() {
        return IntegrationFlows.from("p2pQueueChannel")
                .handle("AnimalHelper", "setRandomAge")
                .handle("AnimalHelper", "setRandomAnimalClass")
                .handle("AnimalHelper", "setRandomMeetType")
                .channel("publishSubscribeChannel")
                .get();
    }
}
