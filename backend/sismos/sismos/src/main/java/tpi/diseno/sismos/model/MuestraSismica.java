@Entity
public class MuestraSismica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime fechaHoraMuestra;

 /** Serie temporal a la que pertenece esta muestra. */
    @ManyToOne
    private SerieTemporal serieTemporal;

/** Lista de detalles de la muestra, que contienen los valores y tipos de datos. */
    @OneToMany(mappedBy = "muestraSismica", cascade = CascadeType.ALL)
    private List<DetalleMuestraSismica> detallesMuestra;
}
