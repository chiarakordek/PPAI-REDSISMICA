package tpi.diseno.sismos.model;

import tpi.diseno.sismos.dto.EventoSismicoDetalleDTO;
import tpi.diseno.sismos.dto.EventoSismicoResumenDTO;
import tpi.diseno.sismos.dto.SerieTemporalDTO;
import tpi.diseno.sismos.repository.EstadoRepository;
import tpi.diseno.sismos.repository.EventoSismicoRepository;
import tpi.diseno.sismos.repository.SesionRepository;
import tpi.diseno.sismos.service.GenerarSismogramaService;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class GestorRegistrarResultadoRevisionManual {

    // --- Atributos de Colaboración y Estado ---
    private final EventoSismicoRepository 
    eventoSismicoRepository;
    private final EstadoRepository estadoRepository;
    private final SesionRepository sesionRepository;
    private final GenerarSismogramaService generarSismogramaService;

    // --- Atributos del DDC ---
    private List<EventoSismico> eventosSismicos;
    private List<EventoSismicoResumenDTO> datosEventosSismicos;
    private LocalDateTime fechaHoraActual;
    private Estado punteroBloqueadoEnRevision;
    private Sesion sesionActual;
    private Empleado punteroEmpleado;
    private EventoSismico eventoSeleccionado;
    private List<SerieTemporalDTO> seriesTemporalesEventoSeleccionado;
    private Estado punteroRechazado;
    private Object mapaUbicacion;
    private String opcionResultadoRevision;

    // Atributos para almacenar datos de clasificación y alcance del sismo
    private String nombreClasificacionSismo;
    private String nombreAlcanceSismo;
    private String nombreOrigenGeneracion;


    // --- CONSTRUCTOR ---
    public GestorRegistrarResultadoRevisionManual(EventoSismicoRepository e, EstadoRepository es, SesionRepository s, GenerarSismogramaService gss) {
        this.eventoSismicoRepository = e;
        this.estadoRepository = es;
        this.sesionRepository = s;
        this.generarSismogramaService = gss;
    }

    // --- MÉTODOS PÚBLICOS (En orden secuencial según el diagrama) ---

    public List<EventoSismico> buscarEventosSismicos() { // MSG 4
        return this.eventoSismicoRepository.findAll();
    }

    public List<EventoSismicoResumenDTO> registrarNuevaRevision() { // MSG 3
        this.sesionActual = sesionRepository.findById(1L).orElseThrow(() -> new RuntimeException("Sesión activa no encontrada."));
        this.datosEventosSismicos = this.ordenarEventoSismico(this.buscarEventosSismicos()); // MSG 15
        return this.datosEventosSismicos;
    }

    public void tomarSeleccionEventoSismico(Long id) { // MSG 18 ACA MODIFICAMOS
        if (id == null) {
            throw new IllegalArgumentException("El ID del evento no puede ser nulo.");
        }
        System.out.println("ID del evento seleccionado para actuar: " + id);

        this.sesionActual = sesionRepository.findById(1L)
                .orElseThrow(() -> new RuntimeException("Sesión activa no encontrada para esta operación."));
                
        EventoSismico evento = eventoSismicoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("No se pudo encontrar el evento con ID: " + id));
        this.bloquearEvento(evento); // MSG 19
    }

    public void bloquearEvento(EventoSismico evento) { // MSG 19
        Estado punteroBloqueadoEnRevision = this.buscarEstadoBloqueado(); // MSG 20
        LocalDateTime fechaHoraActual = this.tomarFechaHoraActual(); // MSG 23
        Empleado punteroEmpleado = this.buscarEmpleadoLogueado(); // MSG 24
        evento.revisar(punteroBloqueadoEnRevision, fechaHoraActual, punteroEmpleado); // MSG 27 -- Delega Evento Sismico
        eventoSismicoRepository.save(evento);
        this.buscarDatosSismicos(evento.getId()); // MSG 34
    }

   public Map<String, Object> buscarDatosSismicos(Long id) { // MSG 34
    if (id == null) { 
        throw new IllegalArgumentException("ID de evento no puede ser nulo."); 
    }
    
    // Buscar el evento por ID
    EventoSismico evento = eventoSismicoRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Evento no encontrado con ID: " + id));

    // Usar el evento encontrado en lugar de this.eventoSeleccionado
    String clasificacion = evento.getClasificacion(); // MSG 35
    String alcance = evento.getAlcance(); // MSG 37
    String origen = evento.getOrigen(); // MSG 39
    /////esto agregamos para que sea mostrado en el front el pase del estado de autodetectado a bloqueado en revision
    String estadoActual = null;
    if (evento.getEstadoActual() != null) {
        estadoActual = evento.getEstadoActual().getNombreEstado(); 
    }
//////////////
    Map<String, Object> detalles = new HashMap<>();
    detalles.put("clasificacion", clasificacion);
    detalles.put("alcance", alcance);
    detalles.put("origen_evento", origen); // ← Nota: usar "origen_evento" para que coincida con el frontend
    //agregamos para mostrar en el front
    detalles.put("estado", estadoActual);
    this.buscarSeriesTemporales(evento);
    return detalles;
}
    public void buscarSeriesTemporales(EventoSismico evento){
        this.seriesTemporalesEventoSeleccionado = evento.obtenerSeriesTemporales(); // MSG 41
        this.llamarCasoDeUsoGenerarSismograma(); // MSG 53 
    }

    public void mostrarOpcionVisualizarMapa() { // MSG 54
        System.out.println("Preparando para mostrar opción 'Ver Mapa'.");
    }

    public void tomarOpcVerMapa() { // MSG 56
        System.out.println("Procesando selección 'Ver Mapa'.");
    }

    public void mostrarOpcModificarDatos() { // MSG 57
        System.out.println("Preparando para mostrar opción 'Modificar Datos'.");
    }

    public void tomarOpcModificarDatos() { // MSG 59
        System.out.println("Procesando selección 'Modificar Datos'.");
    }

    public void mostrarOpcionesParaSeleccion() { // MSG 60
        System.out.println("Preparando para mostrar opciones finales (ej: Aprobar/Rechazar).");
    }
    
    public void tomarSeleccionRechazada(Long id ) { // MSG 62
        if (id == null) {
            throw new IllegalArgumentException("El ID del evento no puede ser nulo.");
        }
        System.out.println("ID del evento seleccionado para actuar: " + id);

        EventoSismico evento = eventoSismicoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("No se pudo encontrar el evento con ID: " + id));
        this.evaluarResultadoInspeccion(evento); // MSG 63
    }

    public void evaluarResultadoInspeccion(EventoSismico evento) { // MSG 63
        this.validarExistenciaDatos(evento); // MSG 64
    }

    public void validarExistenciaDatos(EventoSismico evento) { // MSG 64
         if (evento == null) {
            throw new IllegalStateException("No hay un evento sísmico seleccionado para evaluar.");
        }

        if (evento.getValorMagnitud() == null) {
            throw new IllegalStateException("El evento sísmico no tiene asignado un valor de magnitud.");
        }

        if (evento.getAlcanceSismo() == null) {
            throw new IllegalStateException("El evento sísmico no tiene definido el alcance.");
        }

        if (evento.getOrigenGeneracion() == null) {
            throw new IllegalStateException("El evento sísmico no tiene definido el origen de generación.");
        }

        this.rechazarEvento(evento); // MSG 65
    }   

    public void rechazarEvento(EventoSismico evento) { // MSG 65
            Estado punteroRechazado = this.buscarEstadoRechazado(); 
            LocalDateTime fechaHoraActual = this.tomarFechaHoraActual(); 
            evento.rechazar(punteroRechazado, fechaHoraActual, punteroEmpleado); // MSG 70
            eventoSismicoRepository.save(evento);
            finCU();
        }
    /**
     * MSG 66: buscarEstadoRechazado()
     * implementa el bucle literal del diagrama.
     */
    public Estado buscarEstadoRechazado() {
        List<Estado> todosLosEstados = estadoRepository.findAll();
        Estado estadoRechazadoEncontrado = null;

        // Inicia el loop [mientras exista estados]
        for (Estado estado : todosLosEstados) {
            // MSG 67: esAmbitoEventoSismico() -> Se delega la verificación del ámbito al estado.
            if (estado.esAmbitoEventoSismico()) {
                // MSG 68: esRechazado() -> Se delega la verificación del nombre al estado.
                if (estado.esRechazado()) {
                    estadoRechazadoEncontrado = estado;
                    break; // Se encontró el estado, se sale del bucle.
                }
            }
        }

        if (estadoRechazadoEncontrado == null) {
             throw new RuntimeException("No se pudo encontrar el estado 'Rechazado' con ámbito 'EventoSismico'.");
        }
        return estadoRechazadoEncontrado;   
    }

    public void finCU() { // MSG 75
        this.eventosSismicos = null;
        this.datosEventosSismicos = null;
        this.fechaHoraActual = null;
        this.punteroBloqueadoEnRevision = null;
        this.sesionActual = null;
        this.punteroEmpleado = null;
        this.eventoSeleccionado = null;
        this.seriesTemporalesEventoSeleccionado = null;
        this.punteroRechazado = null;
        this.mapaUbicacion = null;
        this.opcionResultadoRevision = null;
        this.nombreClasificacionSismo = null;
        this.nombreAlcanceSismo = null;
        this.nombreOrigenGeneracion = null;
    }

    // --- MÉTODOS PRIVADOS (Implementación de auto-mensajes que no son parte de la cadena de rechazo) ---

    private List<EventoSismicoResumenDTO> buscarEventosSismicos() { // MSG 4
        this.eventosSismicos = this.eventoSismicoRepository.findAll();
                List<EventoSismicoResumenDTO> eventosParaRevision = new ArrayList<>();
        for (EventoSismico evento : this.eventosSismicos) {
            if (evento.esAutoDetectado()) { // MSG 5
                eventosParaRevision.add(evento.getDatos()); // MSG 7
            }
        }
        return eventosParaRevision;
    }
    
    private List<EventoSismicoResumenDTO> ordenarEventoSismico(List<EventoSismicoResumenDTO> listaEventos) { // MSG 15
        listaEventos.sort(Comparator.comparing(EventoSismicoResumenDTO::getFechaHoraOcurrencia).reversed());
        return listaEventos;
    }

    public Estado buscarEstadoBloqueado() { // MSG 20
            List<Estado> todosLosEstados = estadoRepository.findAll();
            Estado estadoBloqueadoEncontrado = null;

        // Inicia el loop Estados Para eventos sismicos [mientras exista estados]
        for (Estado estado : todosLosEstados) {
            // MSG 21: esAmbitoEventoSismico() -> Se delega la verificación del ámbito al estado.
            if (estado.esAmbitoEventoSismico()) {
                // MSG 22: esBloqueadoEnRevision() -> Se delega la verificación del nombre al estado.
                if (estado.esBloqueadoEnRevision()) {
                    estadoBloqueadoEncontrado = estado;
                    break; // Se encontró el estado, se sale del bucle.
                }
            }
        }

        if (estadoBloqueadoEncontrado == null) {
             throw new RuntimeException("No se pudo encontrar el estado 'Bloqueado' con ámbito 'EventoSismico'.");
        }
     return estadoBloqueadoEncontrado;
    }

    private LocalDateTime tomarFechaHoraActual() { // MSG 23 y 69
        return LocalDateTime.now();
    }

    private Empleado buscarEmpleadoLogueado() { // MSG 24
        if (this.sesionActual == null) { throw new RuntimeException("No hay sesión activa"); }
        return this.sesionActual.obtenerUsuarioLogueado(); // MSG 25
    }
    
    private void llamarCasoDeUsoGenerarSismograma() { // MSG 53
        this.generarSismogramaService.generarSismograma();
    }
}