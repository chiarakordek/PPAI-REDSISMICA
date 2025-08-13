package tpi.diseno.sismos.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tpi.diseno.sismos.dto.MuestraSismicaDTO;
import tpi.diseno.sismos.dto.SerieTemporalDTO;
import tpi.diseno.sismos.dto.SismografoDTO;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class SerieTemporal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private boolean condicionAlarma;
    private LocalDateTime fechaHoraInicioRegistroMuestra;
    private LocalDateTime fechaHoraRegistro;
    private double frecuenciaMuestreo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "evento_sismico_id")
    private EventoSismico eventoSismico;

    @OneToMany(mappedBy = "serieTemporal", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<MuestraSismica> muestraSismica = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sismografo_id")
    private Sismografo sismografo;

    /**
     * MSG 42: getDatosSerieTemporal() -> Invocado por EventoSismico.
     * Este método orquesta la recolección de los datos de las muestras y del sismógrafo asociado.
     */
    public SerieTemporalDTO getDatosSerieTemporal() {
        
        // MSG 43: Se invoca el auto-mensaje para buscar los datos de las muestras.
        List<MuestraSismicaDTO> muestrasDTO = this.buscarMuestraSismica();
        
        SismografoDTO sismografoDTO = null;

        // El loop "Sismografo [mientras haya sismografo]" se traduce en verificar si existe.
        if (this.sismografo != null) {
            // MSG 49: sosMiSismografo() -> Se invoca la condición.
            if (this.sismografo.sosMiSismografo()) {
                // MSG 50: getDatosSismografo() -> Si la condición es verdadera, se piden los datos.
                sismografoDTO = this.sismografo.getDatosSismografo();
            }
        }
        
        // Al final, se empaquetan todos los datos recolectados en el DTO de la SerieTemporal.
        return new SerieTemporalDTO(muestrasDTO, sismografoDTO);
    }

    /**
     * MSG 43 (Implementación): buscarMuestraSismica()
     * Este método implementa el bucle anidado para recolectar datos de todas las MuestraSismica.
     */
    private List<MuestraSismicaDTO> buscarMuestraSismica() {
        // Inicia el loop [mientras haya muestras sismicas]
        return this.muestraSismica.stream()
                .map(muestra -> muestra.getDatosMuestra()) // MSG 44
                .collect(Collectors.toList());
    }
}