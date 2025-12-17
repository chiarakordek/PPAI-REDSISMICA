package tpi.diseno.sismos.model.state;

import tpi.diseno.sismos.model.Estado;

public class EventoEstadoFactory {

    public static Estado fromNombre(String nombre) {
        if ("PendienteDeRevision".equals(nombre) || "Autodetectado".equals(nombre)) {
            return new PendienteDeRevisionState();
        }
        if ("BloqueadoEnRevision".equals(nombre)) {
            return new BloqueadoEnRevisionState();
        }
        if ("Rechazado".equals(nombre)) {
            return new RechazadoState();
        }
        if ("Confirmado".equals(nombre)) {
            return new ConfirmadoPorPersonalState();
        }
        if ("DerivadoAExperto".equals(nombre)) {
            return new DerivadoState(); //flujo alternativo para el funcionamiento de derivado a experto
        }
        throw new IllegalArgumentException("Estado no soportado para patrón State: " + nombre);
    }
}
