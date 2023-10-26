package com.everis.wishlist.utils;

import lombok.SneakyThrows;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

public class FileHelper {

    @SneakyThrows
    public static String load(String file) {
        return new BufferedReader(
                new InputStreamReader(FileHelper.class.getResourceAsStream(file), StandardCharsets.UTF_8))
                .lines()
                .collect(Collectors.joining("\n"));
    }
}
