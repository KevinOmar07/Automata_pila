package com.example.structure_automata_pila;

import java.util.Stack;
import java.util.regex.Pattern;

public class Automata_pila {

    private boolean status = true;
    private String entrada = "";

    Automata_pila(String entrada ){
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

        String a = entrada.substring(0,6);
        int con = 0;

        do {
            con++;
            System.out.println("Vuelta " + con);
            x = pila.get(pila.size()-1);
            System.out.println(x);
            System.out.println(a + "\n");
            if (terminales.matcher(x).find()){
                System.out.println("Es un terminal: " + x);
                if(x.equals(a)){
                    pila.pop();
                    mover_apuntador(entrada);
                } else {
                    System.out.println("error");
                    pila.clear();
                }
            } else {
                System.out.println("No es un terminal");
                pila = meter_produccion(pila);
            }
        }while (!pila.isEmpty());
    }

    private void mover_apuntador(String entrada){
        System.out.println("Se mueve el puntero");
    }

    private Stack<String> meter_produccion(Stack<String> pila){
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
                pila.push("RLI");
                pila.push("LI");
                break;
            default: pila.clear();
        }
        return pila;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
