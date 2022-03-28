package com.example.structure_automata_pila;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Controller {

    @FXML
    private Label labelMensaje;

    @FXML
    private Label labelStatus;

    @FXML
    private TextArea textArea;

    @FXML
    protected void validarStructure() {
        String cadena = textArea.getText();

        if (cadena.length() > 0){
            labelStatus.setText("En proceso");
            labelStatus.setStyle("-fx-text-fill: black");
            labelMensaje.setText("Validando cadena");
            System.out.println(cadena + "\n");

            Automata_pila automata_pila = new Automata_pila(cadena);
            automata_pila.validar_entrada();

        } else {
            labelStatus.setText("ERROR");
            labelStatus.setStyle("-fx-text-fill: RED");
            labelMensaje.setText("Debe escribir en el Text Area");
        }
    }

    @FXML
    void limpiar(){
        labelStatus.setText("");
        labelMensaje.setText("");
        textArea.clear();
    }
}