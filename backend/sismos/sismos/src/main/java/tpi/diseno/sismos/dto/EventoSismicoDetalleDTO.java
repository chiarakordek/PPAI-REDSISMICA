package tpi.diseno.sismos.dto;
/* 
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;



@Getter
@Setter
public class EventoSismicoResumenDTO {
    private LocalDateTime fechaHoraOcurrencia;
    private double latitudEpicentro;
    private double longitudEpicentro;
    private double latitudHipocentro;
    private double longitudHipocentro;
    private double valorMagnitud; 
}*/
public class EventoSismicoDetalleDTO {
    private String clasificacion;
    private String alcance;
    private String origen_evento;
    private String estado;

    public EventoSismicoDetalleDTO(String clasificacion, String alcance, String origen_evento, String estado) {
        this.clasificacion = clasificacion;
        this.alcance = alcance;
        this.origen_evento = origen_evento;
        this.estado = estado;
    }

    public String getClasificacion() {
        return clasificacion;
    }

    public void setClasificacion(String clasificacion) {
        this.clasificacion = clasificacion;
    }

    public String getAlcance() {
        return alcance;
    }

    public void setAlcance(String alcance) {
        this.alcance = alcance;
    }

    public String getOrigen_evento() {
        return origen_evento;
    }

    public void setOrigen_evento(String origen_evento) {
        this.origen_evento = origen_evento;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
