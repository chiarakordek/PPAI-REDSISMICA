package tpi.diseno.sismos.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tpi.diseno.sismos.dto.DetalleMuestraSismicaDTO;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class DetalleMuestraSismica {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double valor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "muestra_sismica_id")
    private MuestraSismica muestraSismica;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tipo_dato_id")
    private TipoDeDato tipoDato;

    public DetalleMuestraSismicaDTO getDatosDetalleMuestra() { // prepara los datos de este detalle para ser enviados (formato dto)
        String denominacionTipoDato = this.buscarTipoDeDato(); // llama al metodo para buscar el tipo de dato
        Double valor = this.getValor(); //obtiene el valor numerico
        return new DetalleMuestraSismicaDTO(denominacionTipoDato, valor); // crea y devuelve un objeto con el nombre y el valor
    }

    //Obtiene el nombre del tipo de dato para el valor del detalle
    private String buscarTipoDeDato() { // MSG 47
        return this.tipoDato.getDenominacion(); // MSG 48
    }
}