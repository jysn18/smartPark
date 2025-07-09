package com.example.project.smartpark.Util;

import java.util.regex.Pattern;

public class ValidationUtil {

    private static final Pattern LICENSE_PLATE_PATTERN = Pattern.compile("^[A-Z0-9\\-]+$");

    public boolean isValidLicensePlate(String plate) {
        return LICENSE_PLATE_PATTERN.matcher(plate).matches();
    }
}
