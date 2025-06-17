@Entity
public class Empleado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer cantidadHectareas;
    private String nombre;
    private String apellido;
    private String telefono;
    private String mail;
}
