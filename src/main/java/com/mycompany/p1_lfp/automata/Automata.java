/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.p1_lfp.automata;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author huron_clinch
 */
public class Automata {

    private final Lenguaje LENGUAJE = new Lenguaje();

    public List<Token> analizar(String texto) {//Analizarr texto en pantalla
        List<Token> tokens = new ArrayList<>();//Nueva lista
        int posicion = 0;
        int fila = 1;
        int columna = 1;

        while (posicion < texto.length()) {//Mientras haya tenxo sin analizar 
            char caracter = texto.charAt(posicion);//Obtener la letra seleccionada 

            if (Character.isLetter(caracter)) {
                int comienza = posicion;
                int comienzaColumna = columna;
                StringBuilder lexema = new StringBuilder();//Crear nuevo lexema

                while (posicion < texto.length() && Character.isLetterOrDigit(texto.charAt(posicion))) {
                    lexema.append(texto.charAt(posicion));
                    posicion++;
                    columna++;
                }

                String palabra = lexema.toString();//Palabra completa
                TipoToken tipo = LENGUAJE.getPALABRAS_RESERVADAS().contains(palabra) ? TipoToken.PALABRA_RESERVADA : TipoToken.IDENTIFICADOR;//Definir si se encontro tipo
                tokens.add(new Token(tipo, palabra, comienza, fila, comienzaColumna));//Agregar nuevo token

            } else if (Character.isDigit(caracter)) {
                int comienza = posicion;
                int comienzaColumna = columna;
                StringBuilder lexema = new StringBuilder();
                boolean decimal = false;

                while (posicion < texto.length() && (Character.isDigit(texto.charAt(posicion)) || texto.charAt(posicion) == '.')) {//Si se encuentra un .
                    if (texto.charAt(posicion) == '.') {
                        decimal = true;
                    }

                    lexema.append(texto.charAt(posicion));
                    posicion++;
                    columna++;
                }

                TipoToken tipo = decimal ? TipoToken.DECIMAL : TipoToken.NUMERO;//Definir si se encontro tipo
                tokens.add(new Token(tipo, lexema.toString(), comienza, fila, comienzaColumna));//Agregar nuevo token

            } else if (caracter == '\"') {//Comieza cadena
                int start = posicion;
                int startCol = columna;
                StringBuilder lexema = new StringBuilder();
                lexema.append(caracter);
                posicion++;
                columna++;
                while (posicion < texto.length() && texto.charAt(posicion) != '\"') {//Termina cadena 
                    lexema.append(texto.charAt(posicion));
                    posicion++;
                    columna++;
                }
                if (posicion < texto.length()) {
                    lexema.append('\"');
                    posicion++;
                    columna++;
                    tokens.add(new Token(TipoToken.CADENA, lexema.toString(), start, fila, startCol));//Agregar nuevo token
                } else {
                    tokens.add(new Token(TipoToken.ERROR, lexema.toString(), start, fila, startCol));//Agregar nuevo token (Cadena incorrecto)
                }

            } else if (caracter == '/' && posicion + 1 < texto.length() && texto.charAt(posicion + 1) == '/') {//Comienza comentario linea
                int start = posicion;
                int startCol = columna;
                StringBuilder lexema = new StringBuilder();
                lexema.append("//");
                posicion += 2;
                columna += 2;
                while (posicion < texto.length() && texto.charAt(posicion) != '\n') {//Hasta saldo de linea 
                    lexema.append(texto.charAt(posicion));
                    posicion++;
                    columna++;
                }
                tokens.add(new Token(TipoToken.COMENTARIO_LINEA, lexema.toString(), start, fila, startCol));//Agregar nuevo token

            } else if (caracter == '/' && posicion + 1 < texto.length() && texto.charAt(posicion + 1) == '*') {//Comienza comentario Bloque
                int start = posicion;
                int startCol = columna;
                StringBuilder lexema = new StringBuilder();
                lexema.append("/*");
                posicion += 2;
                columna += 2;
                while (posicion + 1 < texto.length() && !(texto.charAt(posicion) == '*' && texto.charAt(posicion + 1) == '/')) {//termina comentario bloque
                    if (texto.charAt(posicion) == '\n') {
                        fila++;
                        columna = 1;
                    } else {
                        columna++;
                    }
                    lexema.append(texto.charAt(posicion));
                    posicion++;
                }
                if (posicion + 1 < texto.length()) {
                    lexema.append("*/");
                    posicion += 2;
                    columna += 2;
                    tokens.add(new Token(TipoToken.COMENTARIO_BLOQUE, lexema.toString(), start, fila, startCol));//Agregar nuevo token
                } else {
                    tokens.add(new Token(TipoToken.ERROR, lexema.toString(), start, fila, startCol));//Agregar nuevo token(Comentario bloque error)
                }

            } else if (LENGUAJE.getOPERADORES().contains(caracter)) {//Si el lexema es un operador
                tokens.add(new Token(TipoToken.OPERADOR, String.valueOf(caracter), posicion, fila, columna));
                posicion++;
                columna++;

            } else if (LENGUAJE.getAGRUPACIONES().contains(caracter)) {//Si el lexema es una agrupacion
                tokens.add(new Token(TipoToken.AGRUPACION, String.valueOf(caracter), posicion, fila, columna));
                posicion++;
                columna++;

            } else if (caracter == ' ' || caracter == '\t') {//Si el lexema es espacio
                posicion++;
                columna++;

            } else if (caracter == '\n') {//Si el lexema es un salto
                posicion++;
                fila++;
                columna = 1;

            } else {//Si esta incorrecto y no tine alguna forma especifica 
                tokens.add(new Token(TipoToken.ERROR, String.valueOf(caracter), posicion, fila, columna));
                posicion++;
                columna++;
            }
        }
        return tokens;
    }
}
