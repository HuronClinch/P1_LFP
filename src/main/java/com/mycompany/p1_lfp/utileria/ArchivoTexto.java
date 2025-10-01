/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.p1_lfp.utileria;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;
import javax.swing.JOptionPane;

/**
 *
 * @author huron_clinch
 */
public class ArchivoTexto {

    private final String PATH_COMPLETO;

    public ArchivoTexto(String PATH_COMPLETO) {
        this.PATH_COMPLETO = PATH_COMPLETO;
    }

    public String leerTextoConScanner() {
        String texto = ""; // Aquí guardaremos todo el contenido

        try (InputStream inputStream = Files.newInputStream(Path.of(PATH_COMPLETO)); Scanner scanner = new Scanner(inputStream)) {

            while (scanner.hasNextLine()) {//Leer línea por línea y concatenar con salto de línea
                texto += scanner.nextLine() + "\n";
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error al cargar archivo");
            e.printStackTrace();
        }
        return texto;
    }
}
