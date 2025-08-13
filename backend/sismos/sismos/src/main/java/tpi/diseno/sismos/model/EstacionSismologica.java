package tpi.diseno.sismos.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tpi.diseno.sismos.dto.EstacionSismologicaDTO;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class EstacionSismologica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String codigoEstacion;
    private String documentoCertificacionAdq;
    private LocalDate fechaSolicitudCertificacion;
    private double latitud;
    private double longitud;
    private String nombre;

    /**
     * MSG 51: getDatosEstacion() -> Invocado por Sismografo.
     * Empaqueta los datos relevantes de la estación en un DTO.
     */
    public EstacionSismologicaDTO getDatosEstacion() {
        return new EstacionSismologicaDTO(this.getNombre(), this.getLatitud(), this.getLongitud());
    }
}