package com.rpg.core;

import com.rpg.entities.Escuadron;
import com.rpg.entities.Guerrero;
import com.rpg.entities.Mago;
import com.rpg.entities.Personaje;

import java.util.List;

/** Torneo de 4 gladiadores: dos semifinales y una final. */
public class TorneoManager {

    private final Arena arena;
    private final RegistroCombate registro;

    public TorneoManager(Arena arena, RegistroCombate registro) {
        this.arena = arena;
        this.registro = registro;
    }

    /** @return el campeon del torneo */
    public Personaje jugarTorneo() {
        List<Personaje> gladiadores = crearGladiadores().getMiembros();

        // Usamos arrays de 1 elemento para poder capturar el ganador dentro del hilo
        final Personaje[] finalista1 = new Personaje[1];
        final Personaje[] finalista2 = new Personaje[1];

        Thread semi1 = new Thread(() -> {
            finalista1[0] = simularSemifinal(gladiadores.get(0), gladiadores.get(1));
        });

        Thread semi2 = new Thread(() -> {
            finalista2[0] = simularSemifinal(gladiadores.get(2), gladiadores.get(3));
        });

        // 1. Estado antes de arrancar (Debe ser NEW)
        System.out.println("Estado antes de start -> semi1: " + semi1.getState() + " | semi2: " + semi2.getState());

        semi1.start();
        semi2.start();

        // 2. Estado durante la ejecución (Debe ser RUNNABLE o TIMED_WAITING)
        System.out.println("Estado durante ejecución -> semi1: " + semi1.getState() + " | semi2: " + semi2.getState());

        try {
            // El hilo principal espera a que ambas semifinales terminen
            semi1.join();
            semi2.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // 3. Estado después del join (Debe ser TERMINATED)
        System.out.println("Estado después de join -> semi1: " + semi1.getState() + " | semi2: " + semi2.getState());

        // La final solo empieza cuando ambos hilos terminaron y tenemos los ganadores
        return simularFinal(finalista1[0], finalista2[0]);
    }

    public Personaje simularSemifinal(Personaje a, Personaje b) {
        registro.agregar("=== SEMIFINAL: %s vs %s ===", a.getNombre(), b.getNombre());
        Personaje ganador = arena.duelo(a, b);
        registro.agregar("Clasifica a la final: %s", ganador.getNombre());
        return ganador;
    }

    private Personaje simularFinal(Personaje a, Personaje b) {
        a.restaurarVida();
        b.restaurarVida();
        registro.agregar("=== FINAL: %s vs %s ===", a.getNombre(), b.getNombre());
        return arena.duelo(a, b);
    }

    private Escuadron crearGladiadores() {
        Escuadron escuadron = new Escuadron("Gladiadores");
        escuadron.agregar(new Guerrero("Maximo"));
        escuadron.agregar(new Mago("Circe"));
        escuadron.agregar(new Guerrero("Aquiles"));
        escuadron.agregar(new Mago("Merlina"));
        return escuadron;
    }
}
