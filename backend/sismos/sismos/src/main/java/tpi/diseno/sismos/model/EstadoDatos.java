package tpi.diseno.sismos.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class EstadoDatos {

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Long id;
    
    private String ambito;
    
    @Column(name="nombre_estado")
    private String nombreEstado;

    public EstadoDatos() {}
    
    public EstadoDatos(String nombreEstado, String ambito) {
        this.nombreEstado = nombreEstado;
        this.ambito = ambito;
    }

    /**
     * esAutoDetectado() -> verifica si el nombre de este estado es autodetectado.
     */
    public boolean  esAutoDetectado() {
        return "Autodetectado".equals(this.nombreEstado);
    }

    /**
     * esAmbitoEventoSismico() -> Responde al Gestor para verificar el ámbito.
     * este debe ser evento sismico
     */
    public boolean esAmbitoEventoSismico() {
        return "EventoSismico".equals(this.ambito);
    }

    /**
     * esBloqueadoEnRevision() -> verificar si es el estado es bloqueado en revision.
     */
    public boolean esBloqueadoEnRevision() {
        return "BloqueadoEnRevision".equals(this.nombreEstado);
    }

    /**
     * esRechazado() -> verifica si el nombre del estado es rechazado.
     */
    public boolean esRechazado() {
        return "Rechazado".equals(this.nombreEstado);
    }

    /**
     * esConfirmado() -> verifica si el nombre del estado es confirmado.
     */
    public boolean esConfirmado() {
        return "ConfirmadoPorPersonal".equals(this.nombreEstado);
    }

    /**
     * esDerivadoAExperto() -> verifica si el nombre del estado es derivado a experto.
     */
    public boolean esDerivadoAExperto() {
        return "DerivadoAExperto".equals(this.nombreEstado); //flujo alternativo para el funcionamiento de derivado a experto
    }
}
