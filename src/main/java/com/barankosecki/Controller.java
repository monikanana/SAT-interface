package com.barankosecki;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Controller {


    public static String runSATSolver(String name, String fileInputPath, String fileOutputPath ,boolean satelite){

        String filePath = "/home/monika/Projects/SEMESTR_VI/Kompilatory/SAT_solvers_comparison/src/main/java/solvers/"+name;
        StringBuilder  sb = new StringBuilder();

        ProcessBuilder processBuilder;
        try{
            if(satelite){
                runSatELite(fileInputPath);
                fileInputPath="/home/monika/Projects/SEMESTR_VI/Kompilatory/SAT_solvers_comparison/src/main/java/solvers/outputSatElite";
            }

            if(name.equals("lingeling")){
                processBuilder = new ProcessBuilder(filePath,fileInputPath, "-o" ,fileOutputPath);
            }
            else if (name.equals("zchaff") ||name.equals("cadical") || name.equals("abcdsat_p") || name.equals("glucose-syrup")){
                processBuilder = new ProcessBuilder(filePath,fileInputPath);
            }else{
                processBuilder = new ProcessBuilder(filePath,fileInputPath ,fileOutputPath);
            }



            Process process = processBuilder.start();
            InputStream inputStream = process.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream),1);
            String line;
            while ((line = bufferedReader.readLine()) != null){
                sb.append(line);
                sb.append("\n");
            }
            inputStream.close();
            bufferedReader.close();

        }catch(Exception e){
            e.printStackTrace();
        }
        return sb.toString();
    }

    private static void runSatELite(String fileInputPath){
        try{
            ProcessBuilder processBuilder = new ProcessBuilder("/home/monika/Projects/SEMESTR_VI/Kompilatory/SAT_solvers_comparison/src/main/java/solvers/SatELite_v1.0_linux",fileInputPath ,"./../solvers/outputSatElite");
            Process process = processBuilder.start();
            InputStream inputStream = process.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream),1);

            inputStream.close();
            bufferedReader.close();

        }catch(Exception e){
            e.printStackTrace();
        }
    }




}
