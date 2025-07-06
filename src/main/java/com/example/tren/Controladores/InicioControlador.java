package com.example.tren.Controladores;


import com.example.tren.Entidades.Boleta;
import com.example.tren.Entidades.Estacion;
import com.example.tren.Entidades.TrenSimulador;
import com.example.tren.Servicios.SerEstacion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("inicio")
public class InicioControlador {
    @Autowired
    private SerEstacion estacionService;
    @Autowired
    private TrenSimulador trenSimulador;

    @GetMapping("/menu")
    public String vistaGeneral(Model model) {
        List<Estacion> estaciones = estacionService.listarTodas();

        int posTren = trenSimulador.getPosicionActual();
        Estacion estacionActualTren = estacionService.buscarPorOrden(posTren);

        model.addAttribute("estaciones", estaciones);
        model.addAttribute("posicionTren", estacionActualTren);
        model.addAttribute("estacion", estacionActualTren);
        model.addAttribute("boleta", new Boleta());
        return "Inicio";
    }



}
