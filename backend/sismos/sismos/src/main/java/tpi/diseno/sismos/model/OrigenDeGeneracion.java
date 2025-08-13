package tpi.diseno.sismos.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class OrigenDeGeneracion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String descripcion;
    private String nombre;

    /**
     * MSG 40: getOrigen() -> Invocado por EventoSismico.
     * Devuelve los datos relevantes del origen, en este caso, su nombre.
     */
    public String getOrigen() {
        return this.getNombre();
    }
}