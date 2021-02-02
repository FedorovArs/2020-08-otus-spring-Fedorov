package ru.otus.spring_integration.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.messaging.MessageChannel;

@Configuration
public class ChannelConfig {

    @Bean("p2pQueueChannel")
    MessageChannel p2pQueueChannel() {
        return MessageChannels
                .queue(20)
                .get();
    }

    @Bean
    public PublishSubscribeChannel publishSubscribeChannel() {
        return MessageChannels
                .publishSubscribe()
                .minSubscribers(1)
                .get();
    }

    @Bean
    public PublishSubscribeChannel animalFilterDiscardChannel() {
        return MessageChannels
                .publishSubscribe()
                .minSubscribers(1)
                .get();
    }
}
