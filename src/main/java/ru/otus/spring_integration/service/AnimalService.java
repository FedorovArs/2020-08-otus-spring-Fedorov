package ru.otus.spring_integration.service;

import ru.otus.spring_integration.model.animal.AnimalClass;

import java.util.List;
import java.util.Map;

public interface AnimalService {

    Map<AnimalClass, Long> processAnimalsAndGetStatistics(List<String> animals);
}
