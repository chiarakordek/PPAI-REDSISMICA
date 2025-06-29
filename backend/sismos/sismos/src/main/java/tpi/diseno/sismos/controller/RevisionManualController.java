package tpi.diseno.sismos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tpi.diseno.sismos.model.*;
import tpi.diseno.sismos.repository.EventoSismicoRepository;
import tpi.diseno.sismos.repository.EstadoRepository;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/revision-manual")
public class RevisionManualController {

    @Autowired
    private GestorRegistrarResultadoRevisionManual gestor;
    
    @Autowired
    private EventoSismicoRepository eventoSismicoRepository;
    
    @Autowired
    private EstadoRepository estadoRepository;

    // Paso 6: Obtener eventos pendientes de revisión
    @GetMapping("/eventos-pendientes")
    public ResponseEntity<List<String>> obtenerEventosPendientes() {
        gestor.RegistrarNuevaRevision();
        return ResponseEntity.ok(gestor.getDatosEventosSismicos());
    }

    // Paso 7: Seleccionar un evento
    @PostMapping("/seleccionar-evento/{idEvento}")
    public ResponseEntity<List<SerieTemporal>> seleccionarEvento(@PathVariable Long idEvento) {
        EventoSismico evento = eventoSismicoRepository.findById(idEvento)
                .orElseThrow(() -> new RuntimeException("Evento sísmico no encontrado"));

        gestor.tomarSeleccionEventoSismico(evento);
        return ResponseEntity.ok(gestor.getSeriesTemporalesEventoSeleccionado());
    }

    // Paso 9.1: Obtener detalles del evento (alcance, clasificación, origen)
    @GetMapping("/detalles-evento/{idEvento}")
    public ResponseEntity<Map<String, String>> obtenerDetallesEvento(@PathVariable Long idEvento) {
        EventoSismico evento = eventoSismicoRepository.findById(idEvento)
                .orElseThrow(() -> new RuntimeException("Evento no encontrado"));
        
        Map<String, String> detalles = new HashMap<>();
        detalles.put("alcance", evento.getAlcanceSismo().getNombre());
        detalles.put("clasificacion", evento.getClasificacionSismo().getNombre());
        detalles.put("origen", evento.getOrigenDeGeneracion().getNombre());
        
        return ResponseEntity.ok(detalles);
    }


    // Paso 12: Modificar datos del evento
    @PutMapping("/modificar-datos/{idEvento}")
    public ResponseEntity<String> modificarDatosEvento(
            @PathVariable Long idEvento,
            @RequestBody Map<String, String> datosActualizados) {
        
        EventoSismico evento = eventoSismicoRepository.findById(idEvento)
                .orElseThrow(() -> new RuntimeException("Evento no encontrado"));
        
        // Actualizar los campos modificables
        if (datosActualizados.containsKey("magnitud")) {
            evento.setValorMagnitud(Double.parseDouble(datosActualizados.get("magnitud")));
        }
        if (datosActualizados.containsKey("alcance")) {
            // Aquí deberías buscar el AlcanceSismo por nombre y asignarlo
            // evento.setAlcanceSismo(alcanceSismoRepository.findByNombre(datosActualizados.get("alcance")));
        }
        if (datosActualizados.containsKey("origen")) {
            // Similar para origen de generación
            // evento.setOrigenDeGeneracion(origenRepository.findByNombre(datosActualizados.get("origen")));
        }
        
        eventoSismicoRepository.save(evento);
        return ResponseEntity.ok("Datos actualizados exitosamente");
    }

    // Paso 14: Obtener opciones de acción
    @GetMapping("/opciones-accion")
    public ResponseEntity<List<String>> obtenerOpcionesAccion() {
        return ResponseEntity.ok(List.of("CONFIRMAR", "RECHAZAR", "DERIVAR"));
    }

    // Paso 15: Rechazar evento
    @PostMapping("/rechazar-evento")
    public ResponseEntity<String> rechazarEvento() {
        gestor.tomarSeleccionRechazada("RECHAZADO");
        gestor.finCU();
        return ResponseEntity.ok("Evento rechazado exitosamente");
    }

    @GetMapping("/datos-series")
    public List<Map<String, Object>> obtenerDatos() {
        return gestor.getDatosParaFrontend();
    }

}