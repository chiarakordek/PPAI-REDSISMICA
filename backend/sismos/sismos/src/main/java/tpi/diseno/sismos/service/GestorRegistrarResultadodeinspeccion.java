package tpi.diseno.sismos.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tpi.diseno.sismos.model.DatosEventoSismico;
import tpi.diseno.sismos.model.Empleado;
import tpi.diseno.sismos.model.Estado;
import tpi.diseno.sismos.model.EventoSismico;
import tpi.diseno.sismos.model.Sesion;
import tpi.diseno.sismos.repository.EmpleadoRepository;
import tpi.diseno.sismos.repository.EstadoRepository;
import tpi.diseno.sismos.repository.EventoSismicoRepository;
import tpi.diseno.sismos.repository.SismografoRepository;

@Service
public class GestorRegistrarResultadodeinspeccion {

    @Autowired
    private EventoSismicoRepository eventoRepo;

    @Autowired
    private EmpleadoRepository empleadoRepo;

    @Autowired
    private EstadoRepository estadoRepo;

    @Autowired
    private Sesion sesionRepo;


    @Autowired
    private SismografoRepository sismografoRepo;



    // 1. Buscar eventos autodetectados pendientes
    public List<DatosEventoSismico> buscarEventosSismicos() {
        List<EventoSismico> todos = eventoRepo.findAll();
        List<DatosEventoSismico> resultado = new ArrayList<>();

        for (EventoSismico evento : todos) {
            if (evento.esAutodetectado() && evento.esPendiente()) {
                resultado.add(evento.getDatos());
            }
        }

        // Ordenar por fecha de ocurrencia (usando tu getter real: getFechaHora())
        resultado.sort(Comparator.comparing(DatosEventoSismico::getFechaHora));

        return resultado;
    }

    // 2. Bloquear el evento
    public void tomarSeleccionEventoSismico(Long idEventoSeleccionado) {
    // Buscar el evento por ID
        EventoSismico evento = eventoRepo.findById(idEventoSeleccionado)
            .orElseThrow(() -> new RuntimeException("Evento no encontrado"));

    // Buscar el estado "BloqueadoEnRevision" con ambito EventoSismico
        List<Estado> estados = estadoRepo.findAll();
        Estado estadoBloqueado = estados.stream()
            .filter(e -> e.esAmbitoEventoSismico() && e.esBloqueadoEnRevision())
            .findFirst()
            .orElseThrow(() -> new RuntimeException("Estado 'BloqueadoEnRevision' no encontrado"));
        
    // Obtener la fecha y hora actual
        LocalDateTime fechaHoraActual = LocalDateTime.now();

    // Obtener el empleado logueado
        Empleado empleado = sesionRepo.getEmpleado(); 

        
    }
}