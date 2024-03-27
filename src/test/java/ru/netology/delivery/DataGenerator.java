package ru.netology.delivery;

import lombok.Value;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Random;
import com.github.javafaker.Faker;

public class DataGenerator {
    private DataGenerator() {
    }

    public static String generationDate(int shift) {
        return LocalDate.now().plusDays(shift).format(DateTimeFormatter.ofPattern("dd.mm.yyyy"));

    }

    public static String generateCity() {
        var cities = new String[]{"Москва", "Казань", "Псков", "Санкт-Петербург", "Самара", "Волгоград"};
        return cities[new Random().nextInt(cities.length)];
    }

    public static String generationName(String locale) {
        var faker = new Faker(new Locale(locale));
        return faker.name().lastName() + " " + faker.name().firstName();
    }

    public static String generationPhone(String locale) {
        var faker = new Faker(new Locale(locale));
        return faker.phoneNumber().phoneNumber();
    }

    public static class Registration {
        private Registration() {

        }
        public static UserInfo generateUser(String locale) {
            return new UserInfo(generateCity(), generateName(locale), generatePhone(locale));
        }

    }

    @Value
    public static class UserInfo {
        String city;
        String name;
        String phone;
    }

}
