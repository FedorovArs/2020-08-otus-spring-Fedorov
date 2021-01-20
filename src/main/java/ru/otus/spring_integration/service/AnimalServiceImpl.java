package ru.otus.spring_integration.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import ru.otus.spring_integration.model.animal.Animal;
import ru.otus.spring_integration.model.animal.AnimalClass;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AnimalServiceImpl implements AnimalService {

    private final MessageChannel p2pQueueChannel;
    private final PublishSubscribeChannel publishSubscribeChannel;

    @SneakyThrows
    @Override
    public Map<AnimalClass, Long> processAnimalsAndGetStatistics(List<String> animals) {

        List<Animal> newAnimals = new ArrayList<>();

        publishSubscribeChannel.subscribe(s -> System.out.printf("!!! Новый обитатель %s поставлен на довольствие \n", s.getPayload()));
        publishSubscribeChannel.subscribe(s -> System.out.printf("??? Новому обитателю %s определён вольер \n", s.getPayload()));
        publishSubscribeChannel.subscribe(s -> newAnimals.add((Animal) s.getPayload()));

        animals.forEach(name -> {
            p2pQueueChannel.send(
                    MessageBuilder.
                            withPayload(new Animal(name))
                            .build());
        });

        // Как избавиться от этого sleep? Как понять что все новые животные прошли обработку?
        TimeUnit.SECONDS.sleep(1);

        return newAnimals
                .stream()
                .collect(Collectors.groupingBy(Animal::getAnimalClass, Collectors.counting()));
    }
}
