package com.example.tren.Servicios;

import com.example.tren.Entidades.Estacion;

import java.util.List;

public interface SerEstacion {

    public abstract List<Estacion> listarTodas();

    public abstract Estacion buscarEstacion(int id_estacion);

    public abstract int buscaridpornombre(String nombre);

    public abstract Estacion buscarPorOrden(int numero_orden_estacion);

    public int calcularTiempoLlegada(int posicionTren, int estacionObjetivo, boolean haciaAdelante);

    public int calcularTiempoViaje(int estacionOrigen, int estacionDestino, boolean direccionInicialTren,
                                   int posicionTren);

}
