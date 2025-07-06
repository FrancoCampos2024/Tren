package com.example.tren.Servicios;

import com.example.tren.Entidades.Boleta;

public interface SerBoleta {
    void guardar(Boleta boleta);
    Boleta buscarPorId(int id);
}
