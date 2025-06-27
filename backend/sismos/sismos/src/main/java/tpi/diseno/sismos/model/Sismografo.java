package tpi.diseno.sismos.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.FetchType;
import java.util.List;

@Entity
public class Sismografo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate fechaAdquisicion;
    private String identificadorSismografo;
    private Integer nroSerie;
    private List<SerieTemporal> seriesTemporales;

/** Estación sismológica donde está instalado el sismógrafo. */
    @ManyToOne(fetch = FetchType.EAGER)
    private EstacionSismologica estacionSismologica;


/**Constructor */
    public Sismografo() {
    }

    public Sismografo(LocalDate fechaAdquisicion, String identificadorSismografo, Integer nroSerie, EstacionSismologica estacionSismologica) {
        this.fechaAdquisicion = fechaAdquisicion;
        this.identificadorSismografo = identificadorSismografo;
        this.nroSerie = nroSerie;
        this.estacionSismologica = estacionSismologica;
    }

    ///////////// Getters y Setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFechaAdquisicion() {
        return fechaAdquisicion;
    }
    public void setSeriesTemporales(List<SerieTemporal> seriesTemporales) {
        this.seriesTemporales = seriesTemporales;
    }
    public List<SerieTemporal> getSeriesTemporales() {
        return seriesTemporales;
    }
    public void setFechaAdquisicion(LocalDate fechaAdquisicion) {
        this.fechaAdquisicion = fechaAdquisicion;
    }

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
    // Metodos
    public boolean sosMiSismografo(Long serieTemporalId) {
        for(SerieTemporal serieTemporal : this.getSeriesTemporales()) {
            if (serieTemporal.getId().equals(serieTemporalId)) {
                return true;
            }
        }
        return false;
    }

    public String getDatosSismografo() {
        return this.estacionSismologica.getDatosEstacion();
    }
}
