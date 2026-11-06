package com.rpg.controller;

import com.rpg.dao.HeroeDAO;
import com.rpg.model.Heroe;

import java.util.List;

public class HeroeController {
    private HeroeDAO heroeDAO;

    public HeroeController() {
        this.heroeDAO = new HeroeDAO();
    }

    // Registrar héroe con validaciones de negocio
    public String registrarHeroe(String nombre, String tipo, int vidaMaxima, int nivel) {
        if (nombre == null || nombre.trim().isEmpty()) {
            return "Error: El nombre del héroe no puede estar vacío.";
        }
        if (nivel < 1) {
            return "Error: El nivel debe ser mayor o igual a 1.";
        }
        if (!tipo.equalsIgnoreCase("Guerrero") && !tipo.equalsIgnoreCase("Mago")) {
            return "Error: El tipo debe ser 'Guerrero' o 'Mago'.";
        }

        Heroe heroe = new Heroe(nombre.trim(), tipo, vidaMaxima, nivel);
        boolean exito = heroeDAO.insertar(heroe);

        if (exito) {
            return "¡Héroe registrado exitosamente!";
        } else {
            return "Error: No se pudo registrar el héroe (el nombre ya podría existir).";
        }
    }

    // Listar todos los héroes
    public List<Heroe> listarHeroes() {
        return heroeDAO.listar();
    }

    // Buscar por tipo
    public List<Heroe> buscarPorTipo(String tipo) {
        return heroeDAO.buscarPorTipo(tipo);
    }

    // Actualizar nivel validando filas afectadas
    public String actualizarNivel(int id, int nuevoNivel) {
        if (nuevoNivel < 1) {
            return "Error: El nivel debe ser al menos 1.";
        }
        int filas = heroeDAO.actualizarNivel(id, nuevoNivel);
        if (filas == 0) {
            return "No existe un héroe con ese id.";
        }
        return "Nivel actualizado correctamente.";
    }

    // Eliminar héroe validando filas afectadas
    public String eliminarHeroe(int id) {
        int filas = heroeDAO.eliminar(id);
        if (filas == 0) {
            return "No existe un héroe con ese id.";
        }
        return "Héroe eliminado correctamente.";
    }
}