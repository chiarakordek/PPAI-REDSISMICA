package tpi.diseno.sismos.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
public class MuestraSismicaDTO {
    private LocalDateTime fechaHoraMuestra;
    private List<DetalleMuestraSismicaDTO> detalles;
}