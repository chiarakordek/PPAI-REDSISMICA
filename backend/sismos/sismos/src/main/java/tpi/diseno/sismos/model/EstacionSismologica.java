@Entity
public class EstacionSismologica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String codigoEstacion;
    private String documentoCertificacionAdq;
    private LocalDateTime fechaSolicitudCertificacion; 
    private Double latitud;
    private Double longitud;
    private String nombre;
}
