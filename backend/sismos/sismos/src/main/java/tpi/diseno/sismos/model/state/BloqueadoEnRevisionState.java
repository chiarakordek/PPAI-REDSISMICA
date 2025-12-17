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
        crearCambioEstadoRechazado(ahora, evento, usuario, estadosRepo);
    }

    @Override
    public void derivado(LocalDateTime ahora, EventoSismico evento, Empleado usuario, EstadoRepository estadosRepo) {
        // 1. Cerrar el cambio de estado actual según el diagrama
        cerrarCambioEstado(ahora, evento);
        
        // 2. Crear el nuevo estado Derivado según el diagrama
        crearNuevoEstadoDerivadoAExperto(ahora, evento, usuario, estadosRepo);
        
        // 3. Crear el cambio de estado según el diagrama
        crearCambioEstadoDerivadoAExperto(ahora, evento, usuario, estadosRepo);
    }

    @Override
    public void anulado(LocalDateTime ahora, EventoSismico evento, Empleado usuario, EstadoRepository estadosRepo) {
        throw new UnsupportedOperationException("Operación no permitida en estado BloqueadoEnRevision");
    }

    @Override
    public void confirmado(LocalDateTime ahora, EventoSismico evento, Empleado usuario, EstadoRepository estadosRepo) {
        // 1. Cerrar el cambio de estado actual según el diagrama
        cerrarCambioEstado(ahora, evento);
        
        // 2. Crear el nuevo estado Confirmado según el diagrama
        crearNuevoEstadoConfirmado(ahora, evento, usuario, estadosRepo);
        
        // 3. Crear el cambio de estado según el diagrama
        crearCambioEstadoConfirmado(ahora, evento, usuario, estadosRepo);
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
    public void crearNuevoEstadoConfirmado(LocalDateTime ahora, EventoSismico evento, Empleado usuario, EstadoRepository estadosRepo) {
        // Crear el nuevo estado ConfirmadoPorPersonal
        // Este método primero crea el estado y luego será usado por crearCambioEstado
        estadosRepo.findByNombreEstado("ConfirmadoPorPersonal")
                .orElseThrow(() -> new IllegalStateException("Estado 'ConfirmadoPorPersonal' no existe"));
    }

    @Override
    public void crearNuevoEstadoDerivadoAExperto(LocalDateTime ahora, EventoSismico evento, Empleado usuario, EstadoRepository estadosRepo) {
        // Crear el nuevo estado DerivadoAExperto
        // Este método primero crea el estado y luego será usado por crearCambioEstado
        estadosRepo.findByNombreEstado("DerivadoAExperto")
                .orElseThrow(() -> new IllegalStateException("Estado 'DerivadoAExperto' no existe")); //flujo alternativo para el funcionamiento de derivado a experto
    }

    @Override
    public void crearCambioEstado(LocalDateTime ahora, EventoSismico evento, Empleado usuario, EstadoRepository estadosRepo) {
        // Este método es usado por rechazar(), necesitamos un método específico para confirmado
        throw new UnsupportedOperationException("Usar crearCambioEstadoConfirmado() para la transición a ConfirmadoPorPersonal");
    }

    public void crearCambioEstadoRechazado(LocalDateTime ahora, EventoSismico evento, Empleado usuario, EstadoRepository estadosRepo) {
        // Crear el nuevo estado Rechazado
        EstadoDatos rechazado = estadosRepo.findByNombreEstado("Rechazado")
                .orElseThrow(() -> new IllegalStateException("Estado 'Rechazado' no existe"));

        CambioEstado nuevo = new CambioEstado(ahora, rechazado, evento, usuario);
        evento.getHistorialCambioEstado().add(nuevo);
        evento.setEstadoActual(rechazado);
    }

    public void crearCambioEstadoConfirmado(LocalDateTime ahora, EventoSismico evento, Empleado usuario, EstadoRepository estadosRepo) {
        // Crear el nuevo estado ConfirmadoPorPersonal
        EstadoDatos confirmado = estadosRepo.findByNombreEstado("ConfirmadoPorPersonal")
                .orElseThrow(() -> new IllegalStateException("Estado 'ConfirmadoPorPersonal' no existe"));

        CambioEstado nuevo = new CambioEstado(ahora, confirmado, evento, usuario);
        evento.getHistorialCambioEstado().add(nuevo);
        evento.setEstadoActual(confirmado);
    }

    public void crearCambioEstadoDerivadoAExperto(LocalDateTime ahora, EventoSismico evento, Empleado usuario, EstadoRepository estadosRepo) {
        // Crear el nuevo estado DerivadoAExperto
        EstadoDatos derivado = estadosRepo.findByNombreEstado("DerivadoAExperto")
                .orElseThrow(() -> new IllegalStateException("Estado 'DerivadoAExperto' no existe")); //flujo alternativo para el funcionamiento de derivado a experto

        CambioEstado nuevo = new CambioEstado(ahora, derivado, evento, usuario);
        evento.getHistorialCambioEstado().add(nuevo);
        evento.setEstadoActual(derivado);
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
    public boolean esConfirmado() {
        return false;
    }

    @Override
    public boolean esDerivadoAExperto() {
        return false; //flujo alternativo para el funcionamiento de derivado a experto
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
