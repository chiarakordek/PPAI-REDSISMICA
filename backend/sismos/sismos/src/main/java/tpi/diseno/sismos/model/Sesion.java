package tpi.diseno.sismos.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.ManyToOne;

import java.time.LocalDateTime;

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
    private Usuario usuarioLogueado;

    public Sesion() {
    }

    public Sesion(LocalDateTime fechaFin, LocalDateTime fechaInicio, String observaciones, Usuario usuarioLogueado) {
        this.fechaFin = fechaFin;
        this.fechaInicio = fechaInicio;
        this.observaciones = observaciones;
        this.usuarioLogueado = usuarioLogueado;
    }

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

    public Usuario getUsuarioLogueado() {
        return usuarioLogueado;
    }

    public void setUsuario(Usuario usuarioLogueado) {
        this.usuarioLogueado = usuarioLogueado;
    }

}
