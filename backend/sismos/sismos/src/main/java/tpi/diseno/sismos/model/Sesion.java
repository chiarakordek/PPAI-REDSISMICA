package tpi.diseno.sismos.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Sesion {
    //atributos
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
    private String observaciones;
//relaciones con otras clases
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", referencedColumnName = "nombre")
    private Usuario usuario;

    /**
     *  obtenerUsuarioLogueado()
     * Devuelve el usuario asociado a esta sesión.
     */
    public Empleado obtenerUsuarioLogueado() {
        return this.usuario.getEmpleado();
    }
}