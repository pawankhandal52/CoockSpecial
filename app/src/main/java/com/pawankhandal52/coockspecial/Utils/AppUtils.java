package com.pawankhandal52.coockspecial.Utils;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Pawan Khandal on 11/14/18,45
 */
public class AppUtils {
    public static String convertToString(ArrayList<String> list) {
        
        StringBuilder sb = new StringBuilder();
        String delim = "";
        for (String s : list) {
            sb.append(delim);
            sb.append(s);
            delim = ",";
        }
        return sb.toString();
    }
    
    public static ArrayList<String> convertToArray(String string) {
    
        return new ArrayList<String>(Arrays.asList(string.split(",")));
    }
    
}
