package ru.otus.spring_integration.component;

import org.springframework.integration.annotation.Filter;
import org.springframework.integration.annotation.MessageEndpoint;
import ru.otus.spring_integration.model.animal.Animal;

import java.util.HashSet;
import java.util.Set;

@MessageEndpoint
public class AnimalFilter {

    public static final Set<String> parasites = new HashSet<>() {{
        add("саранча");
    }};

    @Filter(inputChannel = "p2pQueueChannel", outputChannel = "p2pQueueChannel", discardChannel = "animalFilterDiscardChannel")
    public boolean parasitesFilter(Animal animal) {
        // Тут планировалась проверка по типу животного, но я так и не разобрался, как заставить работать фильтр после
        // метода setRandomAnimalClass, он всегда отрабатывает до него.
        return !parasites.contains(animal.getName().toLowerCase());
    }
}
