package tpi.diseno.sismos.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;

import tpi.diseno.sismos.model.DatosMuestraSismica;
import tpi.diseno.sismos.model.DatosSerieTemporal;

@Entity
public class SerieTemporal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String condicionAlarma;
    private LocalDateTime fechaHoraInicioRegistroMuestra;
    private LocalDateTime fechaHoraRegistro;
    private Integer frecuenciaMuestreo;

    @ManyToOne
    private EventoSismico eventoSismico;

    @OneToMany(mappedBy = "serieTemporal", cascade = CascadeType.ALL)
    private List<MuestraSismica> muestrasSismicas;

    /** Constructor vacío */
    public SerieTemporal() {}

    /** Constructor completo */
    public SerieTemporal(String condicionAlarma,
                         LocalDateTime fechaHoraInicioRegistroMuestra,
                         LocalDateTime fechaHoraRegistro,
                         Integer frecuenciaMuestreo,
                         EventoSismico eventoSismico) {
        this.condicionAlarma = condicionAlarma;
        this.fechaHoraInicioRegistroMuestra = fechaHoraInicioRegistroMuestra;
        this.fechaHoraRegistro = fechaHoraRegistro;
        this.frecuenciaMuestreo = frecuenciaMuestreo;
        this.eventoSismico = eventoSismico;
    }

    // Getters y setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCondicionAlarma() { return condicionAlarma; }
    public void setCondicionAlarma(String condicionAlarma) { this.condicionAlarma = condicionAlarma; }

    public LocalDateTime getFechaHoraInicioRegistroMuestra() { return fechaHoraInicioRegistroMuestra; }
    public void setFechaHoraInicioRegistroMuestra(LocalDateTime fechaHoraInicioRegistroMuestra) {
        this.fechaHoraInicioRegistroMuestra = fechaHoraInicioRegistroMuestra;
    }

    public LocalDateTime getFechaHoraRegistro() { return fechaHoraRegistro; }
    public void setFechaHoraRegistro(LocalDateTime fechaHoraRegistro) {
        this.fechaHoraRegistro = fechaHoraRegistro;
    }

    public Integer getFrecuenciaMuestreo() { return frecuenciaMuestreo; }
    public void setFrecuenciaMuestreo(Integer frecuenciaMuestreo) {
        this.frecuenciaMuestreo = frecuenciaMuestreo;
    }

    public EventoSismico getEventoSismico() { return eventoSismico; }
    public void setEventoSismico(EventoSismico eventoSismico) {
        this.eventoSismico = eventoSismico;
    }

    public List<MuestraSismica> getMuestrasSismicas() { return muestrasSismicas; }
    public void setMuestrasSismicas(List<MuestraSismica> muestrasSismicas) {
        this.muestrasSismicas = muestrasSismicas;
    }

    // Devuelve los datos agrupados de la serie temporal (DTO)
    public DatosSerieTemporal getDatosSerieTemporal(String nombreEstacion, String identificadorSismografo) {
        List<DatosMuestraSismica> datosMuestras = buscarMuestrasSismicas();

        return new DatosSerieTemporal(
            this.condicionAlarma,
            this.fechaHoraInicioRegistroMuestra,
            this.fechaHoraRegistro,
            this.frecuenciaMuestreo,
            nombreEstacion,
            identificadorSismografo,
            datosMuestras
        );
    }

    // Devuelve las muestras convertidas en DTOs
    public List<DatosMuestraSismica> buscarMuestrasSismicas() {
        List<DatosMuestraSismica> datos = new ArrayList<>();
        for (MuestraSismica muestra : this.muestrasSismicas) {
            datos.add(new DatosMuestraSismica(
                muestra.getFechaHoraMuestra(),
                muestra.getDetallesMuestra()
            ));
        }
        return datos;
    }
}
