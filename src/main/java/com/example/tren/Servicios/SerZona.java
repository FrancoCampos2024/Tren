package com.example.tren.Servicios;

import com.example.tren.Entidades.Zonaturistica;

import java.util.List;

public interface SerZona {

    public abstract List<Zonaturistica> buscarPorEstacion(int estacionId);
    public abstract Zonaturistica buscarPoridZona(int id);
    public abstract List<Zonaturistica> listazonas();

}
