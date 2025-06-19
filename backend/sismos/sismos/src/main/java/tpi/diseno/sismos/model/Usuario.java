package tpi.diseno.sismos.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

@Entity
public class Usuario {

    @Id
    private String nombre;

/** Empleado asociado a este usuario. */
    @OneToOne
    private Empleado empleado;

/**Constructor */
    public Usuario() {
    }

    public Usuario(String nombre, Empleado empleado) {
        this.nombre = nombre;
        this.empleado = empleado;
    }

    /////////// Getters y Setters
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Empleado getEmpleado() {
        return empleado;
    }
    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }
}
