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
    Pattern terminal = Pattern.compile("^([_]{2}|[a-z])+([0-9]+|[a-z]+|[_]+)+$");
    boolean aux = false;
    boolean valid = false;

    Automata_pila(ArrayList <String> entrada ){
        this.entrada = entrada;
    }

    public void validar_entrada (){

        Pattern terminales = Pattern.compile("(struct|\\{|}|,|__|:|i32|String|f32|bool|Ɛ|i64|f64)|^[a-z0-9]{1}");
        Stack<String> pila = new Stack<>();
        pila.push("S");
        String x = "";

        int con = 0;
        int apuntador = 0;

        String a = entrada.get(apuntador);

        do {
            mostrar_pila(pila);
            con++;
            System.out.println("Vuelta " + con);
            x = pila.peek();
            System.out.println(x);
            System.out.println(a + " : a");
            if (terminales.matcher(x).find()){
                System.out.println("Es un terminal: " + x);
                if(x.equals(a) || x.equals("Ɛ")){ //|| x.equals(aAux
                    pila.pop();
                    apuntador++;

                    if (apuntador < entrada.size()){
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
            System.out.println("\n");
        }while (!pila.isEmpty() && status);

        if(pila.isEmpty()){
            System.out.println("Entrada correcta");
        }
    }

    private Stack<String> meter_produccion(Stack<String> pila, String puntero){
        String top = pila.peek();
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
                aux = validarName(entrada.get(1));
                if (aux){
                    pila.push("RLI");
                    pila.push("__");
                    mostrar_pila(pila);
                    pila.pop();
                    pila.pop();
                    pila.push(puntero);
                } else {
                    pila.push("RLI");
                    pila.push("LI");
                }
                break;
            case "LI":
                if (terminal.matcher(puntero).find()){
                    pila.pop();
                    pila.push(puntero);
                } else {
                    pila.push("a..z");
                }
                    break;
            case "RLI":

                if (terminal.matcher(puntero).find()){
                    System.out.println("Entro letra");
                    pila.push(puntero);
                }

                int tipo = validarRLI(puntero);

                if (tipo == 1){
                    pila.push("RLI");
                    pila.push("LI");
                } else if (tipo == 2) {
                    pila.push("RLI");
                    pila.push("D");
                } else if (tipo == 3) {
                    pila.push("RLI");
                    pila.push("_");
                }

                break;
            case "LA":
                pila.push("{");
                break;
            case "IND":
                pila.push("RC");
                pila.push("C");
                break;
            case "C":
                pila.push("Com");
                pila.push("TD");
                pila.push("DP");
                pila.push("Name");
                break;
            case "DP":
                pila.push(":");
                break;
            case "TD":
                Pattern tipo_datos = Pattern.compile("(i32|String|f32|bool|Ɛ|i64|f64)");
                if (tipo_datos.matcher(puntero).find()){
                    pila.push(puntero);
                } else {
                    pila.push("i32 | String | f32 | bool | i64 | f64");
                }
                break;
            case "Com":
                pila.push(",");
                break;
            case "RC":
                if (puntero.equals("}")){
                    System.out.println("Es vacio");
                    pila.push("Ɛ");
                } else {
                    System.out.println("No es vacio");
                    pila.push("RC");
                    pila.push("C");
                }
                break;
            case "LC":
                pila.push("}");
                break;
        }
        return pila;
    }

    private void mostrar_pila(Stack<String> pila){

        System.out.println("---------------------PILA-----------------------");

        for (int i = pila.size(); i>0; i--){
            System.out.println(pila.get(i-1));
        }
        System.out.println("---------------------PILA-----------------------");
    }

    private int validarRLI(String g){
        int aux = 0;

        if (expresionLetra.matcher(g.substring(0,1)).find()){
            aux = 1;
        } else if (expresionNum.matcher(g.substring(0,1)).find()){
            aux = 2;
        } else if (g.substring(0,1).equals("_")){
            aux = 3;
        }

        return aux;
    }

    private boolean validarName (String g){
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
