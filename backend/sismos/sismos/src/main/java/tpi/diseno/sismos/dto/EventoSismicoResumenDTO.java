package tpi.diseno.sismos.dto;

import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;



@Getter
@Setter
@NoArgsConstructor 

public class EventoSismicoResumenDTO {
    private Long id;
    private LocalDateTime fechaHoraOcurrencia;
    private Double latitudEpicentro;
    private Double longitudEpicentro;
    private Double latitudHipocentro;
    private Double longitudHipocentro;
    private Double valorMagnitud;
    
    // Constructor manual con todos los argumentos
    public EventoSismicoResumenDTO(Long id, LocalDateTime fechaHoraOcurrencia, 
                                 Double latitudEpicentro, Double longitudEpicentro, 
                                 Double latitudHipocentro, Double longitudHipocentro, 
                                 Double valorMagnitud) {
        this.id = id;
        this.fechaHoraOcurrencia = fechaHoraOcurrencia;
        this.latitudEpicentro = latitudEpicentro;
        this.longitudEpicentro = longitudEpicentro;
        this.latitudHipocentro = latitudHipocentro;
        this.longitudHipocentro = longitudHipocentro;
        this.valorMagnitud = valorMagnitud;
    }
}