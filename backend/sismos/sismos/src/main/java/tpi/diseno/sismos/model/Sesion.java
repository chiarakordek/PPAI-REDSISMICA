@Entity
public class Sesion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime fechaFin;
    private LocalDateTime fechaInicio;
    private String observaciones;

/** Empleado que inició la sesión. */
    @ManyToOne
    private Empleado empleado;
}
