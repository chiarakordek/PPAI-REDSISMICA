// Archivo completo para: RevisionManualController.java

package tpi.diseno.sismos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tpi.diseno.sismos.model.*;
import tpi.diseno.sismos.repository.*;

import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@CrossOrigin(origins = {"http://localhost:5500", "http://127.0.0.1:5500"})
@RestController
@RequestMapping("/api/revision-manual")
public class RevisionManualController {

    @Autowired private GestorRegistrarResultadoRevisionManual gestor;
    @Autowired private EventoSismicoRepository eventoSismicoRepository;
    @Autowired private EstadoRepository estadoRepository;
    @Autowired private AlcanceSismoRepository alcanceSismoRepository;
    @Autowired private ClasificacionSismoRepository clasificacionSismoRepository;
    @Autowired private OrigenDeGeneracionRepository origenDeGeneracionRepository;
    @Autowired private CambioEstadoRepository cambioEstadoRepository;

    @GetMapping("/eventos-pendientes")
    public ResponseEntity<List<EventoSismico>> obtenerEventosPendientes() {
        gestor.RegistrarNuevaRevision();
        List<EventoSismico> eventos = gestor.getEventosSismicosPendientesCompletos();
        return ResponseEntity.ok(eventos);
    }

    @GetMapping("/eventos-registrados")
    public ResponseEntity<List<DatosEventoSismico>> obtenerEventosRegistrados() {
        List<EventoSismico> todosLosEventos = eventoSismicoRepository.findAll();

        List<DatosEventoSismico> listaDtos = todosLosEventos.stream().map(evento -> {
            DatosEventoSismico dto = new DatosEventoSismico();
            dto.setId(evento.getId());
            dto.setFechaHoraFromLocalDateTime(evento.getFechaHoraOcurrencia());
            dto.setUbicacionFromCoordenadas(evento.getLatitudEpicentro(), evento.getLongitudEpicentro());
            // --- LÍNEA AÑADIDA PARA EL HIPOCENTRO ---
            dto.setUbicacionHipocentroFromCoordenadas(evento.getLatitudHipocentro(), evento.getLongitudHipocentro());
            dto.setMagnitud(evento.getMagnitud());
            return dto;
        }).collect(Collectors.toList());

        return ResponseEntity.ok(listaDtos);
    }
    
    @PostMapping("/eventos")
    @Transactional
    public ResponseEntity<?> crearNuevoEvento(@RequestBody Map<String, String> payload) {
        try {
            Estado estadoPendiente = estadoRepository.findByNombreEstado("PendienteDeRevision").orElseThrow(() -> new RuntimeException("Estado 'PendienteDeRevision' no encontrado."));
            AlcanceSismo alcanceDefault = alcanceSismoRepository.findById(1L).orElseThrow(() -> new RuntimeException("AlcanceSismo con ID=1 no encontrado."));
            ClasificacionSismo clasificacionDefault = clasificacionSismoRepository.findById(1L).orElseThrow(() -> new RuntimeException("ClasificacionSismo con ID=1 no encontrada."));
            OrigenDeGeneracion origenDefault = origenDeGeneracionRepository.findById(1L).orElseThrow(() -> new RuntimeException("OrigenDeGeneracion con ID=1 no encontrado."));
            EventoSismico nuevoEvento = new EventoSismico();
            nuevoEvento.setFechaHoraOcurrencia(LocalDateTime.now());
            nuevoEvento.setValorMagnitud(Double.parseDouble(payload.get("magnitud")));
            nuevoEvento.setLatitudEpicentro(Double.parseDouble(payload.get("latitud")));
            nuevoEvento.setLongitudEpicentro(Double.parseDouble(payload.get("longitud")));
            nuevoEvento.setEstado(estadoPendiente);
            nuevoEvento.setAlcanceSismo(alcanceDefault);
            nuevoEvento.setClasificacionSismo(clasificacionDefault);
            nuevoEvento.setOrigenDeGeneracion(origenDefault);
            EventoSismico eventoGuardado = eventoSismicoRepository.save(nuevoEvento);
            CambioEstado estadoInicial = new CambioEstado();
            estadoInicial.setEstado(estadoPendiente);
            estadoInicial.setFechaInicio(LocalDateTime.now());
            estadoInicial.setEventoSismico(eventoGuardado);
            cambioEstadoRepository.save(estadoInicial);
            List<CambioEstado> cambios = new ArrayList<>();
            cambios.add(estadoInicial);
            eventoGuardado.setCambiosEstado(cambios);
            return ResponseEntity.status(HttpStatus.CREATED).body(eventoGuardado);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(Map.of("message", "Error al crear el evento: " + e.getMessage()));
        }
    }
    
    @PostMapping("/seleccionar-evento/{idEvento}")
    public ResponseEntity<List<SerieTemporal>> seleccionarEvento(@PathVariable Long idEvento) {
        EventoSismico evento = eventoSismicoRepository.findById(idEvento).orElseThrow(() -> new RuntimeException("Evento sísmico no encontrado"));
        gestor.tomarSeleccionEventoSismico(evento);
        return ResponseEntity.ok(gestor.getSeriesTemporalesEventoSeleccionado());
    }

    @GetMapping("/detalles-evento/{idEvento}")
    public ResponseEntity<?> obtenerDetallesEvento(@PathVariable Long idEvento) {
        return eventoSismicoRepository.findById(idEvento)
                .map(evento -> {
                    DatosEventoSismico dto = new DatosEventoSismico();
                    dto.setId(evento.getId());
                    dto.setFechaHoraFromLocalDateTime(evento.getFechaHoraOcurrencia());
                    dto.setUbicacionFromCoordenadas(evento.getLatitudEpicentro(), evento.getLongitudEpicentro());
                    dto.setMagnitud(evento.getMagnitud());
                    if (evento.getEstado() != null) {
                        dto.setEstado(evento.getEstado().getNombreEstado());
                    } else {
                        dto.setEstado("No definido");
                    }
                    if (evento.getAlcanceSismo() != null) {
                        dto.setAlcance(evento.getAlcanceSismo().getNombre());
                    } else {
                        dto.setAlcance("No definido");
                    }
                    return ResponseEntity.ok(dto);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/modificar-datos/{idEvento}")
    public ResponseEntity<String> modificarDatosEvento(@PathVariable Long idEvento, @RequestBody Map<String, String> datosActualizados) {
        EventoSismico evento = eventoSismicoRepository.findById(idEvento).orElseThrow(() -> new RuntimeException("Evento no encontrado"));
        if (datosActualizados.containsKey("magnitud")) {
            evento.setValorMagnitud(Double.parseDouble(datosActualizados.get("magnitud")));
        }
        eventoSismicoRepository.save(evento);
        return ResponseEntity.ok("Datos actualizados exitosamente");
    }
    
    @GetMapping("/opciones-accion")
    public ResponseEntity<List<String>> obtenerOpcionesAccion() { return ResponseEntity.ok(List.of("CONFIRMAR", "RECHAZAR", "DERIVAR")); }
    @PostMapping("/rechazar-evento")
    public ResponseEntity<String> rechazarEvento() { gestor.tomarSeleccionRechazada("RECHAZADO"); gestor.finCU(); return ResponseEntity.ok("Evento rechazado exitosamente"); }
    @GetMapping("/datos-series")
    public List<Map<String, Object>> obtenerDatos() { return gestor.getDatosParaFrontend(); }
}