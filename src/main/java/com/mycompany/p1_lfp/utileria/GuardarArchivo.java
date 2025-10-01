/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.p1_lfp.utileria;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 *
 * @author huron_clinch
 */
public class GuardarArchivo {

    private final String PATH_DIRECTORIO = "Reportes/";//Carpeta donde se guarda
    private final String NOMBRE_ARCHIVO;// Nombre del archivo
    private final String PATH_COMPLETO;// Carpeta + nombre

    public GuardarArchivo(String nombreArchivo) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
        String fechaActual = LocalDateTime.now().format(formatter);

        int punto = nombreArchivo.lastIndexOf(".");//Definir datos antes de la extension
        if (punto != -1) {
            String base = nombreArchivo.substring(0, punto);
            String extension = nombreArchivo.substring(punto);
            this.NOMBRE_ARCHIVO = base + "_" + fechaActual + extension;
        } else {//Por defecto .txt
            this.NOMBRE_ARCHIVO = nombreArchivo + "_" + fechaActual + ".txt";
        }

        this.PATH_COMPLETO = this.PATH_DIRECTORIO + this.NOMBRE_ARCHIVO;

        File directorio = new File(this.PATH_DIRECTORIO);
        if (!directorio.exists()) {//Crear directorio si no exixte
            directorio.mkdirs();
        }
    }

    public void leerTextoConScanner() {
        try (InputStream inputStream = Files.newInputStream(Path.of(PATH_COMPLETO))) {
            Scanner scanner = new Scanner(inputStream);
            while (scanner.hasNextLine()) {
                System.out.println(scanner.nextLine());
            }
        } catch (NoSuchElementException e) {
            System.out.println("Llegamos al fin del archivo");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void escribirConWriter(String texto) {
        File miArchivo = new File(PATH_COMPLETO);
        try (FileWriter writer = new FileWriter(miArchivo)) {
            writer.write(texto);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void escribirConPrinter(String texto) {
        File miArchivo = new File(PATH_COMPLETO);
        try (FileWriter fileWriter = new FileWriter(miArchivo, true); PrintWriter writer = new PrintWriter(fileWriter)) {
            writer.println(texto);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
