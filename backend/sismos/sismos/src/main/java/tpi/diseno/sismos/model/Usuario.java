@Entity
public class Usuario {

    @Id
    private String nombre;

/** Empleado asociado a este usuario. */
    @OneToOne
    private Empleado empleado;
}
