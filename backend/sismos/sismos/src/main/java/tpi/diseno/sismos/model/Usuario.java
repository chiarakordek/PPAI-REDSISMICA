package tpi.diseno.sismos.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Usuario {
    @Id
    private String nombre;

    @OneToOne
    @JoinColumn(name = "empleado_id", referencedColumnName = "id")
    private Empleado empleado;

    /**
     * MSG 26: getEmpleado()
     * Devuelve el Empleado asociado a este usuario. Lombok genera este método.
     */
    public Empleado getEmpleado() {
        return this.empleado;
    }
}