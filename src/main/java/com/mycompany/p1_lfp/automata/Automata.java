/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.p1_lfp.automata;

import com.mycompany.p1_lfp.generacion_automata.Movimiento;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author huron_clinch
 */
public class Automata {

    private final Lenguaje LENGUAJE = new Lenguaje();
    private final List<Movimiento> MOVIMIENTOS = new ArrayList<>();
    private int contadorEstado;

    public List<Movimiento> getMovimientos() {
        return MOVIMIENTOS;
    }

    private void registrarMovimiento(char c, TipoToken tipo, String lexema) {//Registra un movimiento
        int origen = contadorEstado;
        int destino = ++contadorEstado;
        MOVIMIENTOS.add(new Movimiento(origen, destino, c, tipo, lexema));
    }

    private void registrarToken(Token token) {//Registra token completo
        MOVIMIENTOS.add(new Movimiento(contadorEstado, 0, ' ', token.getTipo(), "Guardando token " + token.getTipo() + " Lexema: " + token.getLexema() + ".\n"));
        contadorEstado = 0; // Reinicio de autómata
    }

    public List<Token> analizar(String texto) {//Analizarr texto en pantalla
        MOVIMIENTOS.clear();//Limpiar movimientos existentes
        contadorEstado = 0;

        List<Token> tokens = new ArrayList<>();//Nueva lista
        int posicion = 0;
        int fila = 1;
        int columna = 1;

        while (posicion < texto.length()) {//Mientras haya tenxo sin analizar 
            char caracter = texto.charAt(posicion);//Obtener la letra seleccionada 

            if (Character.isLetter(caracter)) {//Si es identificador o palabra reservada
                int comienza = posicion;
                int comienzaColumna = columna;
                StringBuilder lexema = new StringBuilder();//Crear nuevo lexema

                while (posicion < texto.length() && Character.isLetterOrDigit(texto.charAt(posicion))) {
                    lexema.append(texto.charAt(posicion));
                    registrarMovimiento(texto.charAt(posicion), TipoToken.IDENTIFICADOR, lexema.toString());
                    posicion++;
                    columna++;
                }

                String palabra = lexema.toString();//Palabra completa
                TipoToken tipo = LENGUAJE.getPALABRAS_RESERVADAS().contains(palabra) ? TipoToken.PALABRA_RESERVADA : TipoToken.IDENTIFICADOR;//Definir si se encontro tipo
                Token token = new Token(tipo, palabra, comienza, fila, comienzaColumna);//Agregar nuevo token
                tokens.add(token);
                registrarToken(token);

            } else if (Character.isDigit(caracter)) {//Si es numero o decimal
                int comienza = posicion;
                int comienzaColumna = columna;
                StringBuilder lexema = new StringBuilder();
                boolean decimal = false;
                boolean error = false;

                while (posicion < texto.length()) {
                    char actual = texto.charAt(posicion);

                    if (Character.isDigit(actual)) {//Si es digito
                        lexema.append(actual);
                        registrarMovimiento(actual, decimal ? TipoToken.DECIMAL : TipoToken.NUMERO, lexema.toString());
                        posicion++;
                        columna++;
                    } else if (actual == '.' && !decimal) {//Si se encuentra un .
                        decimal = true;
                        lexema.append(actual);
                        registrarMovimiento(actual, TipoToken.DECIMAL, lexema.toString());
                        posicion++;
                        columna++;
                    } else if (Character.isLetter(actual)) {//Hay error por letra
                        lexema.append(actual);
                        registrarMovimiento(actual, TipoToken.ERROR, lexema.toString());
                        posicion++;
                        columna++;
                        error = true;

                        while (posicion < texto.length() && Character.isLetter(texto.charAt(posicion))) {//Agregamos letra para el error
                            lexema.append(texto.charAt(posicion));
                            registrarMovimiento(texto.charAt(posicion), TipoToken.ERROR, lexema.toString());
                            posicion++;
                            columna++;
                        }
                        break;
                    } else {//Temino el numero
                        break;
                    }
                }
                if (lexema.length() > 0) {//Guardar el lexema segun si es valido o no
                    TipoToken tipo = error ? TipoToken.ERROR : (decimal ? TipoToken.DECIMAL : TipoToken.NUMERO);//Definir si se encontro tipo
                    Token token = new Token(tipo, lexema.toString(), comienza, fila, comienzaColumna);//Agregar nuevo token
                    tokens.add(token);
                    registrarToken(token);
                }
            } else if (caracter == '\"') {//Comieza cadena
                int comienza = posicion;
                int comienzaColumna = columna;
                StringBuilder lexema = new StringBuilder();

                lexema.append(caracter);
                registrarMovimiento(caracter, TipoToken.CADENA, lexema.toString());
                posicion++;
                columna++;

                while (posicion < texto.length() && texto.charAt(posicion) != '\"') {//Termina cadena 
                    lexema.append(texto.charAt(posicion));
                    registrarMovimiento(texto.charAt(posicion), TipoToken.CADENA, lexema.toString());
                    posicion++;
                    columna++;
                }

                if (posicion < texto.length()) {
                    lexema.append('\"');
                    registrarMovimiento('\"', TipoToken.CADENA, lexema.toString());
                    posicion++;
                    columna++;
                    Token token = new Token(TipoToken.CADENA, lexema.toString(), comienza, fila, comienzaColumna);//Agregar nuevo token
                    tokens.add(token);
                    registrarToken(token);
                } else {
                    tokens.add(new Token(TipoToken.ERROR, lexema.toString(), comienza, fila, comienzaColumna));//Agregar nuevo token (Cadena incorrecto)
                }

            } else if (caracter == '/' && posicion + 1 < texto.length() && texto.charAt(posicion + 1) == '/') {//Comienza comentario linea
                int comienza = posicion;
                int comienzaColumna = columna;
                StringBuilder lexema = new StringBuilder();

                lexema.append("//");
                registrarMovimiento('/', TipoToken.COMENTARIO_LINEA, lexema.toString());
                posicion += 2;
                columna += 2;

                while (posicion < texto.length() && texto.charAt(posicion) != '\n') {//Hasta saldo de linea 
                    lexema.append(texto.charAt(posicion));
                    registrarMovimiento(texto.charAt(posicion), TipoToken.COMENTARIO_LINEA, lexema.toString());
                    posicion++;
                    columna++;
                }

                Token token = new Token(TipoToken.COMENTARIO_LINEA, lexema.toString(), comienza, fila, comienzaColumna);
                tokens.add(token);
                registrarToken(token);

            } else if (caracter == '/' && posicion + 1 < texto.length() && texto.charAt(posicion + 1) == '*') {//Comienza comentario Bloque
                int comienza = posicion;
                int comienzaColumna = columna;
                StringBuilder lexema = new StringBuilder();

                lexema.append("/*");
                registrarMovimiento('/', TipoToken.COMENTARIO_BLOQUE, lexema.toString());
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
                    registrarMovimiento(texto.charAt(posicion), TipoToken.COMENTARIO_BLOQUE, lexema.toString());
                    posicion++;
                }

                if (posicion + 1 < texto.length()) {
                    lexema.append("*/");
                    registrarMovimiento('/', TipoToken.COMENTARIO_BLOQUE, lexema.toString());
                    posicion += 2;
                    columna += 2;
                    Token token = new Token(TipoToken.COMENTARIO_BLOQUE, lexema.toString(), comienza, fila, comienzaColumna);//Agregar nuevo token
                    tokens.add(token);
                    registrarToken(token);
                } else {
                    tokens.add(new Token(TipoToken.ERROR, lexema.toString(), comienza, fila, comienzaColumna));//Agregar nuevo token(Comentario bloque error)
                }

            } else if (LENGUAJE.getOPERADORES().contains(caracter)) {//Si el lexema es un operador
                registrarMovimiento(caracter, TipoToken.OPERADOR, String.valueOf(caracter));
                Token token = new Token(TipoToken.OPERADOR, String.valueOf(caracter), posicion, fila, columna);
                tokens.add(token);
                registrarToken(token);
                posicion++;
                columna++;

            } else if (LENGUAJE.getAGRUPACIONES().contains(caracter)) {//Si el lexema es una agrupacion
                registrarMovimiento(caracter, TipoToken.AGRUPACION, String.valueOf(caracter));
                Token token = new Token(TipoToken.AGRUPACION, String.valueOf(caracter), posicion, fila, columna);
                tokens.add(token);
                registrarToken(token);
                posicion++;
                columna++;

            } else if (caracter == ' ' || caracter == '\t') {//Si el lexema es espacio o tabulacion
                posicion++;
                columna++;

            } else if (caracter == '\n') {//Si el lexema es un salto
                posicion++;
                fila++;
                columna = 1;

            } else {//Si esta incorrecto y no tine alguna forma especifica 
                registrarMovimiento(caracter, TipoToken.ERROR, String.valueOf(caracter));
                Token token = new Token(TipoToken.ERROR, String.valueOf(caracter), posicion, fila, columna);
                tokens.add(token);
                registrarToken(token);
                posicion++;
                columna++;
            }
        }
        return tokens;
    }
}
