package tpi.diseno.sismos.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "evento_sismico")
public class EventoSismico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime fechaHoraOcurrencia;
    private Double latitudEpicentro;
    private Double longitudEpicentro;
    private Double latitudHipocentro;
    private Double longitudHipocentro;

    @Column(name = "valor_magnitud")
    private Double magnitud;

/** Estado actual del evento sísmico */
    @ManyToOne(fetch = FetchType.EAGER) // FetchType.EAGER le dice a Hibernate: "Cuando cargues un EventoSismico, SIEMPRE carga su Estado asociado inmediatamente".
    @JoinColumn(name = "estado_actual_id", referencedColumnName = "id") 
    private Estado estadoActual;

/** Clasificación a la cual corresponde el evento sísmico */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "clasificacion_sismo_id")
    private ClasificacionSismo clasificacionSismo;

/** Alcance al cual corresponde el evento sísmico */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "alcance_sismo_id")
    private AlcanceSismo alcanceSismo;

/** Origen al cual corresponde el evento sísmico */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "origen_de_generacion_id")
    private OrigenDeGeneracion origenDeGeneracion;


    /////////// Getters y Setters
    
    public Long getId() { 
        return id; 
    }


    public void setId(Long id) { 
        this.id = id; 
    }


    public LocalDateTime getFechaHoraOcurrencia() { 
        return fechaHoraOcurrencia; 
    }


    public void setFechaHoraOcurrencia(LocalDateTime fechaHoraOcurrencia) { 
        this.fechaHoraOcurrencia = fechaHoraOcurrencia; 
    }


    public Double getLatitudEpicentro() { 
        return latitudEpicentro; 
    }


    public void setLatitudEpicentro(Double latitudEpicentro) { 
        this.latitudEpicentro = latitudEpicentro; 
    }


    public Double getLongitudEpicentro() { 
        return longitudEpicentro; 
    }


    public void setLongitudEpicentro(Double longitudEpicentro) { 
        this.longitudEpicentro = longitudEpicentro; 
    }


    public Double getLatitudHipocentro() { 
        return latitudHipocentro; 
    }


    public void setLatitudHipocentro(Double latitudHipocentro) { 
        this.latitudHipocentro = latitudHipocentro; 
    }


    public Double getLongitudHipocentro() { 
        return longitudHipocentro; 
    }


    public void setLongitudHipocentro(Double longitudHipocentro) { 
        this.longitudHipocentro = longitudHipocentro; 
    }


    public Double getMagnitud() { 
        return magnitud; 
    }


    public void setMagnitud(Double magnitud) { 
        this.magnitud = magnitud; 
    }


    public Estado getEstado() { 
        return estadoActual; 
    }


    public void setEstado(Estado estadoActual) { 
        this.estadoActual = estadoActual; 
    }


    public ClasificacionSismo getClasificacionSismo() { 
        return clasificacionSismo; 
    }


    public void setClasificacionSismo(ClasificacionSismo clasificacionSismo) { 
        this.clasificacionSismo = clasificacionSismo; 
    }


    public AlcanceSismo getAlcanceSismo() { 
        return alcanceSismo; 
    }


    public void setAlcanceSismo(AlcanceSismo alcanceSismo) { 
        this.alcanceSismo = alcanceSismo; 
    }


    public OrigenDeGeneracion getOrigenDeGeneracion() { 
        return origenDeGeneracion; 
    }


    public void setOrigenDeGeneracion(OrigenDeGeneracion origenDeGeneracion) { 
        this.origenDeGeneracion = origenDeGeneracion; 
    }

    //FALTAN LOS MÉTODOS DEL DIAGRAMA DE SECUENCIA
}