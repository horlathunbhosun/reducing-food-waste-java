package tech.olatunbosun.wastemanagement.usermanagement.utility;

import java.util.Random;

public class TokenGenerator {

    public static String generateToken(int digits) {
        int minimum = (int) Math.pow(10, digits - 1); // This will give minimum number with 'digits' number of digits
        int maximum = (int) Math.pow(10, digits) - 1; // This will give maximum number with 'digits' number of digits
        Random random = new Random();
        int randomInt = minimum + random.nextInt(maximum);
        return String.valueOf(randomInt);
    }
}
