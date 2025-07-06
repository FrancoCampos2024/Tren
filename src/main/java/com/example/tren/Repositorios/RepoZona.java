package com.example.tren.Repositorios;


import com.example.tren.Entidades.Zonaturistica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("zonaTurisRepository")
public interface RepoZona extends JpaRepository<Zonaturistica, Integer> {
    @Query(value = "SELECT * FROM zonaturistica WHERE estid = ?1", nativeQuery = true)
    List<Zonaturistica> findByEstacionId(int estacionId);
}
