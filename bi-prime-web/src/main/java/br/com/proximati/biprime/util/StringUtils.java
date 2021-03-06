package br.com.proximati.biprime.util;

public class StringUtils {

    public static String capitalize(String s) {
        if (s.length() == 0) {
            return s;
        }
        return s.substring(0, 1).toUpperCase() + s.substring(1);
    }

    public static String decapitalize(String s) {
        if (s.length() == 0) {
            return s;
        }
        return s.substring(0, 1).toLowerCase() + s.substring(1);
    }
}
