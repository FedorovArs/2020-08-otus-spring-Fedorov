package ru.otus.spring_integration.service;

import ru.otus.spring_integration.model.animal.Animal;

public interface AnimalHelper {

    Animal setRandomAge(Animal animal);
    Animal setRandomAnimalClass(Animal animal);
    Animal setRandomMeetType(Animal animal);
}
