package com.example.structure_automata_pila;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

import java.util.ArrayList;
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

            ArrayList <String> entrada = separarTexto(cadena);


            /*System.out.println("-----------------------Aqui1-----------------------------");
            cortar(cadena);

            System.out.println("-----------------------Aqui2-----------------------------\n");
            ArrayList <String> a = separarTexto(cadena);*/

            Automata_pila automata_pila = new Automata_pila(entrada);
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

    static ArrayList<String> separarTexto(String texto) {
        int cantidad = 0;
        //boolean valid = false;
        ArrayList<String> structSplit = new ArrayList<>();
        // Map<Boolean,ArrayList<String>> validarStruct = new HashMap<Boolean,ArrayList<String>>();
        // struct
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
            String[] atributosCorte = atributos.split(",");
            for (String atributo : atributosCorte) {
                //System.out.println(atributo);
                if (atributo.contains(":")){
                    String[] at = atributo.split(":");
                    structSplit.add(at[0].trim());
                    structSplit.add(":");
                    structSplit.add(at[1].trim());
                }

            }
        }else if (atributos.contains(" ")){
            String[] atributosCorte = atributos.split(" ");
            for (String atributo : atributosCorte) {
                //System.out.println(atributo);
                structSplit.add(atributo.trim());
            }
        }else{
            structSplit.add("e");
        }

        //llave de cierre
        String llaveCierre = texto.substring(texto.length() - 1, texto.length()).trim();
        structSplit.add(llaveCierre);

        return structSplit;

    }

    /*void cortar(String texto){
        int con = 0;
        int cantidad = 0;
        while(con!=5){
            if (con==0) {
                System.out.println(texto.substring(0, 6));
                con=1;
            }else if (con==1){
                int vali =0 ;
                for (int i = 6; i < texto.length(); i++) {
                    if(!texto.substring(i,i+1).equals("{")){
                        vali++;
                    }else{
                        break;
                    }
                }
                System.out.println(texto.substring(6, 6+vali));
                con=2;
            }else if (con==2){
                for (int i = 0; i < texto.length(); i++) {
                    if (texto.charAt(i) == '{'){
                        System.out.println(texto.substring(i, i+1));
                        cantidad=i;
                        break;
                    }
                }
                con=3;
            }else if (con==3){
                String[] atributosCorte = texto.substring(cantidad+1, texto.length()-1).split(",");
                System.out.println("Atributos");
                for (String string : atributosCorte) {
                    System.out.println(string);
                }
                con=4;
            }else if (con==4){
                System.out.println(texto.substring(texto.length()-1, texto.length()));
                con=5;
            }
        }
    } */
}