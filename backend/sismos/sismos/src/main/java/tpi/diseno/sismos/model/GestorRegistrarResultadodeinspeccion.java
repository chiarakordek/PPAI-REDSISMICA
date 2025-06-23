package tpi.diseno.sismos.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Service;

import tpi.diseno.sismos.repository.EstadoRepository;
import tpi.diseno.sismos.repository.EventoSismicoRepository;


@Service
public class GestorRegistrarResultadodeinspeccion {

    private LocalDateTime fechayHoraOcurrencia;
    private LocalDateTime fechaHoraActual;

    private List<EventoSismico> eventosSismicos;
    private EventoSismico eventoSeleccionado;
    private ArrayList<SerieTemporal> SeriesTemporalesEventoSeleccionado;

    private List<String> datosEventosSismicos;
    private Estado punteroBloqueadoEnRevision;
    private Estado punteroRechazado;

    private Sesion sesionActual;
    private Empleado punteroEmpleado;
    private Sesion sesion;

    private enum opcionResultadoRevision {
        RECHAZADO,
        CONFIRMADO,
        DERIVADO
    }

    private List<Estado> estados;

    private EventoSismicoRepository eventoSismicoRepository;
    private EstadoRepository estadoRepository;

    public GestorRegistrarResultadodeinspeccion( EventoSismicoRepository eventoSismicoRepository) {
        this.eventoSismicoRepository = eventoSismicoRepository;
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
    }

    public void buscarEstadoBloqueado(){
        this.estados = this.estadoRepository.findAll();
        for (Estado estado : estados) {
            if (estado.esAmbitoEventoSismico() && estado.esBloqueadoEnRevision()){
                this.punteroBloqueadoEnRevision = estado;
            }
        }
    }

    public void tomarFechaHoraActual(){
        this.fechaHoraActual = LocalDateTime.now();
    }
    //Busca el empleado llamando al metodo de la sesion para obtenerlo
    public Empleado buscarEmpleadoLogueado() {
        return sesion.obtenerUsuarioLogueado();
    }
    //Cambia estado del evento a Bloqueado
    public void bloquearEvento(EventoSismico evento){
        evento.revisar(fechaHoraActual, evento, punteroBloqueadoEnRevision, punteroEmpleado);
        
    }
    //Busca datos 
    public void buscarDatosSismicos(EventoSismico evento){
        evento.obtenerSeriesTemporales();

    }
}
