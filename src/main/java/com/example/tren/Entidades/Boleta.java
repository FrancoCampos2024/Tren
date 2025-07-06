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
public class Boleta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Bolid")
    private int id;

    @Column(name = "Bolnombrecliente", length = 50)
    private String nombrec;

    @Column(name = "Bolnumerotelefono", length = 100)
    private String telefonoc;

    @Column(name = "Bolcantidadpasajeros")
    private int cantidadpasajeros;

    @Column(name = "Bolfecha", length = 100)
    private java.sql.Date fecha;

    @Column(name = "Bolmonto", length = 100)
    private float monto;

    @ManyToOne
    @JoinColumn(name = "Estidinicio")
    private Estacion estacioninicio;

    @ManyToOne
    @JoinColumn(name = "Estiddestino")
    private Estacion estaciondestino;

    @ManyToOne
    @JoinColumn(name = "Zntid")
    private Zonaturistica zonaturistica;


}
