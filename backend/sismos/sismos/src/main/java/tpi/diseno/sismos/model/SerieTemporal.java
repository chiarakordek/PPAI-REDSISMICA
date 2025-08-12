package tpi.diseno.sismos.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class SerieTemporal {
   //ATRIBUTOS
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String condicionAlarma;
    private LocalDateTime fechaHoraInicioRegistroMuestra;
    private LocalDateTime fechaHoraRegistro;
    private Integer frecuenciaMuestreo;

    //Evento sísmico al que pertenece esta serie temporal. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "evento_sismico_id")
    private EventoSismico eventoSismico;

    //Sismógrafo que generó esta serie temporal.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sismografo_id")
    private Sismografo sismografo;

    // Lista de muestras sísmicas registradas en esta serie temporal.
    @OneToMany(mappedBy = "serieTemporal", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<MuestraSismica> muestrasSismicas;

    //CONSTRUCTOR
    public SerieTemporal() {
    }

    public SerieTemporal(String condicionAlarma,
                         LocalDateTime fechaHoraInicioRegistroMuestra,
                         LocalDateTime fechaHoraRegistro,
                         Integer frecuenciaMuestreo,
                         EventoSismico eventoSismico,
                         Sismografo sismografo) {
        this.condicionAlarma = condicionAlarma;
        this.fechaHoraInicioRegistroMuestra = fechaHoraInicioRegistroMuestra;
        this.fechaHoraRegistro = fechaHoraRegistro;
        this.frecuenciaMuestreo = frecuenciaMuestreo;
        this.eventoSismico = eventoSismico;
        this.sismografo = sismografo;
    }

    //METODOS
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

    public Integer getFrecuenciaMuestreo() {
        return frecuenciaMuestreo;
    }
    public void setFrecuenciaMuestreo(Integer frecuenciaMuestreo) {
        this.frecuenciaMuestreo = frecuenciaMuestreo;
    }

    public EventoSismico getEventoSismico() {
        return eventoSismico;
    }
    public void setEventoSismico(EventoSismico eventoSismico) {
        this.eventoSismico = eventoSismico;
    }

    public Sismografo getSismografo() {
        return sismografo;
    }
    public void setSismografo(Sismografo sismografo) {
        this.sismografo = sismografo;
    }

    public List<MuestraSismica> getMuestrasSismicas() {
        return muestrasSismicas;
    }
    public void setMuestrasSismicas(List<MuestraSismica> muestrasSismicas) {
        this.muestrasSismicas = muestrasSismicas;
    }

    //Devuelve los datos de la serie temporal y retorna la lista de muestras sismicas
    public List<MuestraSismica> getDatosSerieTemporal() {
        return this.muestrasSismicas;
    }

   //Instancia el metodo de buscar a su sismografo, pasandole la relacion como parámetro.
    public boolean sosMiSismografo(Sismografo sismografo) {
        return this.sismografo.equals(sismografo);
    }
}