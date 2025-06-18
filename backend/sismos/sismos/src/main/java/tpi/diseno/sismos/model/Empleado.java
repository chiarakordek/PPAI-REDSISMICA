package tpi.diseno.sismos.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Empleado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer cantidadHectareas;
    private String nombre;
    private String apellido;
    private String telefono;
    private String mail;

    public Empleado(Integer cantidadHectareas, String nombre, String apellido, String telefono, String mail) {
        this.cantidadHectareas = cantidadHectareas;
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
        this.mail = mail;
    }
    public Empleado() {
    }
    // SETS y GETS
    public Integer getCantidadHectareas() {
        return this.cantidadHectareas;
    }
    public void setCantidadHectareas(Integer cantidadHectareas) {
        this.cantidadHectareas = cantidadHectareas;
    }
    public Long getId() {
        return this.id;
    }
    public String getNombre() {
        return this.nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getApellido() {
        return this.apellido;
    }
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }
    public String getTelefono() {
        return this.telefono;
    }
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    public String getMail() {
        return this.mail;
    }
    public void setMail(String mail) {
        this.mail = mail;
    }

}
