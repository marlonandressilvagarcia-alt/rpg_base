package com.rpg.dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionDB {

    private static String url = "";
    private static String usuario = "";
    private static String clave = "";
    private static boolean configurado = false;

    // Metodo privado para leer el archivo config.txt al iniciar la clase
    private static void cargarConfiguracion() {
        if (configurado) return;

        File archivo = new File("config.txt");

        if (!archivo.exists()) {
            System.out.println("Error: No se encontro el archivo config.txt en la raiz del proyecto.");
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                linea = linea.trim();
                // Ignorar lineas vacias o comentarios
                if (linea.isEmpty() || linea.startsWith("#")) {
                    continue;
                }

                String[] partes = linea.split("=", 2);
                if (partes.length == 2) {
                    String claveConfig = partes[0].trim();
                    String valorConfig = partes[1].trim();

                    switch (claveConfig) {
                        case "url":
                            url = valorConfig;
                            break;
                        case "usuario":
                            usuario = valorConfig;
                            break;
                        case "clave":
                            clave = valorConfig;
                            break;
                    }
                }
            }
            configurado = true;
        } catch (IOException e) {
            System.out.println("Error al leer el archivo de configuracion: " + e.getMessage());
        }
    }

    // Metodo publico para obtener la conexion usando JDBC
    public static Connection obtenerConexion() {
        cargarConfiguracion();

        if (url.isEmpty() || usuario.isEmpty()) {
            System.out.println("Error: Faltan datos de conexion en el archivo config.txt.");
            return null;
        }

        try {
            return DriverManager.getConnection(url, usuario, clave);
        } catch (SQLException e) {
            System.out.println("Error de conexion a la base de datos: Verifique que el servidor este activo y las credenciales sean correctas.");
            return null;
        }
    }
}