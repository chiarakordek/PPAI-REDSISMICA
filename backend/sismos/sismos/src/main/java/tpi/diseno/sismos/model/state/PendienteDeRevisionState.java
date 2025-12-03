package tpi.diseno.sismos.model.state;

import java.time.LocalDateTime;
import java.util.List;

import tpi.diseno.sismos.model.CambioEstado;
import tpi.diseno.sismos.model.Empleado;
import tpi.diseno.sismos.model.Estado;
import tpi.diseno.sismos.model.EventoSismico;
import tpi.diseno.sismos.repository.EstadoRepository;

public class PendienteDeRevisionState implements EventoEstado {

    @Override
    public void revisar(LocalDateTime ahora, EventoSismico evento, Empleado usuario, EstadoRepository estadosRepo) {
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

        // Crear el nuevo estado BloqueadoEnRevision
        Estado bloqueado = estadosRepo.findByNombreEstado("BloqueadoEnRevision")
                .orElseThrow(() -> new IllegalStateException("Estado 'BloqueadoEnRevision' no existe"));

        CambioEstado nuevo = new CambioEstado(ahora, bloqueado, evento, usuario);
        evento.getHistorialCambioEstado().add(nuevo);
        evento.setEstado(bloqueado);
    }

    @Override
    public void rechazar(LocalDateTime ahora, EventoSismico evento, Empleado usuario, EstadoRepository estadosRepo) {
        throw new IllegalStateException("No se puede rechazar directamente un evento en estado PendienteDeRevision");
    }

    @Override
    public String nombre() {
        return "PendienteDeRevision";
    }
}
