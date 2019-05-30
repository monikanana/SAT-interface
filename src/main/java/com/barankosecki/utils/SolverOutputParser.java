package com.barankosecki.utils;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SolverOutputParser {

    public static String getResult(String solverOutput) {
        ArrayList<String> results = new ArrayList<>();

        BufferedReader bufferedReader = new BufferedReader(new StringReader(solverOutput));
        Pattern pattern = Pattern.compile("^v.*$");
        Matcher matcher = pattern.matcher(solverOutput);
        String line;

        try {
            while ((line = bufferedReader.readLine()) != null) {
                matcher.reset(line);
                if(matcher.find()) {
                    results.add(matcher.group());
                }
            }
        } catch(Exception e) {
            System.out.println(e);
        }

        StringBuilder result = new StringBuilder();

        if(!results.isEmpty()) {
            for(String v : results) {
                System.out.println(v);

                if(v.length() == 3) {
                    continue;
                }

                if(v.charAt(v.length()-1) == '0') {
                    v = v.substring(2, v.length() - 2);
                } else {
                    v = v.substring(2);
                }
                result.append(v);
            }
        }

        return result.toString();
    }
}
