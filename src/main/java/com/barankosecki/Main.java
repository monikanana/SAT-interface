package com.barankosecki;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.sat4j.reader.DimacsReader;
import org.sat4j.tools.DimacsOutputSolver;

import java.io.File;


public class Main extends Application {

    @FXML
    private TextArea outputField;

    @FXML
    private Button currentCnfFileBtn;

    @FXML
    private ComboBox<String> chooseSolver;

    @FXML
    private Button runBtn;

    private String fileInput;
    private String saveOutput;
    private boolean satelite;

    @FXML
    private Button outputFileBtn;

    @FXML
    private Button sateliteBtn;


    private Stage stage;
    private DimacsReader dimacsReader;

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
                //runBtn.setTextFill(Color.GREEN);
                runBtn.setDisable(false);
            } catch (Exception e) {
                //runBtn.setTextFill(Color.RED);
                e.printStackTrace();
                runBtn.setDisable(true);
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
    public void runSolver() {
        System.out.println("Run Solver");
        String solver = this.chooseSolver.getValue();
        String output = "";
        if ("AbcdSAT".equals(solver)) {
            output = Controller.runSATSolver("abcdsat_p", fileInput, saveOutput, satelite);
        } else if ("Cadical".equals(solver)) {
            output = Controller.runSATSolver("cadical", fileInput, saveOutput, satelite);
        } else if ("Glucose".equals(solver)) {
            output = Controller.runSATSolver("glucose_static", fileInput, saveOutput, satelite);
        } else if ("Lingeling".equals(solver)) {
            output = Controller.runSATSolver("lingeling", fileInput, saveOutput, satelite);
        } else if ("Minisat".equals(solver)) {
            output = Controller.runSATSolver("minisat", fileInput, saveOutput, satelite);
        } else if ("Riss".equals(solver)) {
            output = Controller.runSATSolver("riss", fileInput, saveOutput, satelite);
        } else if ("Syrup".equals(solver)) {
            output = Controller.runSATSolver("glucose-syrup", fileInput, saveOutput, satelite);
        } else if ("Zchaff".equals(solver)) {
            output = Controller.runSATSolver("zchaff", fileInput, saveOutput, satelite);
        }

        outputField.setText(output);
    }

    @FXML
    public void setOutputFile() {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            outputFileBtn.setText("Output file: " + file.getName());
            saveOutput = file.getAbsolutePath();
        }
    }

    @FXML
    public void toggleSatelite() {
        satelite = !satelite;
        System.out.println("Satelite is" + (this.satelite ? "on" : "off"));
    }

    @FXML
    public void initialize() {
        System.out.println("Initialization");

        satelite = false;
        saveOutput = "";
        dimacsReader = new DimacsReader(new DimacsOutputSolver());
        runBtn.setDisable(true);

    }

    @Override
    public void start(final Stage primaryStage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("layout.fxml"));
        root.getStylesheets().add(Main.class.getClassLoader().getResource("main.css").toExternalForm());

        /* config */
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
