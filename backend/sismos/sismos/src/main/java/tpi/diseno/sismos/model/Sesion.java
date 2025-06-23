package tpi.diseno.sismos.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Sesion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime fechaFin;
    private LocalDateTime fechaInicio;
    private String observaciones;

/** Usuario que inició la sesión. */
    @ManyToOne
    private Empleado empleado;
    private Usuario usuarioLogueado;

/**Constructor */
    public Sesion() {
    }

    public Sesion(LocalDateTime fechaInicio, LocalDateTime fechaFin, String observaciones, Empleado empleado) {
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.observaciones = observaciones;
        this.empleado = empleado;
    }


    /////////// Getters y Setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getFechaFin() {
        return fechaFin;
    }
    public void setFechaFin(LocalDateTime fechaFin) {
        this.fechaFin = fechaFin;
    }

    public LocalDateTime getFechaInicio() {
        return fechaInicio;
    }
    public void setFechaInicio(LocalDateTime fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getObservaciones() {
        return observaciones;
    }
    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    //set usuario y obtenerlo
    public void setUsuarioLogueado(Usuario usuario) {
        this.usuarioLogueado = usuario;
    }

    public Empleado obtenerUsuarioLogueado() {
        if (usuarioLogueado != null) {
            return usuarioLogueado.getEmpleado();
        } else {
            return null; 
        }
    }

}
