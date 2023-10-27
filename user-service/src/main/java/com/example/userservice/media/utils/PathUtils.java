package com.example.userservice.media.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.stream.Collectors;

public class PathUtils {
    private static final String DELIMITER = "/";

    public static String join(String... paths) {
        return Arrays.stream(paths).map(p -> StringUtils.strip(p, DELIMITER)).collect(Collectors.joining(DELIMITER));
    }
}
