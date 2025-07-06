package com.example.tren.Repositorios;

import com.example.tren.Entidades.Boleta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoletaRepository extends JpaRepository<Boleta, Integer> {
}
