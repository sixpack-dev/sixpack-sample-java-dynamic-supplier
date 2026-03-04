package com.example;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

final class UtilsRandomData {

    private static final List<String> STREET_NAMES =
            List.of("Main St", "Oak Ave", "Maple Rd", "Sunset Blvd", "River Way");
    private static final List<String> CITY_NAMES =
            List.of("Prague", "Brno", "Paris", "Lyon", "Berlin");
    private static final List<String> COUNTRY_CODES = List.of("CZ", "FR", "DE");

    private UtilsRandomData() {
    }

    static String randomAddressLine() {
        int number = ThreadLocalRandom.current().nextInt(1, 400);
        return number + " " + pickRandom(STREET_NAMES);
    }

    static String randomCity() {
        return pickRandom(CITY_NAMES);
    }

    static String randomPostalCode() {
        return String.format("%05d", ThreadLocalRandom.current().nextInt(1000, 99999));
    }

    static String randomCountryCode() {
        return pickRandom(COUNTRY_CODES);
    }

    private static String pickRandom(List<String> values) {
        return values.get(ThreadLocalRandom.current().nextInt(values.size()));
    }
}
