package tpi.diseno.sismos.model;

import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Comparator;

import org.hibernate.annotations.NotFound;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.*;

import tpi.diseno.sismos.repository.EventoSismicoRepository;
import tpi.diseno.sismos.repository.EstadoRepository;


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

    public 
}


