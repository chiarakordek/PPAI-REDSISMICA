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
//atributos
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private LocalDateTime fechaAdquisicion;
    private String identificadorSismografo;
    private String nroSerie;
//relaciones
    @OneToOne
    @JoinColumn(name = "estacion_sismologica_id")
    private EstacionSismologica estacionSismologica;

    @OneToMany(mappedBy = "sismografo") /////serie temporal
    private List<SerieTemporal> seriesTemporales;

//metodos
    public boolean sosMiSismografo(Sismografo sismografo) {
        return this.equals(sismografo);
    }

    /**
     *getDatosSismografo() -> Invocado por SerieTemporal.
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