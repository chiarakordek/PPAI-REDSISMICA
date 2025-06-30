package tpi.diseno.sismos.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class CambioEstado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;

 /** Evento sísmico al que pertenece este cambio de estado. */
    @ManyToOne
    private EventoSismico eventoSismico;

/** Estado que se asigna al evento sísmico tras este cambio. */
    @ManyToOne
    private Estado estado;

/** Empleado responsable del cambio de estado del Evento Sísmico**/ 
    @ManyToOne
    private Empleado empleadoResponsable;

    /**Constructor */
    public CambioEstado() {
    }

    public CambioEstado( LocalDateTime fechaInicio, EventoSismico eventoSismico, Estado estado, Empleado empleadoResponsable) {
        this.fechaInicio = fechaInicio;
        this.eventoSismico = eventoSismico;
        this.estado = estado;
        this.empleadoResponsable = empleadoResponsable;
    }

    public Long getId() { 
        return this.id; 
    }

    public void setId(Long id) { 
        this.id = id; 
    }

    public LocalDateTime getFechaInicio() { 
        return fechaInicio; 
    }

    public void setFechaInicio(LocalDateTime fechaInicio) { 
        this.fechaInicio = fechaInicio; 
    }

    public LocalDateTime getFechaFin() { 
        return fechaFin; 
    }

    public void setFechaFin() { 
        this.fechaFin = LocalDateTime.now(); 
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

    public Empleado getEmpleadoResponsable() { 
        return empleadoResponsable; 
    }

    public void setEmpleadoResponsable(Empleado empleadoResponsable) { 
        this.empleadoResponsable = empleadoResponsable; 
    }


    public boolean esUltimoCambioEstado(){
        return this.fechaFin == null;
    }
}