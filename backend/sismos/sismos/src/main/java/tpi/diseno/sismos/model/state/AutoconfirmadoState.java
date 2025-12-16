package tpi.diseno.sismos.model.state;

import tpi.diseno.sismos.model.Estado;
import tpi.diseno.sismos.model.EventoSismico;
import tpi.diseno.sismos.model.Empleado;
import tpi.diseno.sismos.repository.EstadoRepository;
import java.time.LocalDateTime;

public class AutoconfirmadoState implements Estado {
    
    @Override
    public boolean esAutoDetectado() {
        return false;
    }

    @Override
    public boolean esAmbitoEventoSismico() {
        return false;
    }

    @Override
    public boolean esBloqueadoEnRevision() {
        return false;
    }

    @Override
    public boolean esRechazado() {
        return false;
    }

    @Override
    public void revisar(LocalDateTime ahora, EventoSismico evento, Empleado usuario, EstadoRepository estadosRepo) {
        // Implementación específica para autoconfirmado
    }

    @Override
    public void derivado(LocalDateTime ahora, EventoSismico evento, Empleado usuario, EstadoRepository estadosRepo) {
        // Implementación específica para autoconfirmado
    }

    @Override
    public void anulado(LocalDateTime ahora, EventoSismico evento, Empleado usuario, EstadoRepository estadosRepo) {
        // Implementación específica para autoconfirmado
    }

    @Override
    public void confirmado(LocalDateTime ahora, EventoSismico evento, Empleado usuario, EstadoRepository estadosRepo) {
        // Implementación específica para autoconfirmado
    }

    @Override
    public void cerrado(LocalDateTime ahora, EventoSismico evento, Empleado usuario, EstadoRepository estadosRepo) {
        // Implementación específica para autoconfirmado
    }

    @Override
    public void rechazar(LocalDateTime ahora, EventoSismico evento, Empleado usuario, EstadoRepository estadosRepo) {
        // Implementación específica para autoconfirmado
    }

    @Override
    public void pendientedecierre() {
        // Implementación específica para autoconfirmado
    }

    @Override
    public void crearCambioEstado(LocalDateTime ahora, EventoSismico evento, Empleado usuario, EstadoRepository estadosRepo) {
        // Implementación específica para autoconfirmado
    }

    @Override
    public void cerrarCambioEstado(LocalDateTime ahora, EventoSismico evento) {
        // Implementación específica para autoconfirmado
    }

    @Override
    public void crearNuevoEstadoBloqueadoEnRevision(LocalDateTime ahora, EventoSismico evento, Empleado usuario, EstadoRepository estadosRepo) {
        // Implementación específica para autoconfirmado
    }

    @Override
    public void crearNuevoEstadoRechazado(LocalDateTime ahora, EventoSismico evento, Empleado usuario, EstadoRepository estadosRepo) {
        // Implementación específica para autoconfirmado
    }

    @Override
    public String getNombreEstado() {
        return "Autoconfirmado";
    }

    @Override
    public String getAmbito() {
        return "EventoSismico";
    }
}
