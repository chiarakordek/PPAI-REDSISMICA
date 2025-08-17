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

 //ATRIBUTOS
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "estado_id")
    private Estado estado;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "evento_sismico_id")
    private EventoSismico eventoSismico;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "empleado_responsable_id")
    private Empleado empleadoResponsable;

    /**
     * MSG 29: esUltimoCambioEstado() -> Este método es invocado por EventoSismico.
     * Es responsable de ejecutar la lógica de los mensajes 29 y 30 
     * tambien mas adelante lo reutilizamos con el MSJ 71.
     * Verifica si es el último estado y, si lo es, establece la fecha de fin.
     */
    public void esUltimoCambioEstado(LocalDateTime fecha) { // MSG 29 (verificación)
        if (this.fechaFin == null) {
            // MSG 30 (setea fecha de fin en caso de ser el último cambio)
            this.setFechaFin(fecha);
        }
    }

    /**
     * MSG 32: new() -> Este constructor es invocado por EventoSismico.
     * Representa la creación de una nueva instancia de CambioEstado, en este caso,
     * para el estado 'BloqueadoEnRevision' 
     * posterior con el MSJ 73: para el estado 'Rechazado'.
     */
    public CambioEstado(LocalDateTime fechaInicio, Estado estado, EventoSismico eventoSismico, Empleado empleadoResponsable) {
        this.fechaInicio = fechaInicio;
        this.estado = estado;
        this.eventoSismico = eventoSismico;
        this.empleadoResponsable = empleadoResponsable;
        this.fechaFin = null; // Un nuevo estado siempre se crea sin fecha de fin.
    }
}