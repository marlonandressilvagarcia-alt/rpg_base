package com.rpg.concurrency;

import com.rpg.entities.Personaje;
import com.rpg.entities.EstadoEfecto;

public class EfectoContinuoTask implements Runnable {
    private final Personaje personaje;
    private final EstadoEfecto efecto;
    private final long intervaloMs;

    public EfectoContinuoTask(Personaje personaje, EstadoEfecto efecto, long intervaloMs) {
        this.personaje = personaje;
        this.efecto = efecto;
        this.intervaloMs = intervaloMs;
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted() && personaje.estaVivo()) {
                Thread.sleep(intervaloMs);
                synchronized (personaje) {
                    if (efecto.esDanio()) {
                        personaje.recibirDanio(efecto.getValorPorTick());
                    } else {
                        personaje.curar(efecto.getValorPorTick());
                    }
                }
                efecto.consumirTurno();
                if (efecto.haTerminado()) {
                    break;
                }
            }
        } catch (InterruptedException e) {
            personaje.quitarEfecto(efecto.getNombre());
            Thread.currentThread().interrupt();
        }
    }
}