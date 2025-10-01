/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.p1_lfp.reporte;

import com.mycompany.p1_lfp.automata.TipoToken;
import com.mycompany.p1_lfp.automata.Token;
import java.util.List;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author huron_clinch
 */
public class ReporteBackend {

    private final ReporteFrontend PANEL;
    private final List<Token> LISTA;

    public ReporteBackend(ReporteFrontend PANEL, boolean tokenValido, List<Token> LISTA) {
        this.PANEL = PANEL;
        this.LISTA = LISTA;
        if (tokenValido) {//Si son tokens validos:
            modeloTokenValido();
        } else {
            modeloTokenInalido();
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

    private void definirNoModificable() {
        DefaultTableModel modeloF = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
    }
}
