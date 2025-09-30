/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.p1_lfp.generacion_automata;

import com.mycompany.p1_lfp.automata.TipoToken;

/**
 *
 * @author huron_clinch
 */
public class Movimiento {

    private final int ESTADO_ORIGEN;
    private final int ESTADO_DESTINO;
    private final char CARACTER;
    private final TipoToken TIPO_TOKEN;
    private final String LEXEMA;

    public Movimiento(int estadoOrigen, int estadoDestino, char caracter, TipoToken tipoToken, String lexema) {
        this.ESTADO_ORIGEN = estadoOrigen;
        this.ESTADO_DESTINO = estadoDestino;
        this.CARACTER = caracter;
        this.TIPO_TOKEN = tipoToken;
        this.LEXEMA = lexema;
    }

    @Override
    public String toString() {
        if (LEXEMA != null && LEXEMA.startsWith("Guardando token")) {
            return LEXEMA; // Mensaje especial de cierre
        }
        return "Me moví del estado " + ESTADO_ORIGEN + " al estado " + ESTADO_DESTINO + " con una " + CARACTER + ",";
    }
}
