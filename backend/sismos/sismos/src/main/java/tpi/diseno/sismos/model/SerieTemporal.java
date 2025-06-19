package tpi.diseno.sismos.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.CascadeType;

import java.time.LocalDateTime;
import java.util.List;

@Entity
public class SerieTemporal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String condicionAlarma;
    private LocalDateTime fechaHoraInicioRegistroMuestra;
    private LocalDateTime fechaHoraRegistro;
    private LocalDateTime frecuenciaMuestreo;

/** Evento sísmico al que pertenece esta serie temporal. */
    @ManyToOne
    private EventoSismico eventoSismico;

/** Lista de muestras sísmicas registradas en esta serie temporal. */
    @OneToMany(mappedBy = "serieTemporal", cascade = CascadeType.ALL)
    private List<MuestraSismica> muestrasSismicas;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCondicionAlarma() {
        return condicionAlarma;
    }

    public void setCondicionAlarma(String condicionAlarma) {
        this.condicionAlarma = condicionAlarma;
    }

    public LocalDateTime getFechaHoraInicioRegistroMuestra() {
        return fechaHoraInicioRegistroMuestra;
    }

    public void setFechaHoraInicioRegistroMuestra(LocalDateTime fechaHoraInicioRegistroMuestra) {
        this.fechaHoraInicioRegistroMuestra = fechaHoraInicioRegistroMuestra;
    }

    public LocalDateTime getFechaHoraRegistro() {
        return fechaHoraRegistro;
    }

    public void setFechaHoraRegistro(LocalDateTime fechaHoraRegistro) {
        this.fechaHoraRegistro = fechaHoraRegistro;
    }

    public LocalDateTime getFrecuenciaMuestreo() {
        return frecuenciaMuestreo;
    }

    public void setFrecuenciaMuestreo(LocalDateTime frecuenciaMuestreo) {
        this.frecuenciaMuestreo = frecuenciaMuestreo;
    }

    public EventoSismico getEventoSismico() {
        return eventoSismico;
    }
}
