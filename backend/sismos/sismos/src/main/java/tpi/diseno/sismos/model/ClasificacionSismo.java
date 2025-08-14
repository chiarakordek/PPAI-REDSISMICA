package tpi.diseno.sismos.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class ClasificacionSismo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private double kmProfundidadDesde;
    private double kmProfundidadHasta;
    private String nombre;

    /**
     * MSG 36: getClasificacion() -> Invocado por EventoSismico.
     * Devuelve los datos relevantes de la clasificación, en este caso, su nombre.
     */
    public String getClasificacion() {
        return this.getNombre();
    }
}