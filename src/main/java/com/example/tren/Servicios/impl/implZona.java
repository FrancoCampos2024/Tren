package com.example.tren.Servicios.impl;


import com.example.tren.Entidades.Zonaturistica;
import com.example.tren.Repositorios.RepoZona;
import com.example.tren.Servicios.SerZona;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("zonaTurisService")
public class implZona implements SerZona {

    @Autowired
    @Qualifier("zonaTurisRepository")
    private RepoZona zonaRepo;

    @Override
    public List<Zonaturistica> buscarPorEstacion(int estacionId) {
        if (estacionId == 0) {
            return zonaRepo.findAll();
        }
        return zonaRepo.findByEstacionId(estacionId);
    }

    @Override
    public Zonaturistica buscarPoridZona(int id) {
        return zonaRepo.findById(id).orElse(null);
    }

    @Override
    public List<Zonaturistica> listazonas() {
        return zonaRepo.findAll();
    }
}
