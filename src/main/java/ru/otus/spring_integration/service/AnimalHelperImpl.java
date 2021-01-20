package ru.otus.spring_integration.service;

import org.springframework.stereotype.Service;
import ru.otus.spring_integration.model.animal.Animal;
import ru.otus.spring_integration.model.animal.AnimalClass;
import ru.otus.spring_integration.model.animal.MeetType;

import java.util.Random;

// Если не задать имя Servic'y тогда мы получаем ошибку, но я не понял почему? Пробовал во Flow подставлять имена AnimalHelper или AnimalHelperImpl
// Получаю ошибку вида: No bean named 'AnimalHelperImpl' available
@Service("AnimalHelper")
public class AnimalHelperImpl implements AnimalHelper {

    public Animal setRandomAge(Animal animal) {
        Random random = new Random();
        final int randomAge = random.nextInt(50);
        animal.setAge(randomAge);

        System.out.println("Врачём произведен первичный осмотр и определен возраст нового обитателя! (" + randomAge + " лет)");
        return animal;
    }

    public Animal setRandomAnimalClass(Animal animal) {
        Random random = new Random();
        final AnimalClass randomAnimalClass = AnimalClass.values()[random.nextInt(4)];
        animal.setAnimalClass(randomAnimalClass);

        System.out.println("Врач определил класс нового обитателя! " + randomAnimalClass);
        return animal;
    }

    public Animal setRandomMeetType(Animal animal) {
        Random random = new Random();
        final MeetType randomMeetType = MeetType.values()[random.nextInt(3)];
        animal.setMeetType(randomMeetType);

        System.out.println("Врач определил тип питания нового обитателя! " + randomMeetType);
        return animal;
    }
}
