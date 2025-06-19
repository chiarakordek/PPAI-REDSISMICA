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
    public Usuario(String nombre, Empleado empleado) {
        this.nombre = nombre;
        this.empleado = empleado;
    }
    public Usuario() {
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getNombre() {
        return this.nombre;
    }
    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }
    public Empleado getEmpleado() {
        return this.empleado;
    }
}
