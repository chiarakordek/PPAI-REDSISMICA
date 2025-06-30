package tpi.diseno.sismos.model;

import java.time.LocalDateTime;
import java.util.List;

public class DatosSerieTemporal {

    private String condicionAlarma;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaRegistro;
    private Integer frecuencia;
    private String nombreEstacion;
    private String identificadorSismografo;
    private List<DatosMuestraSismica> datosMuestras;

    public DatosSerieTemporal(String condicionAlarma,
                              LocalDateTime fechaInicio,
                              LocalDateTime fechaRegistro,
                              Integer frecuencia,
                              String nombreEstacion,
                              String identificadorSismografo,
                              List<DatosMuestraSismica> datosMuestras) {
        this.condicionAlarma = condicionAlarma;
        this.fechaInicio = fechaInicio;
        this.fechaRegistro = fechaRegistro;
        this.frecuencia = frecuencia;
        this.nombreEstacion = nombreEstacion;
        this.identificadorSismografo = identificadorSismografo;
        this.datosMuestras = datosMuestras;
    }

    // Getters
    public String getCondicionAlarma() {
        return condicionAlarma;
    }

    public LocalDateTime getFechaInicio() {
        return fechaInicio;
    }

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }

    public Integer getFrecuencia() {
        return frecuencia;
    }

    public String getNombreEstacion() {
        return nombreEstacion;
    }

    public String getIdentificadorSismografo() {
        return identificadorSismografo;
    }

    public List<DatosMuestraSismica> getDatosMuestras() {
        return datosMuestras;
    }
}
