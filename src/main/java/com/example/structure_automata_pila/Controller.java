package com.example.structure_automata_pila;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

import java.util.ArrayList;
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

            ArrayList <String> entrada = separarTexto(cadena);


            /*System.out.println("-----------------------Aqui1-----------------------------");
            cortar(cadena);

            System.out.println("-----------------------Aqui2-----------------------------\n");
            ArrayList <String> a = separarTexto(cadena);*/

            Automata_pila automata_pila = new Automata_pila(entrada);
            automata_pila.validar_entrada();
            if (automata_pila.getStatus()) {
                labelStatus.setText("Correcto");
                labelStatus.setStyle("-fx-text-fill: #3FAE74");
            }else{
                labelStatus.setText("Incorrecto");
                labelStatus.setStyle("-fx-text-fill: red");
            }

            //probar(cadena);

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

    static ArrayList<String> separarTexto(String texto) {
        int cantidad = 0;
        ArrayList<String> structSplit = new ArrayList<>();
        texto = texto.trim();
        structSplit.add(texto.substring(0, 6));

        // nombre de la struct
        int vali = 0;
        for (int i = 6; i < texto.length(); i++) {
            if (!texto.substring(i, i + 1).equals("{")) {
                vali++;

            } else {
                break;
            }
        }
        String nombreStruct = texto.substring(6, 6 + vali);
        structSplit.add(nombreStruct.trim());

        // llave de apertura
        for (int i = 0; i < texto.length(); i++) {
            if (texto.charAt(i) == '{') {
                System.out.println(texto.substring(i, i + 1));
                structSplit.add(texto.substring(i, i+1));
                cantidad = i;
                break;
            }
        }

        // atributos
        String atributos = texto.substring(cantidad + 1, texto.length() - 1).trim();
        if(atributos.contains(",")){
            if (atributos.substring(atributos.length()-1, atributos.length()).equals(",")){
                String[] atributosCorte = atributos.split(",");
                for (String atributo : atributosCorte) {
                    if (atributo.contains(":")){
                        String[] at = atributo.split(":");
                        structSplit.add(at[0].trim());
                        structSplit.add(":");
                        if (at.length > 1){
                            structSplit.add(at[1].trim());
                            structSplit.add(",");
                        }
                    }
    
                }
            }else{
                String[] atributosCorte = atributos.split(",");
                int contador = 0;
                for (String atributo : atributosCorte) {
                    if (atributo.contains(":")){
                        String[] at = atributo.split(":");
                        structSplit.add(at[0].trim());
                        structSplit.add(":");
                        structSplit.add(at[1].trim());
                        if (contador<atributosCorte.length){
                            structSplit.add(",");
                        }
                        contador++;
                    }
    
                }
            }
        }else{
            structSplit.add("e");
        }

        //llave de cierre
        String llaveCierre = texto.substring(texto.length() - 1, texto.length()).trim();
        structSplit.add(llaveCierre);

        return structSplit;

    }

    void probar(String t){
        //Pattern terminales = Pattern.compile("^([_]{2}|[a-z])+([0-9]+|[a-z]+|[_]+)+$");

        Pattern terminales = Pattern.compile("(^[S]{1}[t][r][i][n][g]\\b)");

        if (terminales.matcher(t).matches()){
            System.out.println(t);
        } else {
            System.out.println("nel");
        }

    }
}