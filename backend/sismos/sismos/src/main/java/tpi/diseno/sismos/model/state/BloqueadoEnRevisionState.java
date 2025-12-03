package tpi.diseno.sismos.model.state;

import java.time.LocalDateTime;
import java.util.List;

import tpi.diseno.sismos.model.CambioEstado;
import tpi.diseno.sismos.model.Empleado;
import tpi.diseno.sismos.model.Estado;
import tpi.diseno.sismos.model.EventoSismico;
import tpi.diseno.sismos.repository.EstadoRepository;

public class BloqueadoEnRevisionState implements EventoEstado {

    @Override
    public void revisar(LocalDateTime ahora, EventoSismico evento, Empleado usuario, EstadoRepository estadosRepo) {
        throw new IllegalStateException("No se puede volver a revisar: el evento ya está BloqueadoEnRevision");
    }

    @Override
    public void rechazar(LocalDateTime ahora, EventoSismico evento, Empleado usuario, EstadoRepository estadosRepo) {
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

        // Crear el nuevo estado Rechazado
        Estado rechazado = estadosRepo.findByNombreEstado("Rechazado")
                .orElseThrow(() -> new IllegalStateException("Estado 'Rechazado' no existe"));

        CambioEstado nuevo = new CambioEstado(ahora, rechazado, evento, usuario);
        evento.getHistorialCambioEstado().add(nuevo);
        evento.setEstado(rechazado);
    }

    @Override
    public String nombre() {
        return "BloqueadoEnRevision";
    }
}
