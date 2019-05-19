package com.barankosecki;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Controller {


    public static String runSATSolver(String name, String fileInputPath, String fileOutputPath, boolean satelite) {

        String filePath = "./src/main/resources/solvers/" + name;
        StringBuilder sb = new StringBuilder();

        ProcessBuilder processBuilder;
        try {
            if (satelite) {
                runSatELite(fileInputPath);
                fileInputPath = "./src/main/resources/solvers/outputSatElite";
            }

            switch (name) {
                case "Lingeling":
                    processBuilder = new ProcessBuilder(filePath, fileInputPath, "-o", fileOutputPath);
                    break;
                case "Zchaff":
                case "Cadical":
                case "AbcdSAT":
                case "Glucose":
                    processBuilder = new ProcessBuilder(filePath, fileInputPath);
                    break;
                default:
                    processBuilder = new ProcessBuilder(filePath, fileInputPath, fileOutputPath);
                    break;
            }

            Process process = processBuilder.start();
            InputStream inputStream = process.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream), 1);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
                sb.append("\n");
            }
            inputStream.close();
            bufferedReader.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    private static void runSatELite(String fileInputPath) {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder("./src/main/resources/solvers/SatELite_v1.0_linux", fileInputPath, "./src/main/resources/solvers/outputSatElite");
            Process process = processBuilder.start();
            InputStream inputStream = process.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream), 1);

            inputStream.close();
            bufferedReader.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
