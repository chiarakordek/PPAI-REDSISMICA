package tpi.diseno.sismos.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;
@Entity
public class MuestraSismica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime fechaHoraMuestra;

 /** Serie temporal a la que pertenece esta muestra. */
    @ManyToOne
    private SerieTemporal serieTemporal;

/** Lista de detalles de la muestra, que contienen los valores y tipos de datos. */
    @OneToMany(mappedBy = "muestraSismica", cascade = CascadeType.ALL)
    private List<DetalleMuestraSismica> detallesMuestra;

/**Constructor */
    public MuestraSismica() {
    }

    public MuestraSismica(LocalDateTime fechaHoraMuestra, SerieTemporal serieTemporal, List<DetalleMuestraSismica> detallesMuestra) {
        this.fechaHoraMuestra = fechaHoraMuestra;
        this.serieTemporal = serieTemporal;
        this.detallesMuestra = detallesMuestra;
    }

    /////////// Getters y Setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getFechaHoraMuestra() {
        return fechaHoraMuestra;
    }
    public void setFechaHoraMuestra(LocalDateTime fechaHoraMuestra) {
        this.fechaHoraMuestra = fechaHoraMuestra;
    }

    public SerieTemporal getSerieTemporal() {
        return serieTemporal;
    }
    public void setSerieTemporal(SerieTemporal serieTemporal) {
        this.serieTemporal = serieTemporal;
    }

    public List<DetalleMuestraSismica> getDetallesMuestra() {
        return detallesMuestra;
    }
    public void setDetallesMuestra(List<DetalleMuestraSismica> detallesMuestra) {
        this.detallesMuestra = detallesMuestra;
    }

//////////// metodos
/**
     * Devuelve todos los detalles registrados en esta muestra.
     */
    public MuestraSismica getDatosMuestra() {

        List<DetalleMuestraSismica> detalles = new ArrayList<>();
        for (DetalleMuestraSismica detalle : this.detallesMuestra) {
            detalles.add(detalle.getDatosDetalleMuestra());
        }
        return new MuestraSismica(this.fechaHoraMuestra, this.serieTemporal, detalles);
    }

    /**
     * Busca y devuelve un detalle específico según el tipo de dato 
     * Si no se encuentra, devuelve null.
     */
    public List<DetalleMuestraSismica> buscarDetalleMuestra() {
        List<DetalleMuestraSismica> detalles = new ArrayList<>();
        for (DetalleMuestraSismica detalle : detallesMuestra) {
            detalles.add(detalle.getDatosDetalleMuestra());
        }
        return detalles;
    }
}
