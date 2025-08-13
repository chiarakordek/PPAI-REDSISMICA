package tpi.diseno.sismos.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class AlcanceSismo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String descripcion;
    private String nombre;

    /**
     * MSG 38: getAlcance() -> Invocado por EventoSismico.
     * Devuelve los datos relevantes del alcance, en este caso, su nombre.
     */
    public String getAlcance() {
        return this.getNombre();
    }
}