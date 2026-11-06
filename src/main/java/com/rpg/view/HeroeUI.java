package com.rpg.view;

import com.rpg.controller.HeroeController;
import com.rpg.model.Heroe;

import java.util.List;
import java.util.Scanner;

public class HeroeUI {
    private HeroeController controller;
    private Scanner scanner;

    public HeroeUI() {
        this.controller = new HeroeController();
        this.scanner = new Scanner(System.in);
    }

    public void iniciar() {
        int opcion = 0;
        do {
            System.out.println("\n--- GESTIÓN DE HÉROES - RPG ---");
            System.out.println("1. Registrar héroe");
            System.out.println("2. Listar todos los héroes");
            System.out.println("3. Buscar héroes por tipo (Guerrero/Mago)");
            System.out.println("4. Actualizar nivel de un héroe");
            System.out.println("5. Eliminar héroe");
            System.out.println("6. Salir");
            System.out.print("Seleccione una opción: ");

            try {
                opcion = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Error: Por favor ingrese un número válido.");
                continue;
            }

            switch (opcion) {
                case 1:
                    registrar();
                    break;
                case 2:
                    listar();
                    break;
                case 3:
                    buscarPorTipo();
                    break;
                case 4:
                    actualizarNivel();
                    break;
                case 5:
                    eliminar();
                    break;
                case 6:
                    System.out.println("Saliendo del sistema...");
                    break;
                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
            }
        } while (opcion != 6);
    }

    private void registrar() {
        System.out.print("Nombre del héroe: ");
        String nombre = scanner.nextLine();

        System.out.print("Tipo (Guerrero / Mago): ");
        String tipo = scanner.nextLine();

        System.out.print("Vida máxima: ");
        int vidaMaxima = Integer.parseInt(scanner.nextLine());

        System.out.print("Nivel inicial: ");
        int nivel = Integer.parseInt(scanner.nextLine());

        String resultado = controller.registrarHeroe(nombre, tipo, vidaMaxima, nivel);
        System.out.println(resultado);
    }

    private void listar() {
        List<Heroe> heroes = controller.listarHeroes();
        System.out.println("\n--- LISTA DE HÉROES ---");
        if (heroes.isEmpty()) {
            System.out.println("No hay héroes registrados.");
        } else {
            for (Heroe h : heroes) {
                System.out.println(h);
            }
        }
    }

    private void buscarPorTipo() {
        System.out.print("Ingrese el tipo a buscar (Guerrero / Mago): ");
        String tipo = scanner.nextLine();
        List<Heroe> heroes = controller.buscarPorTipo(tipo);

        System.out.println("\n--- RESULTADOS PARA: " + tipo + " ---");
        if (heroes.isEmpty()) {
            System.out.println("No se encontraron héroes de ese tipo.");
        } else {
            for (Heroe h : heroes) {
                System.out.println(h);
            }
        }
    }

    private void actualizarNivel() {
        System.out.print("Ingrese el ID del héroe a actualizar: ");
        int id = Integer.parseInt(scanner.nextLine());

        System.out.print("Ingrese el nuevo nivel: ");
        int nuevoNivel = Integer.parseInt(scanner.nextLine());

        String resultado = controller.actualizarNivel(id, nuevoNivel);
        System.out.println(resultado);
    }

    private void eliminar() {
        System.out.print("Ingrese el ID del héroe a eliminar: ");
        int id = Integer.parseInt(scanner.nextLine());

        String resultado = controller.eliminarHeroe(id);
        System.out.println(resultado);
    }
}