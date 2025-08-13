package tpi.diseno.sismos.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tpi.diseno.sismos.dto.EstacionSismologicaDTO;
import tpi.diseno.sismos.dto.SismografoDTO;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Sismografo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private LocalDateTime fechaAdquisicion;
    private String identificadorSismografo;
    private String nroSerie;

    @OneToOne
    @JoinColumn(name = "estacion_sismologica_id")
    private EstacionSismologica estacionSismologica;

    @OneToMany(mappedBy = "sismografo")
    private List<SerieTemporal> seriesTemporales;

    /**
     * MSG 49: sosMiSismografo() -> Invocado por SerieTemporal.
     * En el diagrama, esto actúa como una condición. En una implementación real, podría
     * tomar un parámetro para una verificación. Siendo literales, es un método que se llama.
     * Suponemos que si el objeto existe, es el correcto.
     */
    public boolean sosMiSismografo() {
        return true; 
    }

    /**
     * MSG 50: getDatosSismografo() -> Invocado por SerieTemporal.
     * Orquesta la recolección de sus propios datos y los de su estación.
     */
    public SismografoDTO getDatosSismografo() {
        EstacionSismologicaDTO estacionDTO = null;
        if (this.estacionSismologica != null) {
            // MSG 51: getDatosEstacion() -> Delega la obtención de datos a EstacionSismologica.
            estacionDTO = this.estacionSismologica.getDatosEstacion();
        }
        return new SismografoDTO(this.getIdentificadorSismografo(), this.getNroSerie(), estacionDTO);
    }
}