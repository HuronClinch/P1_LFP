/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.p1_lfp.automata;

import java.util.List;

/**
 *
 * @author huron_clinch
 */
public class Lenguaje {

    private final List<String> PALABRAS_RESERVADAS = List.of(
            "SI", "ENTONCES", "PARA", "ESCRIBIR",
            "si", "entonces", "para", "escribir"
    );

    private final List<Character> OPERADORES = List.of(
            '+', '-', '*', '/', '%', '='
    );

    private final List<Character> AGRUPACIONES = List.of(
            '(', '[', '{', ')', ']', '}'
    );

    public List<String> getPALABRAS_RESERVADAS() {
        return PALABRAS_RESERVADAS;
    }

    public List<Character> getOPERADORES() {
        return OPERADORES;
    }

    public List<Character> getAGRUPACIONES() {
        return AGRUPACIONES;
    }
}
