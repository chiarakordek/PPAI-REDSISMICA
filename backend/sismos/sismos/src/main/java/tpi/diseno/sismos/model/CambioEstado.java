package tpi.diseno.sismos.model;

import com.fasterxml.jackson.annotation.JsonBackReference; // <-- AÑADIR IMPORT
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class CambioEstado {

 //ATRIBUTOS
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference("evento-cambioestado") 
    private EventoSismico eventoSismico;

    @ManyToOne
    private Estado estado;

    @ManyToOne
    private Empleado empleadoResponsable;



/////CONSTRUCTORES
    public CambioEstado() {}

    public CambioEstado( LocalDateTime fechaInicio, EventoSismico eventoSismico, Estado estado, Empleado empleadoResponsable) { 
        this.fechaInicio = fechaInicio; 
        this.eventoSismico = eventoSismico; 
        this.estado = estado; 
        this.empleadoResponsable = empleadoResponsable; 
    }


    // MÉTODOS DE NEGOCIO
// Comprueba si esta instancia representa el estado actual del evento sísmico.
    public boolean esUltimoCambioEstado() {
        return this.fechaFin == null;
    }


    // =====================================================================================
    // GETTERS Y SETTERS
    // =====================================================================================

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaFin(LocalDateTime fechaFin) { 
        this.fechaFin = fechaFin;
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
}