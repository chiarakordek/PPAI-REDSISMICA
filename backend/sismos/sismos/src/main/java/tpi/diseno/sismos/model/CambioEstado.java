package tpi.diseno.sismos.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class CambioEstado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "estado_id")
    private EstadoDatos estado;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "evento_sismico_id")
    private EventoSismico eventoSismico;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "empleado_responsable_id")
    private Empleado empleadoResponsable;

    /**
     * MSG 29: esUltimoCambioEstado() -> Este método es invocado por EventoSismico.
     * Es responsable de verificar si el cambio de estado es el ultimo
     * un cambio de estado se considera el último si aún no tiene fecha de fin.
     */
    public boolean esUltimoCambioEstado() { //  (verificación)
        return this.fechaFin == null; //true si fechaFin es null
    }


    /**
     * MSG 32: new() -> Este constructor es invocado por EventoSismico.
     * Representa la creación de una nueva instancia de CambioEstado, en este caso,
     * para el estado 'BloqueadoEnRevision' 
     * posterior para el estado 'Rechazado'.
     */
    public CambioEstado(LocalDateTime fechaInicio, EstadoDatos estado, EventoSismico eventoSismico, Empleado empleadoResponsable) {
        this.fechaInicio = fechaInicio;  //fecha en que comienza el estado
        this.estado = estado; //nuevo estado a registrar
        this.eventoSismico = eventoSismico; //evento al que pertenece el cambio estado
        this.empleadoResponsable = empleadoResponsable; //empleado que lo instancia.
        this.fechaFin = null; // Un nuevo estado siempre se crea sin fecha de fin.
    }
}