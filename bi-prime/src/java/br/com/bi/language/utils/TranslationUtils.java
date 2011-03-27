/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.bi.language.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author luiz
 */
public class TranslationUtils {

    public static String extractName(String expression) {
        String patternStr = "\\[(.*)\\]";

        Pattern pattern = Pattern.compile(patternStr);
        Matcher matcher = pattern.matcher(expression);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }
}
