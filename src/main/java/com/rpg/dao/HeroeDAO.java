package com.rpg.dao;

import com.rpg.model.Heroe;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HeroeDAO {

    // 1. Insertar un nuevo héroe -> usa executeUpdate
    public boolean insertar(Heroe heroe) {
        String sql = "INSERT INTO heroes (nombre, tipo, vida_maxima, nivel) VALUES (?, ?, ?, ?)";
        try (Connection conn = ConexionDB.obtenerConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, heroe.getNombre());
            pstmt.setString(2, heroe.getTipo());
            pstmt.setInt(3, heroe.getVidaMaxima());
            pstmt.setInt(4, heroe.getNivel());

            int filasAfectadas = pstmt.executeUpdate();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            System.err.println("Error al insertar heroe: " + e.getMessage());
            return false;
        }
    }

    // 2. Listar todos los héroes -> usa executeQuery + while (rs.next())
    public List<Heroe> listar() {
        List<Heroe> heroes = new ArrayList<>();
        String sql = "SELECT id, nombre, tipo, vida_maxima, nivel FROM heroes";

        try (Connection conn = ConexionDB.obtenerConexion();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Heroe heroe = new Heroe(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("tipo"),
                        rs.getInt("vida_maxima"),
                        rs.getInt("nivel")
                );
                heroes.add(heroe);
            }
        } catch (SQLException e) {
            System.err.println("Error al listar heroes: " + e.getMessage());
        }

        return heroes;
    }

    // 3. Buscar por tipo -> usa executeQuery con parámetro ?
    public List<Heroe> buscarPorTipo(String tipo) {
        List<Heroe> heroes = new ArrayList<>();
        String sql = "SELECT id, nombre, tipo, vida_maxima, nivel FROM heroes WHERE tipo = ?";

        try (Connection conn = ConexionDB.obtenerConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, tipo);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Heroe heroe = new Heroe(
                            rs.getInt("id"),
                            rs.getString("nombre"),
                            rs.getString("tipo"),
                            rs.getInt("vida_maxima"),
                            rs.getInt("nivel")
                    );
                    heroes.add(heroe);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar heroes por tipo: " + e.getMessage());
        }

        return heroes;
    }

    // 4. Actualizar nivel -> devuelve el número de filas afectadas
    public int actualizarNivel(int id, int nuevoNivel) {
        String sql = "UPDATE heroes SET nivel = ? WHERE id = ?";

        try (Connection conn = ConexionDB.obtenerConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, nuevoNivel);
            pstmt.setInt(2, id);

            return pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al actualizar nivel: " + e.getMessage());
            return 0;
        }
    }

    // 5. Eliminar héroe por id -> devuelve el número de filas afectadas
    public int eliminar(int id) {
        String sql = "DELETE FROM heroes WHERE id = ?";

        try (Connection conn = ConexionDB.obtenerConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);

            return pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al eliminar heroe: " + e.getMessage());
            return 0;
        }
    }
}