/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.yahoo.ycsb.kvcliente;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import static java.lang.System.out;
import java.net.Socket;
import javax.swing.JOptionPane;

/**
 *
 * @author Evelyn Gonzalez
 */
public class KVValidacionesCliente {

    private Socket socket = null;
    private static final int MAX_ARRAY_SIZE = 128 * 1024 * 1024;//128 MB
    private static final int MAX_ARRAY_SIZE_VAL = (2048 * 1024 * 1024)-1;//2GB

    public String validate(String inputLine) throws IOException {
        //BufferedReader stdIn = new BufferedReader(input);
        String  inputLineRet = null;
        
        //inputLine = stdIn.readLine();
        if (inputLine == null) {
            out.println("Ingrese el comando ó 'help' para ayuda");
            inputLineRet = "error";
        } else if (inputLine != null) {
            String[] comando = inputLine.split("\\s+");//Valida mas de un espacio
            //out.println("VALIDA INGRESO COMANDOS");

            if (comando[0] == null || comando[0].equals("")) {
                out.println("ERROR: Debe insertar un comando, 'help' para ayuda");
                inputLineRet = "error";
            } else if (comando[0].toUpperCase().equals("SET")) {//Soporta mayusculas y minusculas
                if (comando.length<3 || comando[1] == null || comando[1].equals("") || comando[2] == null || comando[2].equals("")) {
                    out.println("ERROR: Debe insertar el key y el value: [set key value]");
                    inputLineRet = "error";
                } else if (comando[1].contains("/n") || comando[1].contains("/t")||comando[1].contains("/r")) {
                    out.println("El [key] no debe contener [/n] saltos de linea, [/t] tabulaciones, [/r] Retorno de carro");
                    inputLineRet = "error";
                } else if (comando[2].contains("/n")) {
                    out.println("El [Value] no debe contener [/n] saltos de linea");
                    inputLineRet = "error";
                } else {
                    int tamañoKey = comando[1].getBytes("UTF-8").length;
                    int tamañoValue = comando[2].getBytes("UTF-8").length;
                    if (tamañoKey > MAX_ARRAY_SIZE || tamañoValue > MAX_ARRAY_SIZE_VAL) {
                        out.println("ERROR: El tamaño máximo de una clave debe ser de 128MB; el tamaño máximo\n"
                                + "de un valor es de 2GB");
                        inputLineRet = "error";
                    } else {
                        inputLineRet = inputLine;
                    }
                }
            } else if (comando[0].toUpperCase().equals("GET")) {
                if (comando.length<2 || comando[1] == null || comando[1].equals("")) {
                    out.println("ERROR: Debe insertar el key: [get key]");
                    inputLineRet = "error";
                } else if (comando[1].getBytes("UTF-8").length > MAX_ARRAY_SIZE) {
                    out.println("ERROR: El tamaño máximo de una clave debe ser de 128MB.");
                    inputLineRet = "error";
                } else if (comando[1].contains("/n") || comando[1].contains("/t")) {
                    out.println("El [key] no debe contener [/n] saltos de linea , [/t] tabulaciones");
                    inputLineRet = "error";
                } else {
                    //kvKeyValuehasp.putKeyValue(comando[1], comando[2]);
                    inputLineRet = inputLine;
                }
            } else if (comando.length<2 && comando[0].toUpperCase().equals("DEL")) {//Soporta mayusculas y minusculas
                if (comando[1] == null || comando[1].equals("")) {
                    out.println("ERROR: Debe insertar el key: del key");
                    inputLineRet = "error";
                } else if (comando[1].contains("/n") || comando[1].contains("/t")) {
                    out.println("El [key] no debe contener [/n] saltos de linea , [/t] tabulaciones");
                    inputLineRet = "error";
                } else {
                    //kvKeyValuehasp.putKeyValue(comando[1], comando[2]);
                    inputLineRet = inputLine;
                }
            } else if (comando[0].toUpperCase().equals("LIST")) { //Soporta mayusculas y minusculas
                inputLineRet = inputLine;
            } else if (comando[0].toUpperCase().equals("HELP")) {//Soporta mayusculas y minusculas
                out.println("===================================================");
                out.println("============== KEY / VALUE  STORE =================");
                out.println("===================================================");
                out.println("[SET]:    Almacena la clave, con el valor asociado.");
                out.println("          El valor puede contener cualquier,       ");
                out.println("          caracteres especiales, tabs              ");
                out.println("          [SET] [KEY] [VALUE]                      ");
                out.println("[GET]:    Muestra el valor asociado con  la clave  ");
                out.println("          ingresada.                               ");
                out.println("          [GET] [KEY]                              ");
                out.println("[DEL]:    Elimina la clave, con su valor asociado. ");
                out.println("          [SET] [KEY] [VALUE]                      ");
                out.println("[LIST]:   Retorna la lista de todas las claves.    ");
                out.println("          [LIST]                                   ");
                out.println("[EXIT]:   Sale del Programa.");
                inputLineRet = "error";
            } else if (comando[0].toUpperCase().equals("EXIT")) {
                out.println("Ha salido del programa [KEY/VALUE STORE]!!!!!");
                System.exit(0);
            } else {
                out.println("ERROR: Debe insertar un comando, help para ayuda");
                inputLineRet = "error";
            }

        }
        //stdIn.close();
        return inputLineRet;
    }
}
