/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.p1_lfp.buscar;

import com.mycompany.p1_lfp.automata.editar.EditarFrontend;
import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author huron_clinch
 */
public class Buscar {

    private final EditarFrontend PANEL;

    public Buscar(EditarFrontend PANEL) {
        this.PANEL = PANEL;
    }

    public void buscarYResaltar() {
        String texto = PANEL.getTextoPanel().getText();
        String patron = PANEL.getTextoBusqueda().getText();//Texto a buscar 

        if (patron.isEmpty() || texto.isEmpty()) {
            return;
        }

        Highlighter highlighter = PANEL.getTextoPanel().getHighlighter();
        highlighter.removeAllHighlights();
        Highlighter.HighlightPainter painter = new DefaultHighlighter.DefaultHighlightPainter(Color.YELLOW);//Subrrallar palabras

        int index = texto.indexOf(patron);
        boolean encontrado = false;//Para mostrar un mensaje de no se enctro match

        while (index >= 0) {
            try {
                highlighter.addHighlight(index, index + patron.length(), painter);
                encontrado = true;//Si hay match
            } catch (BadLocationException ex) {
                ex.printStackTrace();
            }
            index = texto.indexOf(patron, index + patron.length());
        }

        if (!encontrado) {//No hay ningun match 
            JOptionPane.showMessageDialog(null, "No se encontró ninguna coincidencia para: \"" + patron + "\"");
        }
    }
}
