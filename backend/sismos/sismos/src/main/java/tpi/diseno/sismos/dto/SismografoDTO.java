package tpi.diseno.sismos.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SismografoDTO {
    private String identificadorSismografo;
    private String nroSerie;
    private EstacionSismologicaDTO estacion;
}