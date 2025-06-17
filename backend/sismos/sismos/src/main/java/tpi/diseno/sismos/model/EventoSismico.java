@Entity
public class EventoSismico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime fechaHoraFin;
    private LocalDateTime fechaHoraOcurrencia;
    private Double latitudEpicentro;
    private Double latitudHipocentro;
    private Double longitudHipocentro;
    private Double longitudEpicentro;
    private Double valorMagnitud;

/** Lista de series temporales asociadas al evento. */
    @OneToMany(mappedBy = "eventoSismico", cascade = CascadeType.ALL)
    private List<SerieTemporal> seriesTemporales;

/** Cambios de estado que ha atravesado este evento sísmico. */
    @OneToMany(mappedBy = "eventoSismico", cascade = CascadeType.ALL)
    private List<CambioEstado> cambiosEstado;
}
