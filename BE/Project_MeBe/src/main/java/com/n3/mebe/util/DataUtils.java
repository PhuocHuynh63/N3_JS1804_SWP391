package com.n3.mebe.util;

import java.util.Random;

public class DataUtils {
    public static String generateTempPwd(int length) {
        String numbers = "012345678";
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
