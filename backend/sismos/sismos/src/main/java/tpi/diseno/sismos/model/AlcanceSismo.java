package tpi.diseno.sismos.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.List;


@Entity
public class AlcanceSismo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String descripcion;

    @OneToMany(mappedBy = "alcanceSismo")
    private List<EventoSismico> eventosSismicos;

    /**Constructor (Vacío)*/
    public AlcanceSismo() {
    }

    /**Constructor */
    public AlcanceSismo(String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
    }


    //////////////// Getters y Setters

    public Long getId() {
        return id;
    }


    public void setId(Long id) {
        this.id = id;
    }


    public String getNombre() {
        return nombre;
    }


    public void setNombre(String nombre) {
        this.nombre = nombre;
    }


    public String getDescripcion() {
        return descripcion;
    }

    
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
