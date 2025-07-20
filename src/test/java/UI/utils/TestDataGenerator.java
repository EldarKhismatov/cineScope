package UI.utils;

import java.time.Year;
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
        return randomString(1).toUpperCase() + randomString(5) + randomInt(100, 999);
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
        return "4" + randomNumericString(15);
    }

    public static String randomCardHolder() {
        return randomString(5).toUpperCase() + " " + randomString(7).toUpperCase();
    }

    public static String randomExpiryDate() {
        int month = randomInt(1, 13);
        int year = Year.now().getValue() % 100 + randomInt(1, 5);
        return String.format("%02d/%02d", month, year);
    }

    public static String randomCvc() {
        return randomNumericString(3);
    }

    private static int randomInt(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max);
    }

    public static String randomString(int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++)
            sb.append(CHARS.charAt(randomInt(0, CHARS.length())));
        return sb.toString();
    }

    public static String randomNumericString(int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++)
            sb.append(randomInt(0, 10));
        return sb.toString();
    }
}
