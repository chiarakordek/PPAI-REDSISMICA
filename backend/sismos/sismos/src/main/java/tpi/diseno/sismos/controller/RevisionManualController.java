package tpi.diseno.sismos.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.annotation.SessionScope;

import tpi.diseno.sismos.dto.EventoSismicoResumenDTO;
import tpi.diseno.sismos.model.EventoSismico;
import tpi.diseno.sismos.model.GestorRegistrarResultadoRevisionManual;
import tpi.diseno.sismos.repository.EstadoRepository;
import tpi.diseno.sismos.repository.EventoSismicoRepository;
import tpi.diseno.sismos.repository.SesionRepository;
import tpi.diseno.sismos.service.GenerarSismogramaService;

@RestController
@RequestMapping("/revision-manual")
@SessionScope
public class RevisionManualController {

    private final GestorRegistrarResultadoRevisionManual gestor;
    private final EventoSismicoRepository eventoRepo;

    @Autowired
    public RevisionManualController(EventoSismicoRepository eventoRepo, EstadoRepository estadoRepo, SesionRepository sesionRepo, GenerarSismogramaService sismogramaService) {
        // La Pantalla es responsable de crear la instancia del Gestor.
        this.gestor = new GestorRegistrarResultadoRevisionManual(eventoRepo, estadoRepo, sesionRepo, sismogramaService);
        this.eventoRepo = eventoRepo;
    }

    /**
     * MSG 1: opcionRegistrarNuevaRevision() -> El Analista inicia el caso de uso a través de este endpoint.
     * Este es el método público que recibe la solicitud inicial.
     */
    @GetMapping("/iniciar")
    public ResponseEntity<List<EventoSismicoResumenDTO>> opcionRegistrarNuevaRevision() {
        // MSG 2: abrir() -> Como resultado de la opción, la Pantalla se "abre".
        
        
        return this.abrir();
    }

    /**
     * MSG 2: abrir() -> Método de la pantalla que orquesta el inicio.
     */

    private ResponseEntity<List<EventoSismicoResumenDTO>> abrir() {
        // MSG 3: registrarNuevaRevision() -> La Pantalla delega la responsabilidad al Gestor.

        System.out.println("Iniciando la revisión manual...");
        List<EventoSismicoResumenDTO> eventos = gestor.registrarNuevaRevision();
        eventos.forEach(evento -> {
        System.out.println("Evento DTO - ID: " + evento.getId() + 
                          ", Fecha: " + evento.getFechaHoraOcurrencia());
    });
        
        // MSG 16: mostrarEventoSismicoParaSeleccion() -> La Pantalla devuelve la lista al front-end.
        return ResponseEntity.ok(eventos);
    }
    @PatchMapping("/eventos/{id}/cambiar-estado")
        public ResponseEntity<String> cambiarEstado(@PathVariable Long id, @RequestBody Map<String, String> payload) {
    try {
        String nuevoEstado = payload.get("nuevoEstado");
        
        // Delegar al gestor para cambiar el estado del evento
        gestor.tomarSeleccionEventoSismico(id);

        return ResponseEntity.ok("Estado cambiado exitosamente");
        
    } catch (Exception e) {
        return ResponseEntity.badRequest().body("Error al cambiar estado: " + e.getMessage());
    }
}

    
    @GetMapping("/detalles-evento")
    public ResponseEntity<Map<String, Object>> obtenerDetallesEvento(@RequestParam Long id) {
    try {
        EventoSismico evento = eventoRepo.findById(id)
            .orElseThrow(() -> new RuntimeException("Evento no encontrado con ID: " + id));
        
        // Usar los métodos que ya tienes en la entidad
        Map<String, Object> detalles = new HashMap<>();
        detalles.put("clasificacion", evento.getClasificacion());
        detalles.put("alcance", evento.getAlcance());
        detalles.put("origen_evento", evento.getOrigen());
        detalles.put("estado", evento.getEstado()); // ← Usa el nuevo método
        
        System.out.println("Detalles enviados: " + detalles);
        
        return ResponseEntity.ok(detalles);
        
    } catch (Exception e) {
        System.err.println("Error al obtener detalles: " + e.getMessage());
        e.printStackTrace();
        return ResponseEntity.badRequest().body(Map.of("error", "Error al obtener detalles: " + e.getMessage()));
    }
}
    /**
     * MSG 17: tomarSeleccionEvento() -> El Analista selecciona un evento por su índice.
     */
    @PostMapping("/seleccionar-evento")
    public ResponseEntity<String> tomarSeleccionEvento(@RequestBody Map<String, Long> payload) {
        Long idSeleccionado = payload.get("id");
        // MSG 18: tomarSeleccionEventoSismico() -> La Pantalla informa al Gestor la selección.
        gestor.tomarSeleccionEventoSismico(idSeleccionado);
        return ResponseEntity.ok("Evento seleccionado y bloqueado. Datos detallados listos para consultar.");
    }
    
    /**
     * MSG 55: tomarOpcVerMapa() -> El Analista selecciona la opción para ver el mapa.
     */
    @PostMapping("/ver-mapa")
    public ResponseEntity<String> tomarOpcVerMapa() {
        // MSG 56: tomarOpcVerMapa() -> La Pantalla informa al Gestor.
        gestor.tomarOpcVerMapa();
        return ResponseEntity.ok("Opción 'Ver Mapa' procesada.");
    }
    
    /**
     * MSG 58: tomarOpcModificarDatos() -> El Analista selecciona la opción para modificar datos.
     */
    @PostMapping("/modificar-datos")
    public ResponseEntity<String> tomarOpcModificarDatos() {
        // MSG 59: tomarOpcModificarDatos() -> La Pantalla informa al Gestor.
        gestor.tomarOpcModificarDatos();
        return ResponseEntity.ok("Opción 'Modificar Datos' procesada.");
    }

    /**
     * MSG 61: tomarSeleccion() -> El Analista toma una decisión final (ej: Rechazar).
     */
    @PostMapping("/finalizar-revision")
    public ResponseEntity<String> tomarSeleccion(@RequestBody Map<String, String> payload) {
        String decision = payload.get("decision");

        if ("rechazar".equalsIgnoreCase(decision)) {
            // MSG 62: tomarSeleccionRechazada() -> La Pantalla invoca al Gestor con la decisión de rechazar.
            gestor.tomarSeleccionRechazada();
            return ResponseEntity.ok("El evento ha sido RECHAZADO. Caso de uso finalizado.");
        }
        return ResponseEntity.badRequest().body("Decisión no válida.");
    }
    
    // Endpoint para que tu JS pueda obtener todos los eventos
    @GetMapping("/eventos-todos")
public ResponseEntity<List<EventoSismicoResumenDTO>> obtenerEventosTodos() {
    try {
        List<EventoSismicoResumenDTO> eventos = eventoRepo.findAll()
                .stream()
                .map(evento -> evento.getDatos())  // Usa el método que ya tienes en la entidad
                .collect(Collectors.toList());
        
        return ResponseEntity.ok(eventos);
        
    } catch (Exception e) {
        System.err.println("Error al obtener eventos: " + e.getMessage());
        e.printStackTrace();
        return ResponseEntity.internalServerError().body(null);
    }
    }
}