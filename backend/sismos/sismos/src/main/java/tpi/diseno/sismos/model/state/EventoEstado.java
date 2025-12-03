package tpi.diseno.sismos.model.state;

import java.time.LocalDateTime;

import tpi.diseno.sismos.model.Empleado;
import tpi.diseno.sismos.model.EventoSismico;
import tpi.diseno.sismos.repository.EstadoRepository;

public interface EventoEstado {

    void revisar(LocalDateTime ahora, EventoSismico evento, Empleado usuario, EstadoRepository estadosRepo);

    void rechazar(LocalDateTime ahora, EventoSismico evento, Empleado usuario, EstadoRepository estadosRepo);

    String nombre();
}
