package tpi.diseno.sismos.model;

import java.time.LocalDateTime;
import java.util.List;

public class DatosSerieTemporal {

    private String condicionAlarma;
    private LocalDateTime fechaHoraInicioRegistroMuestra;
    private LocalDateTime fechaHoraRegistro;
    private Integer frecuenciaMuestreo;
    private List<DatosMuestraSismica> datosMuestras;

    public DatosSerieTemporal(String condicionAlarma,
                              LocalDateTime fechaHoraInicioRegistroMuestra,
                              LocalDateTime fechaHoraRegistro,
                              Integer frecuenciaMuestreo,
                              List<DatosMuestraSismica> datosMuestras) {
        this.condicionAlarma = condicionAlarma;
        this.fechaHoraInicioRegistroMuestra = fechaHoraInicioRegistroMuestra;
        this.fechaHoraRegistro = fechaHoraRegistro;
        this.frecuenciaMuestreo = frecuenciaMuestreo;
        this.datosMuestras = datosMuestras;
    }

    // Getters
    public String getCondicionAlarma() {
        return condicionAlarma;
    }

    public LocalDateTime getFechaHoraInicioRegistroMuestra() {
        return fechaHoraInicioRegistroMuestra;
    }

    public LocalDateTime getFechaHoraRegistro() {
        return fechaHoraRegistro;
    }

    public Integer getFrecuenciaMuestreo() {
        return frecuenciaMuestreo;
    }

    public List<DatosMuestraSismica> getDatosMuestras() {
        return datosMuestras;
    }
}
