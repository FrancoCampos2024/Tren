package com.example.tren.Entidades;

public class Clima {

    private int temperatura;
    private String descripcion;

    public Clima(int temperatura, String descripcion) {
        this.temperatura = temperatura;
        this.descripcion = descripcion;
    }

    public int getTemperatura() {
        return temperatura;
    }

    public String getDescripcion() {
        return descripcion;
    }

    @Override
    public String toString() {
        return temperatura + "°C, " + descripcion;
    }

}
