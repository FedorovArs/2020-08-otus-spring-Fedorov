package ru.otus.spring_integration.component;

import org.springframework.context.annotation.Bean;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Component;

@Component
public class Channel {

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
}
