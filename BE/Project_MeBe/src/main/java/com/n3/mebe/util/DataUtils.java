package com.n3.mebe.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Random;

public class DataUtils {


    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static String encodeToBase64UrlSafe(Object obj) throws JsonProcessingException {
        String jsonString = objectMapper.writeValueAsString(obj);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(jsonString.getBytes(StandardCharsets.UTF_8));
    }

    public static <T> T decodeFromBase64UrlSafe(String encodedString, Class<T> clazz) throws JsonProcessingException, UnsupportedEncodingException {
        byte[] decodedBytes = Base64.getUrlDecoder().decode(encodedString);
        String jsonString = new String(decodedBytes, StandardCharsets.UTF_8);
        return objectMapper.readValue(jsonString, clazz);
    }


    public static String generateTempPwd(int length) {
        String numbers = "0123456789";
        char otp[] = new char[length];
        Random getOtpNum = new Random();
        for (int i = 0; i < length; i++) {
            otp[i] = numbers.charAt(getOtpNum.nextInt(numbers.length()));
        }
        String optCode = "";
        for (int i = 0; i < otp.length; i++) {
            optCode += otp[i];
        }
        return optCode;
    }

    public static String generateCode(int length) {
        String characters = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        char otp[] = new char[length];
        Random getOtpNum = new Random();
        for (int i = 0; i < length; i++) {
            otp[i] = characters.charAt(getOtpNum.nextInt(characters.length()));
        }
        String otpCode = new String(otp);
        return otpCode;
    }

}
