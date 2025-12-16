package tpi.diseno.sismos.model.state;

import java.time.LocalDateTime;
import java.util.List;

import tpi.diseno.sismos.model.CambioEstado;
import tpi.diseno.sismos.model.Empleado;
import tpi.diseno.sismos.model.Estado;
import tpi.diseno.sismos.model.EstadoDatos;
import tpi.diseno.sismos.model.EventoSismico;
import tpi.diseno.sismos.repository.EstadoRepository;

public class BloqueadoEnRevisionState implements Estado {

    @Override
    public void revisar(LocalDateTime ahora, EventoSismico evento, Empleado usuario, EstadoRepository estadosRepo) {
        throw new IllegalStateException("No se puede volver a revisar: el evento ya está BloqueadoEnRevision");
    }

    @Override
    public void rechazar(LocalDateTime ahora, EventoSismico evento, Empleado usuario, EstadoRepository estadosRepo) {
        // 1. Cerrar el cambio de estado actual según el diagrama
        cerrarCambioEstado(ahora, evento);
        
        // 2. Crear el nuevo estado Rechazado según el diagrama
        crearNuevoEstadoRechazado(ahora, evento, usuario, estadosRepo);
        
        // 3. Crear el cambio de estado según el diagrama
        crearCambioEstado(ahora, evento, usuario, estadosRepo);
    }

    @Override
    public void derivado(LocalDateTime ahora, EventoSismico evento, Empleado usuario, EstadoRepository estadosRepo) {
        throw new UnsupportedOperationException("Operación no permitida en estado BloqueadoEnRevision");
    }

    @Override
    public void anulado(LocalDateTime ahora, EventoSismico evento, Empleado usuario, EstadoRepository estadosRepo) {
        throw new UnsupportedOperationException("Operación no permitida en estado BloqueadoEnRevision");
    }

    @Override
    public void confirmado(LocalDateTime ahora, EventoSismico evento, Empleado usuario, EstadoRepository estadosRepo) {
        throw new UnsupportedOperationException("Operación no permitida en estado BloqueadoEnRevision");
    }

    @Override
    public void cerrado(LocalDateTime ahora, EventoSismico evento, Empleado usuario, EstadoRepository estadosRepo) {
        throw new UnsupportedOperationException("Operación no permitida en estado BloqueadoEnRevision");
    }

    @Override
    public void pendientedecierre() {
        // Implementación específica para el estado BloqueadoEnRevision
        System.out.println("Ejecutando pendientedecierre en estado BloqueadoEnRevision");
    }

    @Override
    public void cerrarCambioEstado(LocalDateTime ahora, EventoSismico evento) {
        // Cerrar el cambio de estado actual (BloqueadoEnRevision)
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
        throw new UnsupportedOperationException("No se puede crear estado BloqueadoEnRevision desde BloqueadoEnRevision");
    }

    @Override
    public void crearNuevoEstadoRechazado(LocalDateTime ahora, EventoSismico evento, Empleado usuario, EstadoRepository estadosRepo) {
        // Crear el nuevo estado Rechazado
        // Este método primero crea el estado y luego será usado por crearCambioEstado
        estadosRepo.findByNombreEstado("Rechazado")
                .orElseThrow(() -> new IllegalStateException("Estado 'Rechazado' no existe"));
    }

    @Override
    public void crearCambioEstado(LocalDateTime ahora, EventoSismico evento, Empleado usuario, EstadoRepository estadosRepo) {
        // Crear el nuevo estado Rechazado
        EstadoDatos rechazado = estadosRepo.findByNombreEstado("Rechazado")
                .orElseThrow(() -> new IllegalStateException("Estado 'Rechazado' no existe"));

        CambioEstado nuevo = new CambioEstado(ahora, rechazado, evento, usuario);
        evento.getHistorialCambioEstado().add(nuevo);
        evento.setEstadoActual(rechazado);
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
        return true;
    }

    @Override
    public boolean esRechazado() {
        return false;
    }

    @Override
    public String getNombreEstado() {
        return "BloqueadoEnRevision";
    }

    @Override
    public String getAmbito() {
        return "EventoSismico";
    }
}
