package tpi.diseno.sismos.model.state;

import tpi.diseno.sismos.model.Estado;
import tpi.diseno.sismos.model.EventoSismico;
import tpi.diseno.sismos.model.Empleado;
import tpi.diseno.sismos.repository.EstadoRepository;
import java.time.LocalDateTime;

public class DerivadoAExpertoState implements Estado {
    
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
    public boolean esConfirmado() {
        return false;
    }

    @Override
    public boolean esDerivadoAExperto() {
        return true; //flujo alternativo para el funcionamiento de derivado a experto
    }

    @Override
    public void revisar(LocalDateTime ahora, EventoSismico evento, Empleado usuario, EstadoRepository estadosRepo) {
        throw new UnsupportedOperationException("No se puede revisar un evento ya derivado a experto");
    }

    @Override
    public void derivado(LocalDateTime ahora, EventoSismico evento, Empleado usuario, EstadoRepository estadosRepo) {
        throw new UnsupportedOperationException("El evento ya está derivado a experto");
    }

    @Override
    public void anulado(LocalDateTime ahora, EventoSismico evento, Empleado usuario, EstadoRepository estadosRepo) {
        throw new UnsupportedOperationException("No se puede anular un evento derivado a experto");
    }

    @Override
    public void confirmado(LocalDateTime ahora, EventoSismico evento, Empleado usuario, EstadoRepository estadosRepo) {
        throw new UnsupportedOperationException("No se puede confirmar un evento derivado a experto");
    }

    @Override
    public void cerrado(LocalDateTime ahora, EventoSismico evento, Empleado usuario, EstadoRepository estadosRepo) {
        // Implementación específica para derivado a experto
    }

    @Override
    public void rechazar(LocalDateTime ahora, EventoSismico evento, Empleado usuario, EstadoRepository estadosRepo) {
        throw new UnsupportedOperationException("No se puede rechazar un evento derivado a experto");
    }

    @Override
    public void pendientedecierre() {
        throw new UnsupportedOperationException("Operación no permitida en estado DerivadoAExperto");
    }

    @Override
    public void cerrarCambioEstado(LocalDateTime ahora, EventoSismico evento) {
        // Implementación específica para derivado a experto
    }

    @Override
    public void crearNuevoEstadoBloqueadoEnRevision(LocalDateTime ahora, EventoSismico evento, Empleado usuario, EstadoRepository estadosRepo) {
        throw new UnsupportedOperationException("No se puede crear estado BloqueadoEnRevision desde DerivadoAExperto");
    }

    @Override
    public void crearNuevoEstadoRechazado(LocalDateTime ahora, EventoSismico evento, Empleado usuario, EstadoRepository estadosRepo) {
        throw new UnsupportedOperationException("No se puede crear estado Rechazado desde DerivadoAExperto");
    }

    @Override
    public void crearNuevoEstadoConfirmado(LocalDateTime ahora, EventoSismico evento, Empleado usuario, EstadoRepository estadosRepo) {
        throw new UnsupportedOperationException("No se puede crear estado Confirmado desde DerivadoAExperto");
    }

    @Override
    public void crearNuevoEstadoDerivadoAExperto(LocalDateTime ahora, EventoSismico evento, Empleado usuario, EstadoRepository estadosRepo) {
        throw new UnsupportedOperationException("No se puede crear estado DerivadoAExperto desde DerivadoAExperto"); //flujo alternativo para el funcionamiento de derivado a experto
    }

    @Override
    public void crearCambioEstado(LocalDateTime ahora, EventoSismico evento, Empleado usuario, EstadoRepository estadosRepo) {
        throw new UnsupportedOperationException("No se pueden crear cambios de estado desde DerivadoAExperto");
    }

    @Override
    public String getNombreEstado() {
        return "DerivadoAExperto";
    }

    @Override
    public String getAmbito() {
        return "EventoSismico";
    }
}
