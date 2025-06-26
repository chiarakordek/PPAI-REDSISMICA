package tpi.diseno.sismos.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.CascadeType;
import tpi.diseno.sismos.repository.SismografoRepository;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class SerieTemporal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String condicionAlarma;
    private LocalDateTime fechaHoraInicioRegistroMuestra;
    private LocalDateTime fechaHoraRegistro;
    private Integer frecuenciaMuestreo;

/** Evento sísmico al que pertenece esta serie temporal. */
    @ManyToOne
    private EventoSismico eventoSismico;

/** Lista de muestras sísmicas registradas en esta serie temporal. */
    @OneToMany(mappedBy = "serieTemporal", cascade = CascadeType.ALL)
    private List<MuestraSismica> muestrasSismicas;

    private SismografoRepository sismografos;

/**Constructor */
    public SerieTemporal() {
    }

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

    ///////////// Getters y Setters
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

    public List<MuestraSismica> getMuestrasSismicas() {
        return muestrasSismicas;
    }
    public void setMuestrasSismicas(List<MuestraSismica> muestrasSismicas) {
        this.muestrasSismicas = muestrasSismicas;
    }

/////////////metodos
    /** 
     * Devuelve todas las muestras sísmicas asociadas a esta serie temporal. 
     * (equivale a obtener los datos en orden cronológico).
     */
    //CREO QUE ACA TIENE QUE DEVOLVER EL SISMOGRAFO Y LA ESTACION SISMOLOGICA TAMBIEN ()
    public List<SerieTemporal> getDatosSerieTemporal() {
        List<MuestraSismica> muestraSismica = this.buscarMuestrasSismicas();
        List<SerieTemporal> seriesTemporales = new ArrayList<>();
        for (MuestraSismica muestra : muestraSismica) {
            seriesTemporales.add(new SerieTemporal(
                this.condicionAlarma,
                this.fechaHoraInicioRegistroMuestra,
                this.fechaHoraRegistro,
                this.frecuenciaMuestreo,
                this.eventoSismico
            ));
        for (Sismografo sismografo : sismografos.findAll()) {
            if(sismografo.sosMiSismografo(this.id)){
                sismografo.getDatosSismografo();
            }
            }
        }
        return seriesTemporales;
        //return sismografo;
    }

    /** 
     * Devuelve todas las muestras para búsquedas o visualización. 
     */
    //ESTO DEBERIA LLAMAR AL METODO GETDATOSMUESTRA DE MUESTRAS SISMICAS
    public List<MuestraSismica> buscarMuestrasSismicas() {
        List<MuestraSismica> muestras = new ArrayList<>();
        for(MuestraSismica muestra : this.muestrasSismicas) {
            muestras.add(muestra.getDatosMuestra());
        }
        return muestras;
    }

    //EL  METODO getDatosSerieTemporal y buscarMuestrasSismicas devuelven hacen lo mismo
}
