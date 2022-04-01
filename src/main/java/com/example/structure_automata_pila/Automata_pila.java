package com.example.structure_automata_pila;

import java.util.ArrayList;
import java.util.Stack;
import java.util.regex.Pattern;

/*

struct prueba {
	name:String,
}

struct prueba {
	na_me:String,
}

struct __a {
	gtz_pedro:String,
	edad_ni : i32,
	__: String,
}

 */

public class Automata_pila {

    private boolean status = true;
    private ArrayList <String> entrada;
    Pattern expresionNum = Pattern.compile("(^[0-9]{1})");
    Pattern expresionLetra = Pattern.compile("(^[a-z]|_)");
    Pattern terminal = Pattern.compile("^([a-z]+)|(([_]{1}|([a-z]))+([0-9]+|[a-z]+|[_]+))+$");

    boolean aux = false;
    boolean banderaValidar = true;
    String entradaAux = "";
    boolean boolAux = false;

    Automata_pila(ArrayList <String> entrada ){
        this.entrada = entrada;
    }

    public void validar_entrada (){

        Pattern terminales = Pattern.compile("(struct|\\{|}|,|__|:|i32|String|f32|bool|Ɛ|i64|f64)|^([_][a-z0-9]{1}|[a-z0-9]{1})|_");
        Stack<String> pila = new Stack<>();
        pila.push("S");
        String x = "";

        int apuntador = 0;

        String a = entrada.get(apuntador);

        do {
            mostrar_pila(pila);
            x = pila.peek();
            if (terminales.matcher(x).find()){
                if((x.equals(a) || x.equals("Ɛ"))  && !boolAux){
                    pila.pop();
                    apuntador++;

                    if (apuntador < entrada.size()){
                        a = entrada.get(apuntador);
                    }

                } else if (x.equals(a.substring(0,1)) && boolAux){
                    pila.pop();
                    a = entradaAux;
                } else {
                    System.out.println("Entrada invalida");
                    status = false;
                }
            } else {
                pila = meter_produccion(pila, a);
            }
            System.out.println("\n");
        }while (!pila.isEmpty() && status);

        if(pila.isEmpty()){
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
                int aux2 = 0;
                if (terminal.matcher(puntero).find()){
                    aux2 = validarName(puntero);
                }

                if (aux2 == 1){
                    pila.push("RLI");
                    pila.push("__");
                    mostrar_pila(pila);
                    pila.pop();
                    pila.pop();
                    pila.push(puntero);
                    aux = true;
                } else if (aux2 == 2){
                    aux = false;
                    pila.push("RLI");
                    pila.push("LI");
                } else {
                    pila.push("a..z RLI | __ RLI");
                }
                break;
            case "LI":
                pila = validarDorLI(pila, puntero, expresionLetra);

                if (!banderaValidar){
                    banderaValidar = true;
                    pila.push("a..z");
                }

                break;
            case "RLI":
                int tipo = 0;
                if (puntero.length()>0){
                    tipo = validarRLI(puntero);
                }

                if (tipo == 1){
                    pila.push("RLI");
                    pila.push("LI");
                } else if (tipo == 2) {
                    pila.push("RLI");
                    pila.push("D");
                } else if (tipo == 3) {
                    pila.push("RLI");
                    pila.push("_");

                    mostrar_pila(pila);
                    pila.pop();
                    pila.pop();
                    pila.push(puntero);
                    boolAux = false;
                }

                break;
            case "D":
                pila = validarDorLI(pila, puntero, expresionNum);
                if (!banderaValidar){
                    pila.push("0..9");
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
                Pattern tipo_datos = Pattern.compile("(i32|String|f32|bool|i64|f64)");
                if (tipo_datos.matcher(puntero).matches()){
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
                    pila.push("Ɛ");
                } else {
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

        System.out.println("---------------------PILA INICIO-----------------------");

        for (int i = pila.size(); i>0; i--){
            System.out.println(pila.get(i-1));
        }
        System.out.println("---------------------PILA FIN------------------------");
    }

    private Stack <String> validarDorLI(Stack <String> pila, String puntero, Pattern expresion){
        if (expresion.matcher(puntero.substring(0,1)).find()){
            pila.push(puntero.substring(0,1));
            entradaAux = puntero.substring(1,puntero.length());

            if (entradaAux.length() > 0){
                boolAux = true;
            } else {
                boolAux = false;
            }
        } else {
            banderaValidar = false;
        }

        return pila;
    }

    private int validarRLI(String g){
        int aux = 0;

        if (expresionLetra.matcher(g.substring(0,1)).find()){
            if (g.substring(0,1).equals("_")){
                aux = 3;
            } else {
                aux = 1;
            }
        } else if (expresionNum.matcher(g.substring(0,1)).find()){
            aux = 2;
        }

        return aux;
    }

    private int validarName (String g){
        int status_aux = 2;
        if(g.length() >= 2){
            if(g.substring(0,2).equals("__")) {
                status_aux = 1;
            }
        }

        return status_aux;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}