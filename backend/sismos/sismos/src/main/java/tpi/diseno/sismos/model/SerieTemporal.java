package tpi.diseno.sismos.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.CascadeType;

import java.time.LocalDateTime;
import java.util.List;

@Entity
public class SerieTemporal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String condicionAlarma;
    private LocalDateTime fechaHoraInicioRegistroMuestra;
    private LocalDateTime fechaHoraRegistro;
    private LocalDateTime frecuenciaMuestreo;

/** Evento sísmico al que pertenece esta serie temporal. */
    @ManyToOne
    private EventoSismico eventoSismico;

/** Lista de muestras sísmicas registradas en esta serie temporal. */
    @OneToMany(mappedBy = "serieTemporal", cascade = CascadeType.ALL)
    private List<MuestraSismica> muestrasSismicas;
}
