/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.p1_lfp.automata.editar;

import com.mycompany.p1_lfp.automata.Automata;
import com.mycompany.p1_lfp.automata.Token;
import com.mycompany.p1_lfp.buscar.Buscar;
import com.mycompany.p1_lfp.generacion_automata.Movimiento;
import com.mycompany.p1_lfp.reporte.ReporteFrontend;
import com.mycompany.p1_lfp.utileria.ArchivoTexto;
import com.mycompany.p1_lfp.utileria.GuardarArchivo;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 *
 * @author huron_clinch
 */
public class EditarBackend {

    private final EditarFrontend PANEL;
    private final Buscar buscarXPalabra;

    //Analizar automata
    private Automata automata = new Automata();
    private List<Token> ultimosTokens = new ArrayList<>();

    public EditarBackend(EditarFrontend PANEL) {
        this.PANEL = PANEL;
        buscarXPalabra = new Buscar(PANEL);
    }

    protected void colorear() {//Colorear segun los cambios
        PANEL.getTextoPanel().addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                analizarTexto();
                actualizarPosicion();
            }
        });
    }

    protected void analizarTexto() {//Analizar texto para colorear por token
        String contenido = PANEL.getTextoPanel().getText();//Obtener contenido de panel
        ultimosTokens = automata.analizar(contenido);
        List<Token> tokens = automata.analizar(contenido);//Analizar token

        StyledDocument textAnalizado = PANEL.getTextoPanel().getStyledDocument();
        textAnalizado.setCharacterAttributes(0, contenido.length(), PANEL.getTextoPanel().getStyle(StyleContext.DEFAULT_STYLE), true);

        for (Token token : tokens) {//Segun el tipo token pintar
            Style style = PANEL.getTextoPanel().addStyle(token.getTipo().name(), null);
            switch (token.getTipo()) {
                case PALABRA_RESERVADA ->
                    StyleConstants.setForeground(style, Color.BLUE);
                case IDENTIFICADOR ->//Cafe
                    StyleConstants.setForeground(style, new Color(139, 69, 19));
                case NUMERO ->
                    StyleConstants.setForeground(style, Color.GREEN);
                case DECIMAL ->
                    StyleConstants.setForeground(style, Color.BLACK);
                case OPERADOR ->
                    StyleConstants.setForeground(style, Color.ORANGE);
                case AGRUPACION ->//Morado
                    StyleConstants.setForeground(style, new Color(128, 0, 128));
                case COMENTARIO_LINEA, COMENTARIO_BLOQUE ->//Verde oscuro
                    StyleConstants.setForeground(style, new Color(0, 100, 0));
                case ERROR ->
                    StyleConstants.setForeground(style, Color.RED);
            }
            try {
                textAnalizado.setCharacterAttributes(token.getInicio(), token.getLexema().length(), style, true);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        StringBuilder sb = new StringBuilder("Movimientos del autómata:\n");//Mostrar los movimientos del automata
        for (Movimiento m : automata.getMovimientos()) {
            sb.append(m.toString()).append("\n");
        }
        PANEL.getVisorMovimientos().setText(sb.toString());
    }

    private void actualizarPosicion() {
        int pos = PANEL.getTextoPanel().getCaretPosition();
        int fila = 0;
        int columna = 0;

        try {
            String texto = PANEL.getTextoPanel().getText(0, pos);
            for (int i = 0; i < texto.length(); i++) {
                if (texto.charAt(i) == '\n') {
                    fila++;
                    columna = 0;
                } else {
                    columna++;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        PANEL.getInformacionPosicion().setText("Fila: " + fila + ", Columna: " + columna);
    }

    protected void mostrarReporteTokens() {//Mostrar reporte para tokens
        if (ultimosTokens != null) {
            ReporteFrontend reporte = new ReporteFrontend(PANEL.getTextoPanel().getText(), true, ultimosTokens);
            reporte.setVisible(true);
        }
    }

    protected void mostrarReporteErrores() {//Mostrar reporte para tokens malos
        if (ultimosTokens != null) {
            ReporteFrontend reporte = new ReporteFrontend(PANEL.getTextoPanel().getText(), false, ultimosTokens);
            reporte.setVisible(true);
        }
    }

    protected void buscarPatron() {//Funcion para bucar por patron
        buscarXPalabra.buscarYResaltar();
    }

    protected void subirTexto() {
        JFileChooser archivoSeleccionado = new JFileChooser();
        File archivo;
        if (archivoSeleccionado.showDialog(null, "Abrir") == JFileChooser.APPROVE_OPTION) {
            archivo = archivoSeleccionado.getSelectedFile();//obtener la direccion del archivo
            String path = "" + archivo;//Guardar la direccion en archivo 
            ArchivoTexto leerArchivo = new ArchivoTexto((String) path);//Crear path para el archivo
            PANEL.getTextoPanel().setText(leerArchivo.leerTextoConScanner());
            leerArchivo.leerTextoConScanner();//Leer archivo
        }
        analizarTexto();
    }

    protected void exportarTextoEdicion() {
        String codigo = PANEL.getTextoPanel().getText();
        if (codigo.trim().length() == 0) {//No hay texto
            JOptionPane.showMessageDialog(null, """
                                                No se puede exportar codigo
                                                No tiene codigo escrito""");
            return;
        }
        GuardarArchivo archivo = new GuardarArchivo("Codigo.txt");//Si no hay errores guardar
        archivo.escribirConWriter(codigo);
        JOptionPane.showMessageDialog(null, "Se exporto correctamente el codigo escrito");
    }
}
