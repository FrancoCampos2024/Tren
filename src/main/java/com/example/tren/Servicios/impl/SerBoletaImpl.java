package com.example.tren.Servicios.impl;

import com.example.tren.Entidades.Boleta;
import com.example.tren.Repositorios.BoletaRepository;
import com.example.tren.Servicios.SerBoleta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SerBoletaImpl implements SerBoleta {

    @Autowired
    private BoletaRepository boletaRepository;

    @Override
    public void guardar(Boleta boleta) {
        boletaRepository.save(boleta);
    }

    @Override
    public Boleta buscarPorId(int id) {
        return boletaRepository.findById(id).orElse(null); // o lanza excepción si prefieres
    }
}

