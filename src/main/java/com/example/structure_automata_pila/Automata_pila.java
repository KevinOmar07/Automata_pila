package com.example.structure_automata_pila;

import java.util.ArrayList;
import java.util.Stack;
import java.util.regex.Pattern;

/*

struct prueba {
	name:string,
}


 */

public class Automata_pila {

    private boolean status = true;
    private ArrayList <String> entrada;
    Pattern expresionNum = Pattern.compile("(^[0-9]{1})");
    Pattern expresionLetra = Pattern.compile("(^[a-z]{1})");
    String aAux;
    boolean auxPun = true;

    Automata_pila(ArrayList <String> entrada ){
        this.entrada = entrada;
    }

    public void validar_entrada (){
        // "(struct | :)" para hacer un or
        // "struct" para una cadena con un patrón en especifico
        // (struct || \{ || } || , || _ || : i32 || String || f32 || bool || i64 || f64)

        Pattern terminales = Pattern.compile("(struct|\\{|}|,|__|:|i32|String|f32|bool|i64|f64)|^[a-z0-9]{1}");
        Stack<String> pila = new Stack<>();
        pila.push("S");
        String x = "";

        int con = 0;
        int apuntador = 0;
        int apuntadorAux = 0;
        char [] cadenaAux;
        String a = entrada.get(apuntador);
        do {
            mostrar_pila(pila);
            con++;
            System.out.println("Vuelta " + con);
            x = pila.get(pila.size()-1);
            System.out.println(x);
            System.out.println(a + " : a\n");
            if (terminales.matcher(x).find()){
                System.out.println("Es un terminal: " + x);
                if(x.equals(a) || x.equals(aAux)){
                    pila.pop();

                    /*if(apuntador > 0){
                        cadenaAux = entrada.get(apuntador).toCharArray();
                        a = String.valueOf(cadenaAux[apuntadorAux]);
                        apuntadorAux++;
                    } else {

                    }*/
                    if (auxPun){
                        apuntador++;
                        a = entrada.get(apuntador);
                    }

                } else {
                    System.out.println("error");
                    status = false;
                }
            } else {
                System.out.println("No es un terminal");
                pila = meter_produccion(pila, a);
            }
        }while (!pila.isEmpty() && status);

        if(pila.isEmpty()){
            System.out.println("Entrada correcta: " + pila.isEmpty());
        }
    }

    /*private String mover_apuntador(ArrayList <String> entrada){
        System.out.println("Se mueve el puntero");
        entrada = entrada.substring(ini,fin);

        return entrada;
    } */

    private Stack<String> meter_produccion(Stack<String> pila, String puntero){
        String top = pila.get(pila.size()-1);
        pila.pop();
        switch (top){
            case "S":
                pila.push("LC");
                pila.push("IND");
                pila.push("LA");
                pila.push("Name");
                pila.push("Inicio");
                break;
            case "Inicio":
                pila.push("struct");
                break;
            case "Name":
                if (validarOr(entrada.get(1))){
                    pila.push("RLI");
                    pila.push("__");
                } else {
                    pila.push("RLI");
                    pila.push("LI");
                }
                break;
            case "LI":
                if (expresionLetra.matcher(puntero.substring(0,1)).find()) {
                    pila.push(puntero.substring(0,1));
                    aAux = puntero.substring(0,1);
                    auxPun = false;
                }
                    break;
            case "RLI":
                Pattern terminales = Pattern.compile("(_|^[a-z0-9])");
                if (terminales.matcher(puntero).find()){
                    System.out.println("Entro letra");
                    pila.push(puntero);
                    auxPun = true;
                } /*else if (expresionNum.matcher(puntero).find()){
                    System.out.println("Entro numero");
                    pila.push("RLI");
                    pila.push("D");
                } else if (puntero.equals("_")){
                    System.out.println("Entro guion");
                    pila.push("RLI");
                    pila.push("_");
                }*/
                break;
            /*case "LI":
                pila.push("a");
                break;
            case "RLI":
                if (puntero.equals(" ")){
                    pila.push("abc");
                }
                break;*/
            case "LA":
                pila.push("{");
                break;
            case "IND":
                break;
            case "LC":
                pila.push("}");
                break;
            default:
                System.out.println("default");
                pila.clear();
        }
        return pila;
    }

    private void mostrar_pila(Stack<String> pila){
        System.out.println("--------------------------------------------");

        for (int i = pila.size(); i>0; i--){
            System.out.println(pila.get(i-1));
        }
        System.out.println("--------------------------------------------");
    }

    private boolean validarOr (String g){
        boolean status_aux = false;
        if(g.substring(0,2).equals("__")) {
            status_aux = true;
        }

        return status_aux;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
