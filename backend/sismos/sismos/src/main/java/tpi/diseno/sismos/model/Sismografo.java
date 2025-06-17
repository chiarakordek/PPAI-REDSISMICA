@Entity
public class Sismografo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate fechaAdquisicion;
    private String identificadorSismografo;
    private Integer nroSerie;

/** Estación sismológica donde está instalado el sismógrafo. */
    @ManyToOne
    private EstacionSismologica estacionSismologica;
}
