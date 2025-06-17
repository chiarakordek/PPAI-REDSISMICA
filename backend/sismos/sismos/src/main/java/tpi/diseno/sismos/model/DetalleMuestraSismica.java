@Entity
public class DetalleMuestraSismica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double valor;

/** Muestra sísmica a la que pertenece este detalle. */
    @ManyToOne
    private MuestraSismica muestraSismica;

 /** Tipo de dato al que corresponde el valor registrado. */
    @ManyToOne
    private TipoDeDato tipoDeDato;
}
