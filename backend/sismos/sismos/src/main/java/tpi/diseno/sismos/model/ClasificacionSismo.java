package tpi.diseno.sismos.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class ClasificacionSismo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double kmProfundidadDesde;
    private Double kmProfundidadHasta;
    private String nombre;

    @OneToMany(mappedBy = "clasificacionSismo", cascade = CascadeType.ALL)
    private List<EventoSismico> eventosSismicos;

/////CONSTRUCTORES
    public ClasificacionSismo() {
    }

    public ClasificacionSismo(Double kmProfundidadDesde, Double kmProfundidadHasta, String nombre) {
        this.kmProfundidadDesde = kmProfundidadDesde;
        this.kmProfundidadHasta = kmProfundidadHasta;
        this.nombre = nombre;
    }

///MÉTODOS

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public Double getKmProfundidadDesde() {
        return kmProfundidadDesde;
    }
    public void setKmProfundidadDesde(Double kmProfundidadDesde) {
        this.kmProfundidadDesde = kmProfundidadDesde;
    }

    public Double getKmProfundidadHasta() {
        return kmProfundidadHasta;
    }
    public void setKmProfundidadHasta(Double kmProfundidadHasta) {
        this.kmProfundidadHasta = kmProfundidadHasta;
    }

    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}


