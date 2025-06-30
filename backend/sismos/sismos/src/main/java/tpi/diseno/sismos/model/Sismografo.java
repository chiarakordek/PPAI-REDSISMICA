package tpi.diseno.sismos.model;

import java.time.LocalDate;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.FetchType;
// import java.util.List;  // Comentado ya que no se usa la lista en JPA

@Entity
public class Sismografo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate fechaAdquisicion;
    private String identificadorSismografo;
    private Integer nroSerie;

    /* 
     * Relación comentada porque no existe en el diagrama de clases.
     * Se maneja la lógica en el GestorRegistrarResultadoRevisionManual.
     */
    // @OneToMany(mappedBy = "sismografo")  
    // private List<SerieTemporal> seriesTemporales;

    /** Estación sismológica donde está instalado el sismógrafo. */
    @ManyToOne(fetch = FetchType.EAGER)
    private EstacionSismologica estacionSismologica;

    /** Constructor */
    public Sismografo() {
    }

    public Sismografo(LocalDate fechaAdquisicion, String identificadorSismografo, 
                     Integer nroSerie, EstacionSismologica estacionSismologica) {
        this.fechaAdquisicion = fechaAdquisicion;
        this.identificadorSismografo = identificadorSismografo;
        this.nroSerie = nroSerie;
        this.estacionSismologica = estacionSismologica;
    }

    ///////////// Getters y Setters /////////////
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFechaAdquisicion() {
        return fechaAdquisicion;
    }
    public void setFechaAdquisicion(LocalDate fechaAdquisicion) {
        this.fechaAdquisicion = fechaAdquisicion;
    }

    /*
     * Getters/Setters de seriesTemporales comentados porque la relación no existe.
     * La lógica se maneja en el gestor mediante sismografoRepository.
     */
    // public List<SerieTemporal> getSeriesTemporales() {
    //     return seriesTemporales;
    // }
    // public void setSeriesTemporales(List<SerieTemporal> seriesTemporales) {
    //     this.seriesTemporales = seriesTemporales;
    // }

    public String getIdentificadorSismografo() {
        return identificadorSismografo;
    }
    public void setIdentificadorSismografo(String identificadorSismografo) {
        this.identificadorSismografo = identificadorSismografo;
    }

    public Integer getNroSerie() {
        return nroSerie;
    }
    public void setNroSerie(Integer nroSerie) {
        this.nroSerie = nroSerie;
    }

    public EstacionSismologica getEstacionSismologica() {
        return estacionSismologica;
    }
    public void setEstacionSismologica(EstacionSismologica estacionSismologica) {
        this.estacionSismologica = estacionSismologica;
    }

    // Métodos /////////////////////////////////
    /*
     * Método comentado porque la verificación de relación ahora se hace en el gestor.
     * Si necesitas mantener esta función, implementala en el GestorRegistrarResultadoRevisionManual.
     */
    // public boolean sosMiSismografo(Long serieTemporalId) {
    //     if (this.seriesTemporales != null) {
    //         for(SerieTemporal serieTemporal : this.seriesTemporales) {
    //             if (serieTemporal.getId().equals(serieTemporalId)) {
    //                 return true;
    //             }
    //         }
    //     }
    //     return false;
    // }

    /** Devuelve el nombre de la estación asociada */
    public String getDatosSismografo() {
        return this.estacionSismologica != null ? 
            this.estacionSismologica.getDatosEstacion() : 
            "Estación no asignada";
    }
}