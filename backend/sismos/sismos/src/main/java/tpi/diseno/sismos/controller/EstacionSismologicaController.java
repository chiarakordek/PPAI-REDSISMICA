package tpi.diseno.sismos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tpi.diseno.sismos.dto.EstacionSismologicaDTO;
import tpi.diseno.sismos.model.EstacionSismologica;
import tpi.diseno.sismos.service.EstacionSismologicaService;

import java.util.List;

@RestController
@RequestMapping("/api/estaciones")
public class EstacionSismologicaController {

    private final EstacionSismologicaService service;

    @Autowired
    public EstacionSismologicaController(EstacionSismologicaService service) {
        this.service = service;
    }

    @PostMapping("/guardar")
    public ResponseEntity<EstacionSismologica> guardarEstacion(@RequestBody EstacionSismologica estacion) {
        EstacionSismologica estacionGuardada = service.guardarEstacion(estacion);
        return ResponseEntity.ok(estacionGuardada);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EstacionSismologicaDTO> getEstacionById(@PathVariable Long id) {
        EstacionSismologicaDTO estacionDTO = service.getDatosEstacion(id);
        return ResponseEntity.ok(estacionDTO);
    }
    
    @GetMapping("/todas")
    public ResponseEntity<List<EstacionSismologicaDTO>> getAllEstaciones() {
        List<EstacionSismologicaDTO> estaciones = service.obtenerTodasLasEstaciones();
        return ResponseEntity.ok(estaciones);
    }
}