package ru.netology.web;

import com.github.javafaker.Faker;
import lombok.Value;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Random;

public class DataGenerator {
    private DataGenerator() {
    }

    public static String generateCity() {
        String[] city = new String[]{
                "Абакан", "Абакан", "Анадырь", "Архангельск", "Астрахань", "Барнаул", "Белгород", "Биробиджан", "Благовещенск", "Брянск",
                "Владивосток", "Владимир", "Волгоград", "Вологда", "Воронеж", "Горно-Алтайск", "Грозный", "Екатеринбург", "Иваново",
                "Иркутск", "Йошкар-Ола", "Казань", "Калининград", "Калуга", "Кемерово", "Киров", "Кострома", "Краснодар", "Красноярск",
                "Курган", "Курск", "Липецк", "Магадан", "Майкоп", "Махачкала", "Москва", "Мурманск", "Нальчик", "Нарьян-Мар", "Нижний Новгород",
                "Новосибирск", "Омск", "Оренбург", "Орёл", "Пенза", "Петрозаводск", "Петропавловск-Камчатский", "Псков", "Ростов-на-Дону", "Рязань",
                "Самара", "Саратов", "Салехард", "Саранск", "Севастополь", "Симферополь", "Сыктывкар", "Смоленск", "Ставрополь", "Тамбов", "Тверь",
                "Томск", "Тула", "Тюмень", "Улан-Удэ", "Ульяновск", "Уфа", "Хабаровск", "Ханты-Мансийск", "Чебоксары", "Челябинск", "Черкесск", "Чита",
                "Южно-Сахалинск", "Якутск", "Ярославль", "Элиста"};
        return city[new Random().nextInt(city.length)];
    }

    public static String generateDate(int shift) {
        return LocalDate.now().plusDays(shift).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    public static String generateName(String locale) {
        Faker faker = new Faker(new Locale(locale));
        return faker.name().lastName() + " " + faker.name().firstName();
    }

    public static String generatePhone(String locale) {
        Faker faker = new Faker(new Locale(locale));
        return faker.phoneNumber().phoneNumber();
    }

    //Метод генерации неверного телефона из 5 цифр
    public static String generateInvalidPhone(String locale) {
        Faker faker = new Faker(new Locale(locale));
        return faker.numerify("#####");
    }

    public static class Registration {
        private Registration() {
        }

        public static UserInfo generateUser(String locale) {
            return new UserInfo(generateCity(), generateName(locale), generatePhone(locale));
        }

        @Value
        public static class UserInfo {
            String city;
            String name;
            String phone;
        }
    }


}


