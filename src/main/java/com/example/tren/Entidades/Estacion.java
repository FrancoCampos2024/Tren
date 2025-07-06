package com.example.tren.Entidades;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Estacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Estid")
    private int id_estacion;
    @Column(name = "Estnombre", length = 30)
    private String nombre_estacion;

    @Column(name = "Estnumero")
    private int ordenEstacion;

    @OneToMany(mappedBy = "estacion")
    private List<Zonaturistica> zonas;
}
