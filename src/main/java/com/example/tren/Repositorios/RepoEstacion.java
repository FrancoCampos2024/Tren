package com.example.tren.Repositorios;


import com.example.tren.Entidades.Estacion;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository("estacionRepository")
public interface RepoEstacion extends JpaRepository<Estacion, Integer> {

    default Optional<Estacion> findById(Long id) {
        return findById(Integer.valueOf(id.intValue()));
    }

    @Query("SELECT e.ordenEstacion FROM Estacion e WHERE e.nombre_estacion = :nombre")
    Integer obtenerIdPorNombre(@Param("nombre") String nombre);
}
