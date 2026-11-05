package com.rpg.persistencia;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class GestorPersistencia {
    private static final String CARPETA_PARTIDAS = "partidas";
    private static final String ARCHIVO_LOG = "partidas/historial_combate.txt";
    private static final String ARCHIVO_DAT = "partidas/partida.dat";

    // Inicializar carpeta y archivos de prueba (Sección 1)
    public static void inicializarDirectorio() {
        File carpeta = new File(CARPETA_PARTIDAS);
        if (!carpeta.exists()) {
            carpeta.mkdirs();
        }

        // Demostración de rutas (Relativa vs Absoluta)
        File rel = new File(ARCHIVO_LOG);
        System.out.println("Ruta relativa: " + rel.getPath());
        System.out.println("Ruta absoluta: " + rel.getAbsolutePath());
    }

    // Escritura con BufferedWriter y FileWriter con opción de append (Sección 2)
    public static synchronized void guardarLogCombate(String textoRegistro, boolean anadir) {
        inicializarDirectorio();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARCHIVO_LOG, anadir))) {
            writer.write(textoRegistro);
            writer.newLine();
            writer.flush();
        } catch (IOException e) {
            System.out.println("Error al escribir el registro de combate: " + e.getMessage());
        }
    }

    // Lectura con BufferedReader (Sección 3)
    public static void leerLogCombate() {
        File archivo = new File(ARCHIVO_LOG);
        if (!archivo.exists()) {
            System.out.println("Aun no existe un historial de combate guardado.");
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
            String linea;
            System.out.println("\n=== HISTORIAL DE COMBATE LEIDO DESDE DISCO ===");
            while ((linea = reader.readLine()) != null) {
                System.out.println(linea);
            }
        } catch (FileNotFoundException e) {
            System.out.println("El archivo de log no fue encontrado.");
        } catch (IOException e) {
            System.out.println("Error de E/S al leer el archivo: " + e.getMessage());
        }
    }

    // Borrado de archivos (Sección 4)
    public static boolean borrarPartidaBinaria() {
        File archivo = new File(ARCHIVO_DAT);
        if (archivo.exists()) {
            return archivo.delete(); // Uso de File.delete()
        }
        return false;
    }

    public static void borrarConFiles(Path ruta) {
        try {
            Files.deleteIfExists(ruta); // Uso de Files.deleteIfExists
        } catch (IOException e) {
            System.out.println("No se pudo eliminar el archivo con Files: " + e.getMessage());
        }
    }

    // Serialización: Guardar objeto completo (Sección 5 y 7)
    public static synchronized void guardarObjetoPartida(Object objeto) {
        inicializarDirectorio();
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARCHIVO_DAT))) {
            oos.writeObject(objeto);
            System.out.println("[Partida guardada exitosamente en disco binario]");
        } catch (NotSerializableException e) {
            System.out.println("Error: Un objeto interno no implementa Serializable -> " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Error de E/S al serializar la partida: " + e.getMessage());
        }
    }

    // Serialización: Cargar objeto completo (Sección 5)
    public static Object cargarObjetoPartida() {
        File archivo = new File(ARCHIVO_DAT);
        if (!archivo.exists()) {
            System.out.println("No hay archivos de partida guardados en disco.");
            return null;
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(archivo))) {
            return ois.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("Archivo de guardado no encontrado.");
        } catch (ClassNotFoundException e) {
            System.out.println("Clase no encontrada al deserializar: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Error al leer el archivo de partida: " + e.getMessage());
        }
        return null;
    }
}