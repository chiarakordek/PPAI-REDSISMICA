package tpi.diseno.sismos.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class MuestraSismica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime fechaHoraMuestra;

    @ManyToOne
    private SerieTemporal serieTemporal;

    @OneToMany(mappedBy = "muestraSismica", cascade = CascadeType.ALL)
    private List<DetalleMuestraSismica> detallesMuestra;

    // Constructor vacío
    public MuestraSismica() {
    }

    // Constructor completo
    public MuestraSismica(LocalDateTime fechaHoraMuestra, SerieTemporal serieTemporal) {
        this.fechaHoraMuestra = fechaHoraMuestra;
        this.serieTemporal = serieTemporal;
    }

    // Getters y Setters
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

    // 
    public DatosMuestraSismica getDatosMuestra() {
        return new DatosMuestraSismica(
            this.fechaHoraMuestra,
            this.detallesMuestra
        );
    }

    // Método para buscar un detalle por tipo
    public DetalleMuestraSismica buscarDetalleMuestra(TipoDeDato tipo) {
        for (DetalleMuestraSismica detalle : detallesMuestra) {
            if (detalle.getTipoDeDato().equals(tipo)) {
                return detalle;
            }
        }
        return null;
    }
}
