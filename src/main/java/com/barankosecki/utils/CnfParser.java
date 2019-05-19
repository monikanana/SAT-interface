package com.barankosecki.utils;

import java.util.HashMap;
import java.util.Map;

public class CnfParser {

    public static String parseToCnf(String input) {

        String[] clauses = input.split("\n");

        StringBuilder result = new StringBuilder();
        result.append("p cnf  ").append(clauses.length).append("\n");

        Map<String, Integer> map = new HashMap<>();

        for (String clause : clauses) {
            for (String literal : clause.split(" ")) {

                String variable = literal.charAt(0) == '-' ? literal.substring(1) : literal;
                String sign = literal.charAt(0) == '-' ? "-" : "";

                if (map.containsKey(variable)) {
                    result.append(sign).append(map.get(variable)).append(" ");
                } else {
                    map.put(variable, map.size() + 1);
                    result.append(sign).append(map.get(variable)).append(" ");
                }
            }
            result.append("0\n");
        }
        result.insert(6, map.size());

        return result.toString();
    }

    public static void main(String[] args) {

    }
}
