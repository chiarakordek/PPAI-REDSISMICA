package tpi.diseno.sismos.model.state;

import tpi.diseno.sismos.model.Estado;
import tpi.diseno.sismos.model.EventoSismico;
import tpi.diseno.sismos.model.Empleado;
import tpi.diseno.sismos.repository.EstadoRepository;
import java.time.LocalDateTime;

public class ConfirmadoPorPersonalState implements Estado {
    
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
        return false;
    }

    @Override
    public boolean esConfirmado() {
        return true;
    }

    @Override
    public boolean esDerivadoAExperto() {
        return false; //flujo alternativo para el funcionamiento de derivado a experto
    }

    @Override
    public void revisar(LocalDateTime ahora, EventoSismico evento, Empleado usuario, EstadoRepository estadosRepo) {
        throw new IllegalStateException("No se puede revisar un evento ya ConfirmadoPorPersonal");
    }

    @Override
    public void derivado(LocalDateTime ahora, EventoSismico evento, Empleado usuario, EstadoRepository estadosRepo) {
        throw new UnsupportedOperationException("Operación no permitida en estado ConfirmadoPorPersonal");
    }

    @Override
    public void anulado(LocalDateTime ahora, EventoSismico evento, Empleado usuario, EstadoRepository estadosRepo) {
        throw new UnsupportedOperationException("Operación no permitida en estado ConfirmadoPorPersonal");
    }

    @Override
    public void confirmado(LocalDateTime ahora, EventoSismico evento, Empleado usuario, EstadoRepository estadosRepo) {
        throw new IllegalStateException("El evento ya está en estado ConfirmadoPorPersonal");
    }

    @Override
    public void cerrado(LocalDateTime ahora, EventoSismico evento, Empleado usuario, EstadoRepository estadosRepo) {
        throw new UnsupportedOperationException("Operación no permitida en estado ConfirmadoPorPersonal");
    }

    @Override
    public void rechazar(LocalDateTime ahora, EventoSismico evento, Empleado usuario, EstadoRepository estadosRepo) {
        throw new UnsupportedOperationException("Operación no permitida en estado ConfirmadoPorPersonal");
    }

    @Override
    public void pendientedecierre() {
        // Implementación específica para el estado ConfirmadoPorPersonal
        System.out.println("Ejecutando pendientedecierre en estado ConfirmadoPorPersonal");
    }

    @Override
    public void crearCambioEstado(LocalDateTime ahora, EventoSismico evento, Empleado usuario, EstadoRepository estadosRepo) {
        throw new UnsupportedOperationException("No se pueden crear cambios de estado desde el estado ConfirmadoPorPersonal");
    }

    @Override
    public void cerrarCambioEstado(LocalDateTime ahora, EventoSismico evento) {
        throw new UnsupportedOperationException("No se pueden cerrar cambios de estado desde el estado ConfirmadoPorPersonal");
    }

    @Override
    public void crearNuevoEstadoBloqueadoEnRevision(LocalDateTime ahora, EventoSismico evento, Empleado usuario, EstadoRepository estadosRepo) {
        throw new UnsupportedOperationException("No se puede crear estado BloqueadoEnRevision desde el estado ConfirmadoPorPersonal");
    }

    @Override
    public void crearNuevoEstadoRechazado(LocalDateTime ahora, EventoSismico evento, Empleado usuario, EstadoRepository estadosRepo) {
        throw new UnsupportedOperationException("No se puede crear estado Rechazado desde el estado ConfirmadoPorPersonal");
    }

    @Override
    public void crearNuevoEstadoConfirmado(LocalDateTime ahora, EventoSismico evento, Empleado usuario, EstadoRepository estadosRepo) {
        throw new UnsupportedOperationException("No se puede crear estado Confirmado desde ConfirmadoPorPersonal");
    }

    @Override
    public void crearNuevoEstadoDerivadoAExperto(LocalDateTime ahora, EventoSismico evento, Empleado usuario, EstadoRepository estadosRepo) {
        throw new UnsupportedOperationException("No se puede crear estado DerivadoAExperto desde ConfirmadoPorPersonal"); //flujo alternativo para el funcionamiento de derivado a experto
    }

    @Override
    public String getNombreEstado() {
        return "ConfirmadoPorPersonal";
    }

    @Override
    public String getAmbito() {
        return "EventoSismico";
    }
}
