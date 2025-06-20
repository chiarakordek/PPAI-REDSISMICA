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
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;

 /** Evento sísmico al que pertenece este cambio de estado. */
    @ManyToOne
    private EventoSismico eventoSismico;

/** Estado que se asigna al evento sísmico tras este cambio. */
    @ManyToOne
    private Estado estado;

    /**Constructor */
    public CambioEstado() {
    }

    public CambioEstado(String denominacion, String nombreUnidadMedida, Double valorUmbral, LocalDateTime fechaInicio, LocalDateTime fechaFin, EventoSismico eventoSismico, Estado estado) {
        this.denominacion = denominacion;
        this.nombreUnidadMedida = nombreUnidadMedida;
        this.valorUmbral = valorUmbral;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.eventoSismico = eventoSismico;
        this.estado = estado;
    }

    public Long getId() { return this.id; }

    public void setId(Long id) { this.id = id; }

    public String getDenominacion() { return this.denominacion; }

    public void setDenominacion(String denominacion, Long id) { 
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

    public LocalDateTime getFechaInicio() { return fechaInicio; }

    public void setFechaInicio(LocalDateTime fechaInicio) { this.fechaInicio = fechaInicio; }

    public LocalDateTime getFechaFin() { return fechaFin; }

    public void setFechaFin(LocalDateTime fechaFin) { this.fechaFin = fechaFin; }

    public EventoSismico getEventoSismico() {
        return eventoSismico;
    }
    public void setEventoSismico(EventoSismico eventoSismico) {
        this.eventoSismico = eventoSismico;
    }

    public Estado getEstado() { return estado; }

    public void setEstado(Estado estado) { this.estado = estado; }

    public boolean esUltimoCambioEstado(){
        return this.fechaFin == null;
    }
}