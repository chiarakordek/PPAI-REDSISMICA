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

    public DetalleMuestraSismicaDTO getDatosDetalleMuestra() { // MSG 46
        String denominacionTipoDato = this.buscarTipoDeDato(); // MSG 47
        Double valor = this.getValor();
        return new DetalleMuestraSismicaDTO(denominacionTipoDato, valor);
    }

    //Obtiene el tipo de dato para el valor del detalle
    private String buscarTipoDeDato() { // MSG 47
        //Si el tipo de dato es null, se informa que no se reconoce el tipo de dato.
        return this.tipoDato.getDenominacion(); // MSG 48
    }
}