package UI.utils;

import java.util.concurrent.ThreadLocalRandom;

public class TestDataGenerator {
    private static final String CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final String[] DOMAINS = {"gmail.com", "yahoo.com", "mail.ru", "yandex.ru"};

    public static String randomEmail() {
        String username = randomString(8);
        String domain = DOMAINS[ThreadLocalRandom.current().nextInt(DOMAINS.length)];
        return username + "@" + domain;
    }

    public static String randomPassword() {

        String upperCase = randomString(1).toUpperCase();
        String lowerCase = randomString(5);
        String numbers = String.valueOf(ThreadLocalRandom.current().nextInt(100, 999));
        return upperCase + lowerCase + numbers;
    }

    public static String randomString(int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(CHARS.charAt(ThreadLocalRandom.current().nextInt(CHARS.length())));
        }
        return sb.toString();
    }

    public static String randomFullName() {
        String[] firstNames = {"Иван", "Петр", "Алексей", "Сергей", "Дмитрий"};
        String[] lastNames = {"Иванов", "Петров", "Сидоров", "Смирнов", "Кузнецов"};
        return lastNames[ThreadLocalRandom.current().nextInt(lastNames.length)] + " " +
                firstNames[ThreadLocalRandom.current().nextInt(firstNames.length)] + " " +
                lastNames[ThreadLocalRandom.current().nextInt(lastNames.length)] + "ович";
    }

    public static int randomRating() {
        return ThreadLocalRandom.current().nextInt(1, 6);
    }

    public static String randomCardNumber() {
        // Генерация валидного тестового номера карты
        return "4" + randomNumericString(15); // Visa начинается с 4
    }

    public static String randomCardHolder() {
        return randomString(5).toUpperCase() + " " + randomString(7).toUpperCase();
    }

    public static String randomExpiryDate() {
        int currentYear = java.time.Year.now().getValue() % 100; // 2024 -> 24
        int year = currentYear + ThreadLocalRandom.current().nextInt(1, 5);
        int month = ThreadLocalRandom.current().nextInt(1, 13);
        return String.format("%02d/%02d", month, year);
    }

    public static String randomCvc() {
        return randomNumericString(3);
    }

    private static String randomNumericString(int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(ThreadLocalRandom.current().nextInt(10));
        }
        return sb.toString();
    }
}
