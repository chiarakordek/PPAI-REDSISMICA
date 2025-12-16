package tpi.diseno.sismos.model;

import java.time.LocalDateTime;
import tpi.diseno.sismos.repository.EstadoRepository;

public interface Estado {

    // Métodos de consulta
    boolean esAutoDetectado();
    boolean esAmbitoEventoSismico();
    boolean esBloqueadoEnRevision();
    boolean esRechazado();
  
    // Métodos de la maquina de estados
    void revisar(LocalDateTime ahora, EventoSismico evento, Empleado usuario, EstadoRepository estadosRepo);
    void derivado(LocalDateTime ahora, EventoSismico evento, Empleado usuario, EstadoRepository estadosRepo);
    void anulado(LocalDateTime ahora, EventoSismico evento, Empleado usuario, EstadoRepository estadosRepo);
    void confirmado(LocalDateTime ahora, EventoSismico evento, Empleado usuario, EstadoRepository estadosRepo);
    void cerrado(LocalDateTime ahora, EventoSismico evento, Empleado usuario, EstadoRepository estadosRepo);
    void rechazar(LocalDateTime ahora, EventoSismico evento, Empleado usuario, EstadoRepository estadosRepo);
    void pendientedecierre();

    // Métodos específicos del patrón State según diagrama
    void crearCambioEstado(LocalDateTime ahora, EventoSismico evento, Empleado usuario, EstadoRepository estadosRepo);
    void cerrarCambioEstado(LocalDateTime ahora, EventoSismico evento);
    void crearNuevoEstadoBloqueadoEnRevision(LocalDateTime ahora, EventoSismico evento, Empleado usuario, EstadoRepository estadosRepo);
    void crearNuevoEstadoRechazado(LocalDateTime ahora, EventoSismico evento, Empleado usuario, EstadoRepository estadosRepo);

    // Métodos de acceso a datos
    String getNombreEstado();
    String getAmbito();
}