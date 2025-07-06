package com.example.tren.Servicios.impl;


import com.example.tren.Entidades.Estacion;
import com.example.tren.Repositorios.RepoEstacion;
import com.example.tren.Servicios.SerEstacion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("estacionService")
public class implEstacion implements SerEstacion {

    @Autowired
    @Qualifier("estacionRepository")
    private RepoEstacion estacionRepository;

    private static final int SEGUNDOS_POR_ESTACION = 2; // 3s viaje + 2s espera
    private static final int ESTACION_INICIO = 1;
    private static final int ESTACION_FINAL = 14;

    @Override
    public List<Estacion> listarTodas() {
        return estacionRepository.findAll(Sort.by("ordenEstacion"));
    }

    @Override
    public Estacion buscarEstacion(int id_estacion) {
        return estacionRepository.findById(id_estacion).orElse(null);
    }

    @Override
    public int buscaridpornombre(String nombre) {
        return estacionRepository.obtenerIdPorNombre(nombre);
    }

    @Override
    public Estacion buscarPorOrden(int numero_orden_estacion) {
        return estacionRepository.findAll().stream()
                .filter(e -> e.getOrdenEstacion() == numero_orden_estacion)
                .findFirst()
                .orElse(null);
    }

    @Override
    public int calcularTiempoLlegada(int posicionTren, int estacionObjetivo, boolean haciaAdelante) {
        if (posicionTren == estacionObjetivo)
            return 0;

        boolean puedeDirecto = (haciaAdelante && estacionObjetivo > posicionTren) ||
                (!haciaAdelante && estacionObjetivo < posicionTren);

        if (puedeDirecto) {
            return Math.abs(estacionObjetivo - posicionTren - 1) * SEGUNDOS_POR_ESTACION;
        } else {
            int hastaExtremo = haciaAdelante ? (ESTACION_FINAL - posicionTren) : (posicionTren - ESTACION_INICIO);
            int desdeExtremo = haciaAdelante ? (ESTACION_FINAL - estacionObjetivo)
                    : (estacionObjetivo - ESTACION_INICIO);
            return (hastaExtremo + desdeExtremo - 1) * SEGUNDOS_POR_ESTACION;
        }
    }

    @Override
    public int calcularTiempoViaje(int origen, int destino, boolean direccionTren, int posicionTren) {
        boolean daraLaVueltaAntesDeRecoger = (direccionTren && origen < posicionTren)
                || (!direccionTren && origen > posicionTren);

        boolean viajeEnMismoSentido = origen > destino; // VUELTA
        if (daraLaVueltaAntesDeRecoger) {
            // el tren dará la vuelta → entonces tu viaje será en sentido opuesto
            viajeEnMismoSentido = !viajeEnMismoSentido;
        }

        if (viajeEnMismoSentido) {
            return Math.abs(destino - origen) * SEGUNDOS_POR_ESTACION;
        } else {
            // vuelta necesaria para llegar
            int hastaExtremo = origen - ESTACION_INICIO;
            int desdeExtremo = destino - ESTACION_INICIO;
            return (hastaExtremo + desdeExtremo) * SEGUNDOS_POR_ESTACION;
        }
    }

}
