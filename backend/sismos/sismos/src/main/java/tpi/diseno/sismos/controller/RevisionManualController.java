package tpi.diseno.sismos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.SessionScope;
import tpi.diseno.sismos.dto.EventoSismicoResumenDTO;
import tpi.diseno.sismos.model.GestorRegistrarResultadoRevisionManual;
import tpi.diseno.sismos.repository.EstadoRepository;
import tpi.diseno.sismos.repository.EventoSismicoRepository;
import tpi.diseno.sismos.repository.SesionRepository;
import tpi.diseno.sismos.service.GenerarSismogramaService;

import java.util.List;
import java.util.Map;

// Esta clase es PantallaRegistrarResultadoRevisionManual.
@RestController
@RequestMapping("/revision-manual")
@SessionScope
public class RevisionManualController {

    private final GestorRegistrarResultadoRevisionManual gestor;

    @Autowired
    public RevisionManualController(EventoSismicoRepository eventoRepo, EstadoRepository estadoRepo, SesionRepository sesionRepo, GenerarSismogramaService sismogramaService) {
        // La Pantalla es responsable de crear la instancia del Gestor.
        this.gestor = new GestorRegistrarResultadoRevisionManual(eventoRepo, estadoRepo, sesionRepo, sismogramaService);
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
        List<EventoSismicoResumenDTO> eventos = gestor.registrarNuevaRevision();
        
        // MSG 16: mostrarEventoSismicoParaSeleccion() -> La Pantalla devuelve la lista al front-end.
        return ResponseEntity.ok(eventos);
    }

    /**
     * MSG 17: tomarSeleccionEvento() -> El Analista selecciona un evento por su índice.
     */
    @PostMapping("/seleccionar-evento")
    public ResponseEntity<String> tomarSeleccionEvento(@RequestBody Map<String, Integer> payload) {
        int indiceSeleccionado = payload.get("indice");
        // MSG 18: tomarSeleccionEventoSismico() -> La Pantalla informa al Gestor la selección.
        gestor.tomarSeleccionEventoSismico(indiceSeleccionado);
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
}