package com.example;

import dev.sixpack.api.data.Configuration;

final class UtilsInputValue {

    private UtilsInputValue() {
    }

    static String resolveString(Configuration input, String fieldName, String defaultValue) {
        if (!input.containsKey(fieldName)) {
            return defaultValue;
        }
        Object value = input.get(fieldName);
        if (value == null) {
            return defaultValue;
        }
        String stringValue = String.valueOf(value).trim();
        return stringValue.isEmpty() ? defaultValue : stringValue;
    }

    static Boolean resolveBoolean(Configuration input, String fieldName, boolean defaultValue) {
        if (!input.containsKey(fieldName)) {
            return defaultValue;
        }
        Object value = input.get(fieldName);
        if (value == null) {
            return defaultValue;
        }
        if (value instanceof Boolean booleanValue) {
            return booleanValue;
        }
        if (value instanceof String stringValue) {
            return Boolean.parseBoolean(stringValue);
        }
        throw new IllegalArgumentException(
                "Field '" + fieldName + "' must be Boolean or String convertible to Boolean.");
    }
}
