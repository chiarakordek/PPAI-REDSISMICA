package tpi.diseno.sismos.model.state;

import java.time.LocalDateTime;

import tpi.diseno.sismos.model.Empleado;
import tpi.diseno.sismos.model.EventoSismico;
import tpi.diseno.sismos.repository.EstadoRepository;

public class RechazadoState implements EventoEstado {

    @Override
    public void revisar(LocalDateTime ahora, EventoSismico evento, Empleado usuario, EstadoRepository estadosRepo) {
        throw new IllegalStateException("No se puede revisar un evento ya Rechazado");
    }

    @Override
    public void rechazar(LocalDateTime ahora, EventoSismico evento, Empleado usuario, EstadoRepository estadosRepo) {
        throw new IllegalStateException("El evento ya está en estado Rechazado");
    }

    @Override
    public String nombre() {
        return "Rechazado";
    }
}
