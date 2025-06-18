package tpi.diseno.sismos.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;

@Entity
public class CambioEstado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String denominacion;
    private String nombreUnidadMedida;
    private Double valorUmbral;
    private LocalDateTime fechaCambioEstado;

 /** Evento sísmico al que pertenece este cambio de estado. */
    @ManyToOne
    private EventoSismico eventoSismico;

/** Estado que se asigna al evento sísmico tras este cambio. */
    @ManyToOne
    private Estado estado;
}
