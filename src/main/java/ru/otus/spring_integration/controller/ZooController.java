package ru.otus.spring_integration.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.spring_integration.model.animal.AnimalClass;
import ru.otus.spring_integration.service.AnimalServiceImpl;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class ZooController {

    private final AnimalServiceImpl animalService;

    @PostMapping(value = "/zoo/addRandomAnimals")
    public Map<AnimalClass, Long> addRandomAnimals(@RequestBody List<String> animalNames) {

        return animalService.processAnimalsAndGetStatistics(animalNames);
    }
}
