package ru.otus.spring_integration.model.animal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Animal {
    private String name;
    private Integer age;
    private MeetType meetType;
    private AnimalClass animalClass;

    public Animal(String name) {
        this.name = name;
    }
}
