// Archivo completo, final y limpio para: controller/RevisionManualController.java

package tpi.diseno.sismos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tpi.diseno.sismos.model.EventoSismico;
import tpi.diseno.sismos.repository.EventoSismicoRepository;
import tpi.diseno.sismos.repository.EstadoRepository;

import tpi.diseno.sismos.model.Estado;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@CrossOrigin(origins = {"http://localhost:5500", "http://127.0.0.1:5500"})
@RestController
@RequestMapping("/api/revision-manual")
public class RevisionManualController {

    @Autowired
    private EventoSismicoRepository eventoSismicoRepository;
    
    @Autowired
    private EstadoRepository estadoRepository;

    // --- ENDPOINT PARA 'PENDIENTES' (VERSIÓN LIMPIA) ---
    // Ahora que sabemos que funciona, volvemos a usar la consulta eficiente del repositorio.
    @GetMapping("/eventos-pendientes")
    public ResponseEntity<List<Map<String, Object>>> obtenerEventosPendientes() {
        List<EventoSismico> eventos = eventoSismicoRepository.findEventosPendientes();
        return ResponseEntity.ok(this.mapEventosToDTO(eventos));
    }

    // --- ENDPOINT PARA 'REGISTRADOS' ---
    @GetMapping("/eventos-todos")
    public ResponseEntity<List<Map<String, Object>>> obtenerTodosLosEventos() {
        List<EventoSismico> eventos = eventoSismicoRepository.findAll();
        return ResponseEntity.ok(this.mapEventosToDTO(eventos));
    }
    
    // --- ENDPOINT PARA CAMBIAR EL ESTADO ---
    @PatchMapping("/eventos/{id}/cambiar-estado")
    public ResponseEntity<?> cambiarEstadoEvento(@PathVariable Long id, @RequestBody Map<String, String> body) {
        String nuevoEstadoNombre = body.get("nuevoEstado");
        Optional<EventoSismico> eventoOpt = eventoSismicoRepository.findById(id);
        if (eventoOpt.isEmpty()) { return ResponseEntity.notFound().build(); }
        Optional<Estado> nuevoEstadoOpt = estadoRepository.findByNombreEstado(nuevoEstadoNombre);
        if (nuevoEstadoOpt.isEmpty()) { return ResponseEntity.badRequest().body("El estado '" + nuevoEstadoNombre + "' no existe."); }
        EventoSismico evento = eventoOpt.get();
        Estado nuevoEstado = nuevoEstadoOpt.get();
        evento.setEstado(nuevoEstado);
        eventoSismicoRepository.save(evento);
        return ResponseEntity.ok().body("Estado del evento " + id + " actualizado a " + nuevoEstadoNombre);
    }

    // --- ENDPOINT PARA DETALLES ---
    @GetMapping("/detalles-evento")
    public ResponseEntity<Map<String, Object>> obtenerDetallesEvento(@RequestParam("id") Long eventoId) {
        return eventoSismicoRepository.findById(eventoId)
                .map(evento -> {
                    Map<String, Object> detalles = new HashMap<>();
                    detalles.put("clasificacion", (evento.getClasificacionSismo() != null) ? evento.getClasificacionSismo().getNombre() : "No definido");
                    detalles.put("alcance", (evento.getAlcanceSismo() != null) ? evento.getAlcanceSismo().getNombre() : "No definido");
                    detalles.put("origen_evento", (evento.getOrigenDeGeneracion() != null) ? evento.getOrigenDeGeneracion().getNombre() : "No definido");
                    detalles.put("estado", (evento.getEstado() != null) ? evento.getEstado().getNombreEstado() : "No definido");
                    return ResponseEntity.ok(detalles);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // --- MÉTODO PRIVADO DE AYUDA ---
    private List<Map<String, Object>> mapEventosToDTO(List<EventoSismico> eventos) {
        return eventos.stream().map(evento -> {
            Map<String, Object> dto = new LinkedHashMap<>();
            dto.put("id", evento.getId());
            dto.put("fechaHora", (evento.getFechaHoraOcurrencia() != null) ? evento.getFechaHoraOcurrencia().format(DateTimeFormatter.ofPattern("dd/MM/yyyy, HH:mm:ss")) : "N/A");
            dto.put("ubicacionEpicentro", (evento.getLatitudEpicentro() != null && evento.getLongitudEpicentro() != null) ? String.format(Locale.US, "%.1f°S, %.1f°W", evento.getLatitudEpicentro(), evento.getLongitudEpicentro()) : "N/A");
            dto.put("ubicacionHipocentro", (evento.getLatitudHipocentro() != null && evento.getLongitudHipocentro() != null) ? String.format(Locale.US, "%.1f°S, %.1f°W", evento.getLatitudHipocentro(), evento.getLongitudHipocentro()) : "N/A");
            dto.put("magnitud", evento.getMagnitud()); 
            return dto;
        }).collect(Collectors.toList());
    }
}