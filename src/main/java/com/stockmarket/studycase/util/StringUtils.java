package com.stockmarket.studycase.util;

import java.util.Locale;

public class StringUtils {
    public static String toUpperCaseTurkish(String data){
        if(data==null){
            return null;
        }
        else{
            return data.toUpperCase(Locale.forLanguageTag("TR"));
        }
    }
}
