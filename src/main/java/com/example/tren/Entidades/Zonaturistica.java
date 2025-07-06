package com.example.tren.Entidades;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Zonaturistica {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Zntid")
    private int id;

    @Column(name = "Zntnombre", length = 50)
    private String nombre;

    @Column(name = "Zntdireccion", length = 100)
    private String direccion;

    @Column(name = "Zntdescripcion")
    private String descripcion;

    @Column(name = "Znttiempocaminata", length = 100)
    private String duracionCaminata;

    @ManyToOne
    @JoinColumn(name = "Estid")
    private Estacion estacion;

}
