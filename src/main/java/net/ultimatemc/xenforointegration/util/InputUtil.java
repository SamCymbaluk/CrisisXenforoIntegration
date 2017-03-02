package net.ultimatemc.xenforointegration.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class InputUtil {
    private static final String EMAIL_REGEX = "/.+@.+/";

    public static String sanitizeInput(String str) {
        try {
            return URLEncoder.encode(str, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean isEmail(String str) {
        return true;
        //return EMAIL_REGEX.matches(str); Worry about this later
    }
}
