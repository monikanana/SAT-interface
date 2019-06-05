package com.barankosecki.utils;

import java.util.HashMap;
import java.util.Map;

public class CnfParser {

    private static Map<String, Integer> map;

    public static String parseToCnf(String input) {

        String[] clauses = input.split("\n");

        StringBuilder result = new StringBuilder();
        result.append("p cnf  ").append(clauses.length).append("\n");

        map = new HashMap<>();

        for (String clause : clauses) {

            clause = clause.replaceAll("\\s+", " ");
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

        System.out.println("Problem: ");
        System.out.println(result);

        return result.toString();
    }

    public static String numericLiteralsToCharacters(String input) {

        StringBuilder result = new StringBuilder();
        for (String literal : input.split(" ")) {
            result.append(literal.charAt(0) == '-' ? "-" : "");
            result.append(map.entrySet()
                    .stream()
                    .filter(e -> e.getValue().equals(Math.abs(Integer.parseInt(literal))))
                    .findFirst()
                    .get()
                    .getKey());
            result.append(" ");
        }

        return result.toString();
    }
}
