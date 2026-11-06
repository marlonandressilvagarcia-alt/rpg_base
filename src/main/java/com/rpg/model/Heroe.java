package com.rpg.model;

public class Heroe {
    private int id;
    private String nombre;
    private String tipo; // 'Guerrero' o 'Mago'
    private int vidaMaxima;
    private int nivel;

    // Constructor vacío
    public Heroe() {
    }

    // Constructor para insertar nuevos héroes (sin ID ya que es Auto_Increment)
    public Heroe(String nombre, String tipo, int vidaMaxima, int nivel) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.vidaMaxima = vidaMaxima;
        this.nivel = nivel;
    }

    // Constructor completo (para cuando se cargan desde la BD)
    public Heroe(int id, String nombre, String tipo, int vidaMaxima, int nivel) {
        this.id = id;
        this.nombre = nombre;
        this.tipo = tipo;
        this.vidaMaxima = vidaMaxima;
        this.nivel = nivel;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getVidaMaxima() {
        return vidaMaxima;
    }

    public void setVidaMaxima(int vidaMaxima) {
        this.vidaMaxima = vidaMaxima;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    @Override
    public String toString() {
        return "Heroe [ID=" + id + ", Nombre=" + nombre + ", Tipo=" + tipo +
                ", Vida Máxima=" + vidaMaxima + ", Nivel=" + nivel + "]";
    }
}