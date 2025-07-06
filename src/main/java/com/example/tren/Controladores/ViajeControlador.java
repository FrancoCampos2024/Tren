package com.example.tren.Controladores;

import com.example.tren.Entidades.Boleta;
import com.example.tren.Entidades.Estacion;
import com.example.tren.Entidades.TrenSimulador;
import com.example.tren.Entidades.Zonaturistica;
import com.example.tren.Servicios.SerEstacion;
import com.example.tren.Servicios.SerZona;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/viaje")
public class ViajeControlador {

    @Autowired
    @Qualifier("estacionService")
    private SerEstacion estacionService;

    @Autowired
    private TrenSimulador trenSimulador;

    @Autowired
    private SerZona zonaTurisService;

    @GetMapping("/calcular-tiempo-real")
    public String calcularTiempoReal(@RequestParam int estacionOrigen,
                                     @RequestParam int estacionDestino, @RequestParam int idZona,
                                     Model model) {

        int posicionTren = trenSimulador.getPosicionActual();
        boolean haciaAdelante = trenSimulador.getDireccion().equalsIgnoreCase("IDA");

        int tiempoLlegada = estacionService.calcularTiempoLlegada(posicionTren, estacionOrigen, haciaAdelante);
        int tiempoViaje = estacionService.calcularTiempoViaje(estacionOrigen, estacionDestino, haciaAdelante,
                posicionTren);

        System.out.println("Tiempo de llegada: " + tiempoLlegada + " segundos.");
        System.out.println("Tiempo de viaje: " + tiempoViaje + " segundos.");
        System.out.println(haciaAdelante ? "IDA" : "VUELTA");
        System.out.println("Posicion del tren: " + posicionTren);

        Estacion estacionActualTren = estacionService.buscarPorOrden(posicionTren);
        Estacion origen = estacionService.buscarPorOrden(estacionOrigen);
        Estacion destino = estacionService.buscarPorOrden(estacionDestino);

        Zonaturistica zonaTuristica = zonaTurisService.buscarPoridZona(idZona);

        model.addAttribute("posicionTren", estacionActualTren);
        model.addAttribute("origen", origen);
        model.addAttribute("destino", destino);
        model.addAttribute("tiempoLlegada", tiempoLlegada);
        model.addAttribute("tiempoViaje", tiempoViaje);
        model.addAttribute("direccion", trenSimulador.getDireccion());
        model.addAttribute("posicionTrenOrden", posicionTren);
        model.addAttribute("origenOrden", estacionOrigen);
        model.addAttribute("destinoOrden", estacionDestino);
        model.addAttribute("zonaTuristica", zonaTuristica);

        return "Tiemporeal";
    }

    @GetMapping("/formulario")
    public String mostrarFormulario(Model model) {
        model.addAttribute("estaciones", estacionService.listarTodas());
        model.addAttribute("boleta", new Boleta()); // para el th:object si lo usas
        return "formulario-viaje";
    }
}
