package com.rore.demo.utils;

import org.springframework.stereotype.Component;

import java.util.Random;

import static java.lang.System.currentTimeMillis;

@Component
public class UniqueIdGenerator {

    public String getUniqueId() {
        String str = "123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder uniqueString = new StringBuilder(String.valueOf(currentTimeMillis()));

        Random random = new Random();

        while (uniqueString.length() < 20) {
            uniqueString.append(str.charAt(random.nextInt(str.length() - 1)));
        }
        return uniqueString.toString();
    }

}