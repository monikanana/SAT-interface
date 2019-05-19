package com.barankosecki;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.sat4j.reader.DimacsReader;
import org.sat4j.tools.DimacsOutputSolver;

import java.io.File;

public class Main extends Application {

    @FXML
    private TextArea problemInput;

    @FXML
    private TextArea outputField;

    @FXML
    private Button currentCnfFileBtn;

    @FXML
    private ComboBox<String> chooseSolver;

    @FXML
    private Button runBtn;

    @FXML
    private Button outputFileBtn;

    @FXML
    private Button sateliteBtn;

    @FXML
    private Button runInputBtn;

    private Stage stage;
    private DimacsReader dimacsReader;

    private String fileInput;
    private String saveOutput;
    private boolean satelite;

    @FXML
    public void loadCnfFile() {
        System.out.println("Load CNF File");
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("./src/main/resources/problems"));
        File file = fileChooser.showOpenDialog(stage);

        if (file != null) {
            fileInput = file.getAbsolutePath();
            this.currentCnfFileBtn.setText(file.getName());

            try {
                dimacsReader.parseInstance(file.getAbsolutePath());
                runBtn.setDisable(false);
                currentCnfFileBtn.getStyleClass().removeAll("btn", "btn-green", "btn-red");
                currentCnfFileBtn.getStyleClass().add("btn-green");
            } catch (Exception e) {
                runBtn.setDisable(true);
                currentCnfFileBtn.getStyleClass().removeAll("btn", "btn-green", "btn-red");
                currentCnfFileBtn.getStyleClass().add("btn-red");
            }
        }
    }

    @FXML
    public void chooseSolver() {
        System.out.println("Choose solver");
        String currentSolver = chooseSolver.getValue();

        if (currentSolver.equals("Syrup") || currentSolver.equals("AbcdSAT") || currentSolver.equals("Zchaff") || currentSolver.equals("Cadical")) {
            outputFileBtn.setDisable(true);
        } else {
            outputFileBtn.setDisable(false);
        }
    }

    @FXML
    public void runSolverFromInput() {
        System.out.println("Run Solver from input");
        runSolver(false);
    }

    @FXML
    public void runSolverFromFile() {
        System.out.println("Run Solver from file");
        runSolver(true);
    }

    private void runSolver(boolean fromFile) {
        String solver = this.chooseSolver.getValue();
        String input;
        String output = "";

        if (fromFile) {
            input = this.fileInput;
        } else {
            input = ""; // TODO: Servis Moniki
        }

        switch (solver) {
            case "AbcdSAT":
            case "Cadical":
            case "Glucose":
            case "Lingeling":
            case "Minisat":
            case "Riss":
            case "Syrup":
            case "Zchaff":
            case "Maple_CM":
            case "Maple_CM_Dist":
            case "Maple_CM_ordUIP":
            case "Maple_CM_ordUIP+":
            case "MapleLCMDistChronoBT":
            case "Maple_LCM_Scavel_fix2":
            case "Maple_LCM_Scavel_200_fix2":
                output = Controller.runSATSolver(solver, input, saveOutput, satelite);
                break;
        }
        outputField.setText(output);
    }

    @FXML
    public void setOutputFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("./src/main/resources/results"));
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            outputFileBtn.setText("Output file: " + file.getName());
            saveOutput = file.getAbsolutePath();
        }
    }

    @FXML
    public void toggleSatelite() {
        satelite = !satelite;
        if (satelite) {
            sateliteBtn.getStyleClass().remove("btn-red");
            sateliteBtn.getStyleClass().add("btn-green");
        } else {
            sateliteBtn.getStyleClass().remove("btn-green");
            sateliteBtn.getStyleClass().add("btn-red");
        }

        System.out.println("Satelite is " + (this.satelite ? "on" : "off"));
        System.out.println(satelite);
    }

    @FXML
    public void initialize() {
        System.out.println("Initialization");

        satelite = false;
        saveOutput = "";
        dimacsReader = new DimacsReader(new DimacsOutputSolver());
        runBtn.setDisable(true);
        outputFileBtn.setDisable(true);
    }

    @Override
    public void start(final Stage primaryStage) throws Exception {

        /* config */
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("layout.fxml"));
        root.getStylesheets().add(Main.class.getClassLoader().getResource("main.css").toExternalForm());

        stage = primaryStage;
        stage.setResizable(false); // preventing window resizing
        stage.setTitle("SAT Solver Interface");
        stage.setScene(new Scene(root, 1000, 600));
        stage.getIcons().add(new Image("file:src/main/resources/icon.png"));
        /* eof config */

        stage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
