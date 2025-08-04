package tpi.diseno.sismos.model;

import com.fasterxml.jackson.annotation.JsonBackReference; 
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class CambioEstado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;

/** Evento sísmico al que pertenece el cambio de estado*/ 
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference("evento-cambioestado") 
    private EventoSismico eventoSismico;

/** Estado al cual corresponde el cambio de estado*/
    @ManyToOne
    private Estado estado;

/** Empleado que realizó el cambio de estado*/
    @ManyToOne
    private Empleado empleadoResponsable;


    /**Constructor (Vacío)*/
    public CambioEstado() {

    }

    /**Constructor */
    public CambioEstado( LocalDateTime fechaInicio, EventoSismico eventoSismico, Estado estado, Empleado empleadoResponsable) { 
        this.fechaInicio = fechaInicio; 
        this.eventoSismico = eventoSismico; 
        this.estado = estado; 
        this.empleadoResponsable = empleadoResponsable; 
    }

    //////////////// Getters y Setters

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
        this.fechaFin = LocalDateTime.now(); //Obtiene la fecha actual y la usa como fechaFin del cambioEstado
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
        //Valida si el atributo fechaFin del cambioEstado es null, lo que significa que aún no terminó
        return this.fechaFin == null; 
    }
}