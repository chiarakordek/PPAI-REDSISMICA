package tpi.diseno.sismos.model.state;

import java.time.LocalDateTime;

import tpi.diseno.sismos.model.Empleado;
import tpi.diseno.sismos.model.Estado;
import tpi.diseno.sismos.model.EventoSismico;
import tpi.diseno.sismos.repository.EstadoRepository;

public class RechazadoState implements Estado {

    @Override
    public void revisar(LocalDateTime ahora, EventoSismico evento, Empleado usuario, EstadoRepository estadosRepo) {
        throw new IllegalStateException("No se puede revisar un evento ya Rechazado");
    }

    @Override
    public void rechazar(LocalDateTime ahora, EventoSismico evento, Empleado usuario, EstadoRepository estadosRepo) {
        throw new IllegalStateException("El evento ya está en estado Rechazado");
    }

    @Override
    public void derivado(LocalDateTime ahora, EventoSismico evento, Empleado usuario, EstadoRepository estadosRepo) {
        throw new UnsupportedOperationException("Operación no permitida en estado Rechazado");
    }

    @Override
    public void anulado(LocalDateTime ahora, EventoSismico evento, Empleado usuario, EstadoRepository estadosRepo) {
        throw new UnsupportedOperationException("Operación no permitida en estado Rechazado");
    }

    @Override
    public void confirmado(LocalDateTime ahora, EventoSismico evento, Empleado usuario, EstadoRepository estadosRepo) {
        throw new UnsupportedOperationException("Operación no permitida en estado Rechazado");
    }

    @Override
    public void cerrado(LocalDateTime ahora, EventoSismico evento, Empleado usuario, EstadoRepository estadosRepo) {
        throw new UnsupportedOperationException("Operación no permitida en estado Rechazado");
    }

    @Override
    public void pendientedecierre() {
        // Implementación específica para el estado Rechazado
        System.out.println("Ejecutando pendientedecierre en estado Rechazado");
    }

    @Override
    public void cerrarCambioEstado(LocalDateTime ahora, EventoSismico evento) {
        throw new UnsupportedOperationException("No se pueden cerrar cambios de estado desde el estado Rechazado");
    }

    @Override
    public void crearNuevoEstadoBloqueadoEnRevision(LocalDateTime ahora, EventoSismico evento, Empleado usuario, EstadoRepository estadosRepo) {
        throw new UnsupportedOperationException("No se puede crear estado BloqueadoEnRevision desde el estado Rechazado");
    }

    @Override
    public void crearNuevoEstadoRechazado(LocalDateTime ahora, EventoSismico evento, Empleado usuario, EstadoRepository estadosRepo) {
        throw new UnsupportedOperationException("No se puede crear estado Rechazado desde el estado Rechazado");
    }

    @Override
    public void crearCambioEstado(LocalDateTime ahora, EventoSismico evento, Empleado usuario, EstadoRepository estadosRepo) {
        throw new UnsupportedOperationException("No se pueden crear cambios de estado desde el estado Rechazado");
    }

    @Override
    public boolean esAutoDetectado() {
        return false;
    }

    @Override
    public boolean esAmbitoEventoSismico() {
        return true;
    }

    @Override
    public boolean esBloqueadoEnRevision() {
        return false;
    }

    @Override
    public boolean esRechazado() {
        return true;
    }

    @Override
    public String getNombreEstado() {
        return "Rechazado";
    }

    @Override
    public String getAmbito() {
        return "EventoSismico";
    }
}
