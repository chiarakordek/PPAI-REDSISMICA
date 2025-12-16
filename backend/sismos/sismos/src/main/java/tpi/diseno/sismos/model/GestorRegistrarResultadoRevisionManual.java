package tpi.diseno.sismos.model;

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

    // --- Atributos del DDS ---
    private List<EventoSismico> eventosSismicos;
    private List<EventoSismicoResumenDTO> datosEventosSismicos;
    private LocalDateTime fechaHoraActual;
    private EstadoDatos punteroBloqueadoEnRevision;
    private Sesion sesionActual;
    private Empleado punteroEmpleado;
    private EventoSismico eventoSeleccionado;
    private List<SerieTemporalDTO> seriesTemporalesEventoSeleccionado;
    private EstadoDatos punteroRechazado;
    private Object mapaUbicacion;
    private String opcionResultadoRevision;


    // --- CONSTRUCTOR ---
    public GestorRegistrarResultadoRevisionManual(EventoSismicoRepository e, EstadoRepository es, SesionRepository s, GenerarSismogramaService gss) {
        this.eventoSismicoRepository = e;
        this.estadoRepository = es;
        this.sesionRepository = s;
        this.generarSismogramaService = gss;
    }

    // --- MÉTODOS PÚBLICOS (En orden secuencial según el diagrama) ---

    public List<EventoSismicoResumenDTO> registrarNuevaRevision() { // MSG 3
        this.sesionActual = sesionRepository.findById(1L).orElseThrow(() -> new RuntimeException("Sesión activa no encontrada."));
        this.datosEventosSismicos = this.ordenarEventoSismico(this.buscarEventosSismicos());  ///////EJEMPLO DEL PATRON CONTROLADOR
        return this.datosEventosSismicos;
    }

    public void tomarSeleccionEventoSismico(Long id) { 
        if (id == null) {
            throw new IllegalArgumentException("El ID del evento no puede ser nulo.");
        }
        System.out.println("ID del evento seleccionado para actuar: " + id);

        this.sesionActual = sesionRepository.findById(1L)
                .orElseThrow(() -> new RuntimeException("Sesión activa no encontrada para esta operación."));
                
        EventoSismico evento = eventoSismicoRepository.findById(id) //Asignacion directamente por id ya que no encontramos otra manera de almacenar el dato
            .orElseThrow(() -> new RuntimeException("No se pudo encontrar el evento con ID: " + id));
        this.bloquearEvento(evento); 
    }

    public void bloquearEvento(EventoSismico evento) { // MSG 19
        this.punteroBloqueadoEnRevision = this.buscarEstadoBloqueado(); // MSG 20
        this.fechaHoraActual = this.tomarFechaHoraActual(); // MSG 23
        this.punteroEmpleado = this.buscarEmpleadoLogueado(); // MSG 24
        evento.revisar(punteroBloqueadoEnRevision, fechaHoraActual, punteroEmpleado); // MSG 27 -- Delega Evento Sismico
        eventoSismicoRepository.save(evento);
        this.buscarDatosSismicos(evento.getId()); 
    }

   public Map<String, Object> buscarDatosSismicos(Long id) { 
    if (id == null) { 
        throw new IllegalArgumentException("ID de evento no puede ser nulo."); 
    }
    // Buscar el evento por ID
    EventoSismico evento = eventoSismicoRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Evento no encontrado con ID: " + id));

    // Usar el evento encontrado en lugar de this.eventoSeleccionado
    String clasificacion = evento.getClasificacion(); 
    String alcance = evento.getAlcance(); 
    String origen = evento.getOrigen(); 
    /////esto agregamos para que sea mostrado en el front el pase del estado de autodetectado a bloqueado en revision
    String estadoActual = null;
    if (evento.getEstadoActual() != null) {
        estadoActual = evento.getEstadoActual().getNombreEstado(); 
    }
    ////////////// aca termina lo que agregamos para mostrar en el front
    Map<String, Object> detalles = new HashMap<>();
    detalles.put("clasificacion", clasificacion);
    detalles.put("alcance", alcance);
    detalles.put("origen_evento", origen); // 
    //agregamos para mostrar en el front
    detalles.put("estado", estadoActual);
    this.buscarSeriesTemporales(evento);
    return detalles;
}
    public void buscarSeriesTemporales(EventoSismico evento){
        this.seriesTemporalesEventoSeleccionado = evento.obtenerSeriesTemporales(); 
        this.llamarCasoDeUsoGenerarSismograma(); 
    }

    public void tomarOpcVerMapa() { 
        System.out.println("Procesando selección 'Ver Mapa'.");
    }

    public void tomarOpcModificarDatos() { 
        System.out.println("Procesando selección 'Modificar Datos'.");
    }

    public void tomarSeleccionRechazada(Long id ) { 
        if (id == null) {
            throw new IllegalArgumentException("El ID del evento no puede ser nulo.");
        }
        System.out.println("ID del evento seleccionado para actuar: " + id);

        EventoSismico evento = eventoSismicoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("No se pudo encontrar el evento con ID: " + id));
        this.evaluarResultadoInspeccion(evento); 
    }

    public void evaluarResultadoInspeccion(EventoSismico evento) { 
        this.validarExistenciaDatos(evento); 
    }

    public void validarExistenciaDatos(EventoSismico evento) { 
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

        this.rechazarEvento(evento); 
    }   

    public void rechazarEvento(EventoSismico evento) { // MSG 65
            this.punteroRechazado = this.buscarEstadoRechazado(); 
            this.fechaHoraActual = this.tomarFechaHoraActual(); 
            evento.rechazar(punteroRechazado, fechaHoraActual, punteroEmpleado); // MSG 70
            eventoSismicoRepository.save(evento);
            finCU();
        }

    public void finCU() { 
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
    }

    // --- MÉTODOS PRIVADOS (Implementación de auto-mensajes que no son parte de la cadena de rechazo) ---

    private List<EventoSismicoResumenDTO> buscarEventosSismicos() { 
        this.eventosSismicos = this.eventoSismicoRepository.findAll();
                List<EventoSismicoResumenDTO> eventosParaRevision = new ArrayList<>();
        for (EventoSismico evento : this.eventosSismicos) {
            if (evento.esAutoDetectado()) {  ///EJEMPLO DE PATRON EXPERTO DELEGA LA RESPONSABILIDAD A EVENTO QUIEN CONOCE SU ESTADO
                eventosParaRevision.add(evento.getDatos()); 
            }
        }
        return eventosParaRevision;
    }
    
    private List<EventoSismicoResumenDTO> ordenarEventoSismico(List<EventoSismicoResumenDTO> listaEventos) { 
        listaEventos.sort(Comparator.comparing(EventoSismicoResumenDTO::getFechaHoraOcurrencia).reversed());
        return listaEventos;
    }

    private void llamarCasoDeUsoGenerarSismograma() { 
        this.generarSismogramaService.generarSismograma();
    }

    private LocalDateTime tomarFechaHoraActual() { 
        return LocalDateTime.now();
    }

    private Empleado buscarEmpleadoLogueado() { 
        if (this.sesionActual == null) { 
            this.sesionActual = sesionRepository.findById(1L)
                    .orElseThrow(() -> new RuntimeException("No hay sesión activa"));
        }
        return this.sesionActual.obtenerUsuarioLogueado(); 
    }
}