package tpi.diseno.sismos.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tpi.diseno.sismos.dto.DetalleMuestraSismicaDTO;
import tpi.diseno.sismos.dto.MuestraSismicaDTO;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class MuestraSismica {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime fechaHoraMuestra;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "serie_temporal_id")
    private SerieTemporal serieTemporal;

    @OneToMany(mappedBy = "muestraSismica", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<DetalleMuestraSismica> detalleMuestraSismica = new ArrayList<>();

    public MuestraSismicaDTO getDatosMuestra() {  //prepara los datos para ser enviado en un dto
        LocalDateTime fechaHoraMuestra = this.getFechaHoraMuestra();
        List<DetalleMuestraSismicaDTO> detallesDTO = this.buscarDetalleMuestra(); 
        return new MuestraSismicaDTO(fechaHoraMuestra, detallesDTO);
    }

    private List<DetalleMuestraSismicaDTO> buscarDetalleMuestra() { 
        return this.detalleMuestraSismica.stream()
                .map(detalle -> detalle.getDatosDetalleMuestra()) // MSG 46
                .collect(Collectors.toList());
    }
}