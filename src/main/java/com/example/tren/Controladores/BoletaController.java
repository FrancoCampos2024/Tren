package com.example.tren.Controladores;

import com.example.tren.Entidades.*;
import com.example.tren.Servicios.SerBoleta;
import com.example.tren.Servicios.SerEstacion;
import com.example.tren.Servicios.SerZona;
import com.example.tren.Utils.BoletaPDFGenerator;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/viaje")
public class BoletaController {

    @Autowired
    private SerBoleta boletaService;

    @Autowired
    private SerEstacion estacionService;

    @Autowired
    private SerZona zonaTurisService;

    @Autowired
    private TrenSimulador trenSimulador;

    @PostMapping("/guardar-boleta")
    public String guardarBoleta(@RequestParam String nombrePasajero,
                                @RequestParam(required = false) String telefonoCliente,
                                @RequestParam int cantidadBoletos,
                                @RequestParam int estacionOrigen,
                                @RequestParam int estacionDestino,
                                @RequestParam int idZona,
                                Model model) {

        if (estacionOrigen == estacionDestino) {
            model.addAttribute("error", "La estación de origen y destino no pueden ser iguales.");
            return "redirect:/viaje/formulario";
        }

        // ENTIDADES
        Estacion origen = estacionService.buscarPorOrden(estacionOrigen);
        Estacion destino = estacionService.buscarPorOrden(estacionDestino);
        Zonaturistica zonaTuristica = zonaTurisService.buscarPoridZona(idZona);

        // GUARDAR BOLETA
        Boleta boleta = new Boleta();
        boleta.setNombrec(nombrePasajero);
        boleta.setTelefonoc(telefonoCliente);
        boleta.setCantidadpasajeros(cantidadBoletos);
        boleta.setFecha(new java.sql.Date(System.currentTimeMillis()));
        boleta.setEstacioninicio(origen);
        boleta.setEstaciondestino(destino);
        boleta.setZonaturistica(zonaTuristica);
        boleta.setMonto(10.0f * cantidadBoletos);
        boletaService.guardar(boleta);

        // CALCULAR TIEMPO
        int posicionTren = trenSimulador.getPosicionActual();
        boolean haciaAdelante = trenSimulador.getDireccion().equalsIgnoreCase("IDA");

        int tiempoLlegada = estacionService.calcularTiempoLlegada(posicionTren, estacionOrigen, haciaAdelante);
        int tiempoViaje = estacionService.calcularTiempoViaje(estacionOrigen, estacionDestino, haciaAdelante, posicionTren);
        Estacion estacionActualTren = estacionService.buscarPorOrden(posicionTren);

        // MANDAR TO DO A LA VISTA
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
        model.addAttribute("nombrePasajero", nombrePasajero);
        model.addAttribute("cantidadBoletos", cantidadBoletos);

        return "redirect:/viaje/boleta-pdf/" + boleta.getId();
    }

    @GetMapping("/boleta-pdf/{id}")
    public void generarPDF(@PathVariable int id, HttpServletResponse response) throws Exception {
        Boleta boleta = boletaService.buscarPorId(id);

        int posicionTren = trenSimulador.getPosicionActual();
        boolean haciaAdelante = trenSimulador.getDireccion().equalsIgnoreCase("IDA");

        int tiempoLlegada = estacionService.calcularTiempoLlegada(
                posicionTren, boleta.getEstacioninicio().getOrdenEstacion(), haciaAdelante);

        int tiempoViaje = estacionService.calcularTiempoViaje(
                boleta.getEstacioninicio().getOrdenEstacion(),
                boleta.getEstaciondestino().getOrdenEstacion(),
                haciaAdelante,
                posicionTren);

        // Configura respuesta
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=boleta_" + boleta.getId() + ".pdf");

        // Generar PDF
        BoletaPDFGenerator.generar(response.getOutputStream(), boleta,tiempoLlegada,tiempoViaje);
    }

}
