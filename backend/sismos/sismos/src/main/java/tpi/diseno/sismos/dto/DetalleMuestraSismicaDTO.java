package tpi.diseno.sismos.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DetalleMuestraSismicaDTO {
    private String tipoDato;
    private double valor;
}