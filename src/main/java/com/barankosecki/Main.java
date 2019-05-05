package com.barankosecki;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.sat4j.reader.DimacsReader;
import org.sat4j.tools.DimacsOutputSolver;

import java.io.File;


public class Main extends Application {

    private TextArea outputField;
    private Text text;
    private Button executeButton;
    private String fileInput;
    private String saveOutput;
    private Label labelChoose;
    private boolean satelite;
    private Button saveFile;


    @Override
    public void start(final Stage primaryStage) throws Exception {

        final DimacsReader dimacsReader = new DimacsReader(new DimacsOutputSolver());
        primaryStage.setTitle("SAT Solver Interfejs");
        Pane pane = new Pane();
        saveOutput = "";
        satelite = false;


        Label chooseFile = new Label();
        chooseFile.setPrefSize(500, 20);
        chooseFile.setText("Wybierz plik");
        chooseFile.setLayoutX(200);
        chooseFile.setLayoutY(20);
        pane.getChildren().addAll(chooseFile);


        text = new Text();

        Button chooseFileButton = new Button("Wyszukaj");
        chooseFileButton.setOnAction(event -> {
                    FileChooser fileChooser = new FileChooser();
                    fileChooser.setInitialDirectory(new File("./src/main/java/solvers"));
                    File file = fileChooser.showOpenDialog(primaryStage);

                    if (file != null) {
                        fileInput = file.getAbsolutePath();
                        text.setText(file.getName());

                        try {
                            dimacsReader.parseInstance(file.getAbsolutePath());
                            text.setFill(Color.GREEN);
                            executeButton.setDisable(false);
                        } catch (Exception e) {
                            text.setFill(Color.RED);
                            executeButton.setDisable(true);
                        }

                        text.setLayoutX(210);
                        text.setLayoutY(113);

                    }
                }
        );
        pane.getChildren().addAll(text);
        chooseFileButton.setLayoutX(200);
        chooseFileButton.setLayoutY(50);

        Label fileChecker = new Label();
        fileChecker.setText("Problem SAT : ");
        fileChecker.setLayoutX(110);
        fileChecker.setLayoutY(100);
        pane.getChildren().addAll(fileChecker);


        Label chooseSolver = new Label();
        chooseSolver.setText("Wybierz solver");
        chooseSolver.setLayoutX(100);
        chooseSolver.setLayoutY(150);
        pane.getChildren().addAll(chooseSolver);

        final ComboBox<String> comboBox = new ComboBox<String>();
        comboBox.getItems().addAll(
                "AbcdSAT",
                "Cadical",
                "Glucose",
                "Lingeling",
                "Minisat",
                "Riss",
                "Syrup",
                "Zchaff"
        );
        comboBox.setOnAction(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent event) {
                if (comboBox.getValue().toString().equals("Syrup") || comboBox.getValue().toString().equals("AbcdSAT") || comboBox.getValue().toString().equals("Zchaff") || comboBox.getValue().toString().equals("Cadical")) {
                    saveFile.setDisable(true);
                } else {
                    saveFile.setDisable(false);
                }
            }
        });
        comboBox.setValue("AbcdSAT");
        comboBox.setLayoutX(100);
        comboBox.setLayoutY(170);
        pane.getChildren().addAll(comboBox);


        final CheckBox sateliteCheckBox = new CheckBox("Satelite");
        sateliteCheckBox.setOnAction(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent event) {
                if (sateliteCheckBox.isSelected()) {
                    satelite = true;
                } else {
                    satelite = false;
                }
            }
        });
        sateliteCheckBox.setLayoutX(250);
        sateliteCheckBox.setLayoutY(175);

        pane.getChildren().addAll(sateliteCheckBox);


        executeButton = new Button("Wykonaj");
        executeButton.setOnAction(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent event) {
                String s = comboBox.getValue().toString();
                String output = new String();
                if ("AbcdSAT".equals(s)) {
                    output = Controller.runSATSolver("abcdsat_p", fileInput, saveOutput, satelite);
                } else if ("Cadical".equals(s)) {
                    output = Controller.runSATSolver("cadical", fileInput, saveOutput, satelite);
                } else if ("Glucose".equals(s)) {
                    output = Controller.runSATSolver("glucose_static", fileInput, saveOutput, satelite);
                } else if ("Lingeling".equals(s)) {
                    output = Controller.runSATSolver("lingeling", fileInput, saveOutput, satelite);
                } else if ("Minisat".equals(s)) {
                    output = Controller.runSATSolver("minisat", fileInput, saveOutput, satelite);
                } else if ("Riss".equals(s)) {
                    output = Controller.runSATSolver("riss", fileInput, saveOutput, satelite);
                } else if ("Syrup".equals(s)) {
                    output = Controller.runSATSolver("glucose-syrup", fileInput, saveOutput, satelite);
                } else if ("Zchaff".equals(s)) {
                    output = Controller.runSATSolver("zchaff", fileInput, saveOutput, satelite);
                }

                outputField.setText(output);
            }
        });
        executeButton.setDisable(true);
        executeButton.setLayoutX(300);
        executeButton.setLayoutY(300);
        pane.getChildren().addAll(executeButton);


        Label labelSave = new Label("Wybierz miejsce zapisu ");
        labelSave.setLayoutX(50);
        labelSave.setLayoutY(270);
        pane.getChildren().addAll(labelSave);

        saveFile = new Button("Zapisz");
        saveFile.setOnAction(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent event) {
                FileChooser fileChooser = new FileChooser();
                File file = fileChooser.showOpenDialog(primaryStage);
                if (file == null) {
                    labelChoose.setText("Wybrano plik : ");
                    saveOutput = "src/solvers/tmp";
                } else {
                    labelChoose.setText("Wybrano plik : " + file.getName());
                    saveOutput = file.getAbsolutePath();
                }

            }
        });
        saveFile.setLayoutX(100);
        saveFile.setLayoutY(300);
        saveFile.setDisable(true);
        pane.getChildren().addAll(saveFile);


        labelChoose = new Label("Wybrano plik : ");
        labelChoose.setLayoutX(20);
        labelChoose.setLayoutY(340);
        pane.getChildren().addAll(labelChoose);


        outputField = new TextArea();
        outputField.setPrefSize(730, 850);
        outputField.setLayoutX(450);
        outputField.setLayoutY(0);
        pane.getChildren().addAll(outputField);

        pane.getChildren().addAll(chooseFileButton);


        primaryStage.setScene(new Scene(pane, 1200, 850));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
