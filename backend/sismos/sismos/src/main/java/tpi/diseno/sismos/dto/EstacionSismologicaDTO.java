package tpi.diseno.sismos.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class EstacionSismologicaDTO {
    private String nombre;
    private double latitud;
    private double longitud;
}