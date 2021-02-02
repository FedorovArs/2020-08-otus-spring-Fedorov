package ru.otus.spring_integration.component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.ServiceActivator;
import ru.otus.spring_integration.model.animal.Animal;

@MessageEndpoint
@Slf4j
@RequiredArgsConstructor
public class AnimalMessageListener {

    @ServiceActivator(inputChannel = "animalFilterDiscardChannel")
    public void handleDiscardedAnimal(Animal animal) {
        log.error("Питомца, по имени \"" + animal.getName() + "\" не приняли в зоопарк !!!");
    }
}
