package tpi.diseno.sismos.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import java.util.List;

@Getter
@AllArgsConstructor
public class SerieTemporalDTO {
    private List<MuestraSismicaDTO> muestras;
    // Se añade el DTO del sismógrafo para contener los datos de los mensajes 49-51.
    private SismografoDTO sismografo; 
}