package tpi.diseno.sismos.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Estado {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    private String ambito;
    private String nombreEstado;

    /**
     * MSG 6: esPendienteDeRevision() -> Responde al mensaje que le envía el EventoSismico.
     */
    public boolean esPendienteDeRevision() {
        return "PendienteDeRevision".equals(this.nombreEstado);
    }


    /**
     * MSG 21: esAmbitoEventoSismico() -> Responde al Gestor para verificar el ámbito.
     * Este mensaje es reutilizado en MSG 67 secuencias del diagrama.
     */
    public boolean esAmbitoEventoSismico() {
        return "EventoSismico".equals(this.ambito);
    }

    /**
     * MSG 22: esBloqueadoEnRevision() -> Responde al Gestor para verificar si es el estado de bloqueo.
     */
    public boolean esBloqueadoEnRevision() {
        return "BloqueadoEnRevision".equals(this.nombreEstado);
    }

    /**
     * MSG 68: esRechazado() -> Responde al Gestor para verificar si es el estado de rechazo.
     */
    public boolean esRechazado() {
        return "Rechazado".equals(this.nombreEstado);
    }
}