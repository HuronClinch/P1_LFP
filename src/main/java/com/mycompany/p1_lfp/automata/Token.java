/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.p1_lfp.automata;

/**
 *
 * @author huron_clinch
 */
public class Token {

    private TipoToken tipo;
    private String lexema;
    private int inicio;
    private int fila;
    private int columna;

    public Token(TipoToken tipo, String lexema, int inicio, int fila, int columna) {
        this.tipo = tipo;
        this.lexema = lexema;
        this.inicio = inicio;
        this.fila = fila;
        this.columna = columna;
    }

    public TipoToken getTipo() {
        return tipo;
    }

    public void setTipo(TipoToken tipo) {
        this.tipo = tipo;
    }

    public String getLexema() {
        return lexema;
    }

    public void setLexema(String lexema) {
        this.lexema = lexema;
    }

    public int getInicio() {
        return inicio;
    }

    public void setInicio(int inicio) {
        this.inicio = inicio;
    }

    public int getFila() {
        return fila;
    }

    public void setFila(int fila) {
        this.fila = fila;
    }

    public int getColumna() {
        return columna;
    }

    public void setColumna(int columna) {
        this.columna = columna;
    }
}
