/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.p1_lfp.reporte;

import com.mycompany.p1_lfp.automata.TipoToken;
import com.mycompany.p1_lfp.automata.Token;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author huron_clinch
 */
public class ReporteBackend {

    private final ReporteFrontend PANEL;
    private final List<Token> LISTA;

    public ReporteBackend(ReporteFrontend PANEL, int tokenValido, List<Token> LISTA) {
        this.PANEL = PANEL;
        this.LISTA = LISTA;
        if (tokenValido == 0) {//Si son tokens validos:
            modeloTokenValido();
        } else if (tokenValido == 1) {//Si los tokens no son vlaidos
            modeloTokenInalido();
        } else {//Si es recuento de lexemas
            mostrarRecuentoLexemas();
        }
    }

    private void modeloTokenValido() {
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("Nombre del Token");
        modelo.addColumn("Lexema");
        modelo.addColumn("Posicion X");
        modelo.addColumn("Posicion Y");
        for (Token token : LISTA) {
            if (token.getTipo() != TipoToken.ERROR) {
                modelo.addRow(new Object[]{
                    token.getTipo(),
                    token.getLexema(),
                    token.getFila(),
                    token.getColumna()
                });
            }
        }
        PANEL.getTablaRepo().setModel(modelo);
    }

    private void modeloTokenInalido() {
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("Simbolo o Cadena de Error");
        modelo.addColumn("Posicion X");
        modelo.addColumn("Posicion Y");
        modelo.addColumn("Descripción");
        for (Token token : LISTA) {
            if (token.getTipo() == TipoToken.ERROR) {
                modelo.addRow(new Object[]{
                    token.getLexema(),
                    token.getFila(),
                    token.getColumna(),
                    "Caracter no esperado"
                });
            }
        }
        PANEL.getTablaRepo().setModel(modelo);
    }

    private void mostrarRecuentoLexemas() {//hacer y mostrar recuentos de lexemas
        List<Object[]> recuento = new ArrayList<>();//[lexema, tipo, cantidad]

        for (Token t : LISTA) {//Segun la lista
            boolean encontrado = false;//Si ya esta en la analziado en la lista
            for (Object[] fila : recuento) {
                String lexemaExistente = (String) fila[0];
                TipoToken tipoExistente = (TipoToken) fila[1];

                if (lexemaExistente.equals(t.getLexema()) && tipoExistente == t.getTipo()) {
                    fila[2] = (Integer) fila[2] + 1; // incrementar cantidad
                    encontrado = true;
                    break;
                }
            }
            if (!encontrado) {//Guardar en la lista
                recuento.add(new Object[]{t.getLexema(), t.getTipo(), 1});
            }
        }

        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("Lexema");
        modelo.addColumn("Tipo de Token");
        modelo.addColumn("Cantidad");
        for (Object[] fila : recuento) {
            modelo.addRow(fila);
        }
        PANEL.getTablaRepo().setModel(modelo);
    }

    private void definirNoModificable() {
        DefaultTableModel modeloF = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
    }
}
