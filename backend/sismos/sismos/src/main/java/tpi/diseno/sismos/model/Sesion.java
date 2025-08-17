package tpi.diseno.sismos.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Sesion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
    private String observaciones;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", referencedColumnName = "nombre")
    private Usuario usuario;

    /**
     * MSG 25: obtenerUsuarioLogueado()
     * Devuelve el usuario asociado a esta sesión.
     */
    public Empleado obtenerUsuarioLogueado() {
        return this.usuario.getEmpleado();
    }
}