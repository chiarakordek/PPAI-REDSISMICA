package tpi.diseno.sismos.model.state;

import java.time.LocalDateTime;
import java.util.List;

import tpi.diseno.sismos.model.CambioEstado;
import tpi.diseno.sismos.model.Empleado;
import tpi.diseno.sismos.model.Estado;
import tpi.diseno.sismos.model.EstadoDatos;
import tpi.diseno.sismos.model.EventoSismico;
import tpi.diseno.sismos.repository.EstadoRepository;

public class PendienteDeRevisionState implements Estado {

    @Override
    public void revisar(LocalDateTime ahora, EventoSismico evento, Empleado usuario, EstadoRepository estadosRepo) {
        // 1. Cerrar el cambio de estado actual según el diagrama
        cerrarCambioEstado(ahora, evento);
        
        // 2. Crear el nuevo estado BloqueadoEnRevision según el diagrama
        crearNuevoEstadoBloqueadoEnRevision(ahora, evento, usuario, estadosRepo);
        
        // 3. Crear el cambio de estado según el diagrama
        crearCambioEstado(ahora, evento, usuario, estadosRepo);
    }

    @Override
    public void cerrarCambioEstado(LocalDateTime ahora, EventoSismico evento) {
        // Cerrar el cambio de estado actual (si no tiene fecha de fin)
        List<CambioEstado> historial = evento.getHistorialCambioEstado();
        if (historial != null && !historial.isEmpty()) {
            for (CambioEstado ce : historial) {
                if (ce.getFechaFin() == null) {
                    ce.setFechaFin(ahora);
                    break;
                }
            }
        }
    }

    @Override
    public void crearNuevoEstadoBloqueadoEnRevision(LocalDateTime ahora, EventoSismico evento, Empleado usuario, EstadoRepository estadosRepo) {
        // Crear el nuevo estado BloqueadoEnRevision
        // Este método primero crea el estado y luego será usado por crearCambioEstado
        estadosRepo.findByNombreEstado("BloqueadoEnRevision")
                .orElseThrow(() -> new IllegalStateException("Estado 'BloqueadoEnRevision' no existe"));
    }

    @Override
    public void crearNuevoEstadoRechazado(LocalDateTime ahora, EventoSismico evento, Empleado usuario, EstadoRepository estadosRepo) {
        throw new UnsupportedOperationException("No se puede crear estado Rechazado desde PendienteDeRevision");
    }

    @Override
    public void crearNuevoEstadoConfirmado(LocalDateTime ahora, EventoSismico evento, Empleado usuario, EstadoRepository estadosRepo) {
        throw new UnsupportedOperationException("No se puede crear estado Confirmado desde PendienteDeRevision");
    }

    @Override
    public void crearNuevoEstadoDerivadoAExperto(LocalDateTime ahora, EventoSismico evento, Empleado usuario, EstadoRepository estadosRepo) {
        throw new UnsupportedOperationException("No se puede crear estado DerivadoAExperto desde PendienteDeRevision"); //flujo alternativo para el funcionamiento de derivado a experto
    }

    @Override
    public void crearCambioEstado(LocalDateTime ahora, EventoSismico evento, Empleado usuario, EstadoRepository estadosRepo) {
        // Crear el nuevo estado BloqueadoEnRevision
        EstadoDatos bloqueado = estadosRepo.findByNombreEstado("BloqueadoEnRevision")
                .orElseThrow(() -> new IllegalStateException("Estado 'BloqueadoEnRevision' no existe"));

        CambioEstado nuevo = new CambioEstado(ahora, bloqueado, evento, usuario);
        evento.getHistorialCambioEstado().add(nuevo);
        evento.setEstadoActual(bloqueado);
    }

    @Override
    public void pendientedecierre() {
        // Implementación específica para el estado PendienteDeRevision
        System.out.println("Ejecutando pendientedecierre en estado PendienteDeRevision");
    }

    @Override
    public void rechazar(LocalDateTime ahora, EventoSismico evento, Empleado usuario, EstadoRepository estadosRepo) {
        throw new IllegalStateException("No se puede rechazar directamente un evento en estado PendienteDeRevision");
    }

    @Override
    public void derivado(LocalDateTime ahora, EventoSismico evento, Empleado usuario, EstadoRepository estadosRepo) {
        throw new UnsupportedOperationException("Operación no permitida en estado PendienteDeRevision");
    }

    @Override
    public void anulado(LocalDateTime ahora, EventoSismico evento, Empleado usuario, EstadoRepository estadosRepo) {
        throw new UnsupportedOperationException("Operación no permitida en estado PendienteDeRevision");
    }

    @Override
    public void confirmado(LocalDateTime ahora, EventoSismico evento, Empleado usuario, EstadoRepository estadosRepo) {
        throw new UnsupportedOperationException("Operación no permitida en estado PendienteDeRevision");
    }

    @Override
    public void cerrado(LocalDateTime ahora, EventoSismico evento, Empleado usuario, EstadoRepository estadosRepo) {
        throw new UnsupportedOperationException("Operación no permitida en estado PendienteDeRevision");
    }

    @Override
    public boolean esAutoDetectado() {
        return true;
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
        return false;
    }

    @Override
    public boolean esDerivadoAExperto() {
        return false; //flujo alternativo para el funcionamiento de derivado a experto
    }

    @Override
    public String getNombreEstado() {
        return "PendienteDeRevision";
    }

    @Override
    public String getAmbito() {
        return "EventoSismico";
    }
}
