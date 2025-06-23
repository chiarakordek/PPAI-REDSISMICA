package tpi.diseno.sismos.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Comparator;


import org.springframework.stereotype.Service;

import tpi.diseno.sismos.repository.EventoSismicoRepository;
import tpi.diseno.sismos.repository.EstadoRepository;


@Service
public class GestorRegistrarResultadodeinspeccion {

    private LocalDateTime fechaHoraActual;

    private List<EventoSismico> eventosSismicos;
    private EventoSismico eventoSeleccionado;
    private ArrayList<SerieTemporal> SeriesTemporalesEventoSeleccionado;

    private List<String> datosEventosSismicos;
    private Estado punteroBloqueadoEnRevision;
    private Estado punteroRechazado;

    private Sesion sesionActual;
    private Empleado punteroEmpleado;
    private String mapaUbicacion;

    private enum opcionResultadoRevision {
        RECHAZADO,
        CONFIRMADO,
        DERIVADO
    };

    private List<Estado> estados;

    private EventoSismicoRepository eventoSismicoRepository;
    private EstadoRepository estadoRepository;

    public GestorRegistrarResultadodeinspeccion( EventoSismicoRepository eventoSismicoRepository, EstadoRepository estadoRepository) {
        this.eventoSismicoRepository = eventoSismicoRepository;
        this.estadoRepository = estadoRepository;
    }

    public void buscarEventosSismicos(){
        List<EventoSismico> eventos = this.eventoSismicoRepository.findAll();
        if (eventos.isEmpty()){
            throw new RuntimeException("No se encontraron eventos sísmicos.");
        }
        for (EventoSismico evento : eventos) {
            if (evento.esAutodetectado() && evento.esPendiente()){
                this.eventosSismicos.add(evento);
                this.datosEventosSismicos.add(evento.getDatos());
            } 
        }
        ordenarEventoSismicos();
    }

    public void ordenarEventoSismicos(){
        this.eventosSismicos.sort(Comparator.comparing(EventoSismico::getFechaHoraOcurrencia));
    }

    public void tomarSeleccionEventoSismico(EventoSismico evento){
        this.eventoSeleccionado = evento;
        bloquearEvento();
    }

    public Estado buscarEstadoBloqueado(){
        this.estados = this.estadoRepository.findAll();
        for (Estado estado : estados) {
            if (estado.esAmbitoEventoSismico() && estado.esBloqueadoEnRevision()){
                return estado;
            }
        }
        return null;
    }

    public LocalDateTime tomarFechaHoraActual(){
        return LocalDateTime.now();
    }

    public Estado buscarEstadoRechazado(){
        this.estados = this.estadoRepository.findAll();
        for (Estado estado : estados) {
            if (estado.esAmbitoEventoSismico() && estado.esRechazado()){
                return estado;
            }
        }
        return null;
    } 

    public void tomarSeleccionRechazada(String opcion){
        if (opcionResultadoRevision.RECHAZADO.name().equalsIgnoreCase(opcion)){
            rechazararEvento();
        }else{
            throw new RuntimeException("Opción no reconocida.");
        }
    }

    public Empleado buscarEmpleadoLogueado(){
        return this.sesionActual.obtenerUsuarioLogueado();
    }

    public void bloquearEvento(){
        this.fechaHoraActual = tomarFechaHoraActual();
        this.punteroBloqueadoEnRevision = buscarEstadoBloqueado();
        if (this.punteroBloqueadoEnRevision == null){
            throw new RuntimeException("No se encontró estado de bloqueo en revisión.");
        }
        this.punteroEmpleado = buscarEmpleadoLogueado();
        this.eventoSeleccionado.revisar(fechaHoraActual, eventoSeleccionado, punteroBloqueadoEnRevision, punteroEmpleado);
    }

    public void rechazararEvento(){
        this.punteroRechazado = buscarEstadoRechazado();
        if (this.punteroRechazado == null){
            throw new RuntimeException("No se encontró estado de rechazo.");
        }
        this.fechaHoraActual = tomarFechaHoraActual();
        this.eventoSeleccionado.rechazar(fechaHoraActual, eventoSeleccionado, punteroRechazado, punteroEmpleado);
    }

    public void finCU(){
        this.sesionActual.setFechaFin(tomarFechaHoraActual());
    }
}


