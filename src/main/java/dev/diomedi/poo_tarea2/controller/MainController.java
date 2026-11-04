package dev.diomedi.poo_tarea2.controller;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;

public class MainController {
    @FXML
    private ChoiceBox<String>  tipoBox;

    @FXML
    private TextField nombre;


    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}
