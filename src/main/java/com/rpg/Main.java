package com.rpg;

import com.rpg.core.Consola;
import com.rpg.core.MotorJuego;
import com.rpg.persistencia.GestorPersistencia;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            Consola consola = new Consola(scanner);
            MotorJuego motor = new MotorJuego(consola);
            ejecutarMenu(consola, motor);
        } catch (NoSuchElementException e) {
            System.out.println("\nEntrada finalizada. Hasta luego.");
        }
    }

    private static void ejecutarMenu(Consola consola, MotorJuego motor) {
        int opcion;
        do {
            mostrarMenu();
            // Ampliado para el Taller de Archivos (Opciones 4 a 7)
            opcion = consola.leerEntero("Opcion: ", 0, 7);
            switch (opcion) {
                case 1 -> motor.combateRapido();
                case 2 -> motor.jugarTorneo();
                case 3 -> motor.mostrarBestiario();
                case 4 -> {
                    // TALLER ARCHIVOS: Cargar partida serializada (.dat)
                    Object obj = GestorPersistencia.cargarObjetoPartida();
                    if (obj instanceof MotorJuego motorCargado) {
                        System.out.println("[Partida cargada exitosamente desde disco]");
                        // Si deseas reemplazar la instancia actual, puedes continuar con el motor cargado
                    }
                }
                case 5 -> {
                    // TALLER ARCHIVOS: Leer historial de combate con BufferedReader
                    GestorPersistencia.leerLogCombate();
                }
                case 6 -> {
                    // TALLER ARCHIVOS: Mostrar info de archivos con File (exists, length, getName, rutas)
                    mostrarInformacionArchivos();
                }
                case 7 -> {
                    // TALLER ARCHIVOS: Borrar partida guardada con confirmación previa
                    String confirmar = consola.leerLinea("¿Estas seguro de borrar la partida guardada? (s/n): ");
                    if (confirmar.equalsIgnoreCase("s")) {
                        boolean borrado = GestorPersistencia.borrarPartidaBinaria();
                        if (borrado) {
                            System.out.println("Partida borrada correctamente usando File.delete().");
                        } else {
                            System.out.println("No se encontro ninguna partida para borrar.");
                        }
                    } else {
                        System.out.println("Operacion cancelada.");
                    }
                }
                default -> System.out.println("Hasta luego.");
            }
        } while (opcion != 0);
    }

    private static void mostrarMenu() {
        System.out.println("\n=== SIMULADOR DE BATALLA RPG ===");
        System.out.println("1. Combate rapido");
        System.out.println("2. Torneo");
        System.out.println("3. Ver bestiario");
        System.out.println("4. Cargar partida guardada");
        System.out.println("5. Ver historial de combate (Archivo txt)");
        System.out.println("6. Ver informacion de archivos de disco");
        System.out.println("7. Borrar partida guardada");
        System.out.println("0. Salir");
    }

    private static void mostrarInformacionArchivos() {
        File archivoDat = new File("partidas/partida.dat");
        System.out.println("\n=== INFORMACION DE ARCHIVOS DE PARTIDA ===");
        if (archivoDat.exists()) {
            System.out.println("Nombre del archivo: " + archivoDat.getName());
            System.out.println("Ruta relativa: " + archivoDat.getPath());
            System.out.println("Ruta absoluta: " + archivoDat.getAbsolutePath());
            System.out.println("Tamano en bytes: " + archivoDat.length());
        } else {
            System.out.println("El archivo de datos de la partida aun no ha sido creado.");
        }
    }
}