package tpi.diseno.sismos.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;

@Entity
public class CambioEstado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String denominacion;
    private String nombreUnidadMedida;
    private Double valorUmbral;
    private LocalDateTime fechaCambioEstado;

 /** Evento sísmico al que pertenece este cambio de estado. */
    @ManyToOne
    private EventoSismico eventoSismico;

/** Estado que se asigna al evento sísmico tras este cambio. */
    @ManyToOne
    private Estado estado;

    /**Constructor */
    public CambioEstado() {
    }

    public CambioEstado(String denominacion, String nombreUnidadMedida, Double valorUmbral, LocalDateTime fechaCambioEstado, EventoSismico eventoSismico, Estado estado) {
        this.denominacion = denominacion;
        this.nombreUnidadMedida = nombreUnidadMedida;
        this.valorUmbral = valorUmbral;
        this.fechaCambioEstado = fechaCambioEstado;
        this.eventoSismico = eventoSismico;
        this.estado = estado;
    }


/////////////////////////Getters y Setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getDenominacion() {
        return denominacion;
    }
    public void setDenominacion(String denominacion) {
        this.denominacion = denominacion;
    }

    public String getNombreUnidadMedida() {
        return nombreUnidadMedida;
    }
    
    public void setNombreUnidadMedida(String nombreUnidadMedida) {
        this.nombreUnidadMedida = nombreUnidadMedida;
    }

    public Double getValorUmbral() {
        return valorUmbral;
    }
    public void setValorUmbral(Double valorUmbral) {
        this.valorUmbral = valorUmbral;
    }

    public LocalDateTime getFechaCambioEstado() {
        return fechaCambioEstado;
    }
    public void setFechaCambioEstado(LocalDateTime fechaCambioEstado) {
        this.fechaCambioEstado = fechaCambioEstado;
    }

    public EventoSismico getEventoSismico() {
        return eventoSismico;
    }
    public void setEventoSismico(EventoSismico eventoSismico) {
        this.eventoSismico = eventoSismico;
    }

    public Estado getEstado() {
        return estado;
    }
    public void setEstado(Estado estado) {
        this.estado = estado;
    }

}
