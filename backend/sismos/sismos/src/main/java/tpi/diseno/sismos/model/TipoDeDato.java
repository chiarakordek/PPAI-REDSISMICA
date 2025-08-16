package tpi.diseno.sismos.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class TipoDeDato {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String denominacion;
    private String nombreUnidadMedida;
    private double valorUmbral;

    // Aunque Lombok @Getter proporciona este método, lo escribimos explícitamente
    // para poder adjuntar el comentario obligatorio del diagrama de secuencia.
    /**
     * MSG 48: getDenominacion() -> Invocado por DetalleMuestraSismica.
     * Devuelve la denominación de este tipo de dato.
     */
    public String getDenominacion() {
        return this.denominacion;
    }

}