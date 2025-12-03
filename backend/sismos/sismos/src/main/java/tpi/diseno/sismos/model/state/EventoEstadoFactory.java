package tpi.diseno.sismos.model.state;

public class EventoEstadoFactory {

    public static EventoEstado fromNombre(String nombre) {
        if ("PendienteDeRevision".equals(nombre) || "Autodetectado".equals(nombre)) {
            return new PendienteDeRevisionState();
        }
        if ("BloqueadoEnRevision".equals(nombre)) {
            return new BloqueadoEnRevisionState();
        }
        if ("Rechazado".equals(nombre)) {
            return new RechazadoState();
        }
        throw new IllegalArgumentException("Estado no soportado para patrón State: " + nombre);
    }
}
