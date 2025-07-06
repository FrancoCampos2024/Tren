package com.example.tren.Controladores;

import com.example.tren.Servicios.SerEstacion;
import com.example.tren.Servicios.SerZona;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/zonas")
public class ZonaControlador {

    @Autowired
    @Qualifier("zonaTurisService")
    private SerZona zonaService;

    @Autowired
    @Qualifier("estacionService")
    private SerEstacion estacionService;

    @GetMapping("/{idEstacion}")
    public String mostrarZonas(@PathVariable int idEstacion, Model model) {

        model.addAttribute("estacion",estacionService.buscarEstacion(idEstacion).getNombre_estacion());
        model.addAttribute("zonas", zonaService.buscarPorEstacion(idEstacion));
        return "Zona";
    }

    @GetMapping("/ListarZonas")
    public String listarzonas( Model model) {
        model.addAttribute("zonas", zonaService.listazonas());
        return "ListaZonasTuristicas";
    }
}
