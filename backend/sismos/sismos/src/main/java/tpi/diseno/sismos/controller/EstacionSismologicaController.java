package tpi.diseno.sismos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tpi.diseno.sismos.model.EstacionSismologica;
import tpi.diseno.sismos.service.EstacionSismologicaService;


@RestController
@RequestMapping("/estaciones")
public class EstacionSismologicaController {

    @Autowired
    private EstacionSismologicaService service;

    @PostMapping
    public EstacionSismologica crear(@RequestBody EstacionSismologica estacion) {
        return service.guardarEstacion(estacion);
    }
    

    @GetMapping("/{id}/datos")
    public String obtenerDatos(@PathVariable Long id) {
        return service.getDatosEstacion(id);
    }
}