package com.rpg.concurrency;

import com.rpg.core.MotorJuego;

public class AutoGuardadoDaemon implements Runnable {
    private final MotorJuego motorJuego;
    private final long intervaloMs;

    public AutoGuardadoDaemon(MotorJuego motorJuego, long intervaloMs) {
        this.motorJuego = motorJuego;
        this.intervaloMs = intervaloMs;
    }

    @Override
    public void run() {
        try {
            while (true) {
                Thread.sleep(intervaloMs);
                motorJuego.guardarProgreso();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}