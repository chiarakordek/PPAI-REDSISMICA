package tpi.diseno.sismos.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "origen_de_generacion") 
public class OrigenDeGeneracion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String descripcion;
    private String nombre;

    @OneToMany(mappedBy = "origenDeGeneracion")
    private List<EventoSismico> eventosSismicos;

    /**Constructor (Vacío)*/
    public OrigenDeGeneracion() {
    }

    /**Constructor*/
    public OrigenDeGeneracion(String descripcion, String nombre) {
        this.descripcion = descripcion;
        this.nombre = nombre;
    }

    //////////////// Getters y Setters

    public Long getId(){
        return id;
    }

    public void setId(Long id){
        this.id = id;
    }

    public String getDescripcion(){
        return descripcion;
    }

    public void setDescripcion(String descripcion){
        this.descripcion = descripcion;
    }

    public String getNombre(){
        return nombre;
    }

    public void setNombre(String nombre){
        this.nombre = nombre;
    }


    public List<EventoSismico> getEventosSismicos() {
        return eventosSismicos;
    }

    public void setEventosSismicos(List<EventoSismico> eventosSismicos) {
        this.eventosSismicos = eventosSismicos;
    }


    // ==========================================================
    // ===== MÉTODO NUEVO AÑADIDO (LA ÚNICA MODIFICACIÓN) =====
    // ==========================================================
    /**
     * Comprueba si el origen del evento es 'Automático' o 'Autodetectado'.
     * Este método encapsula la lógica de negocio, cumpliendo con el DDS.
     * @return true si el nombre coincide, ignorando mayúsculas/minúsculas.
     */
    public boolean esAutodetectado() {
        // Primero, se comprueba si el atributo 'nombre' es nulo para evitar errores.
        if (this.nombre == null) {
            return false;
        }
        // Devuelve true si el nombre es "Automático" O "Autodetectado".
        return "Automático".equalsIgnoreCase(this.nombre) || "Autodetectado".equalsIgnoreCase(this.nombre);
    }

}