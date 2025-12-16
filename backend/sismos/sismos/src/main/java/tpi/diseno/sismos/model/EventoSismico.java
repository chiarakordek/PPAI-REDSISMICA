package tpi.diseno.sismos.model;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tpi.diseno.sismos.dto.EventoSismicoResumenDTO;
import tpi.diseno.sismos.dto.SerieTemporalDTO;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import tpi.diseno.sismos.model.Estado;
import tpi.diseno.sismos.model.state.EventoEstadoFactory;
import tpi.diseno.sismos.repository.EstadoRepository;

@Entity
@Getter
@Setter
public class EventoSismico {
//atributos
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime fechaHoraFin;
    private LocalDateTime fechaHoraOcurrencia;
    private double latitudEpicentro;
    private double latitudHipocentro;
    private double longitudHipocentro;
    private double longitudEpicentro;
    private Double ValorMagnitud;

//relaciones con otras entidades
    @OneToMany(mappedBy = "eventoSismico", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<SerieTemporal> seriesTemporales = new ArrayList<>();
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "estado_actual_id")
    private EstadoDatos estadoActual;
    @OneToMany(mappedBy = "eventoSismico", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("fechaInicio ASC")
    private List<CambioEstado> historialCambioEstado = new ArrayList<>();
    @ManyToOne(fetch = FetchType.LAZY)
    private ClasificacionSismo clasificacion;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="origen_generacion_id")
    private OrigenDeGeneracion origenGeneracion;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="alcance_sismo_id")
    private AlcanceSismo alcanceSismo;

    @Transient
    private Estado estadoState;

    // --- 2. MÉTODOS PÚBLICOS  ---
    
    public boolean esAutoDetectado() { 
        if (this.estadoActual != null) {
            return this.estadoActual.esAutoDetectado(); 
        }
        return false;
    }

    public EventoSismicoResumenDTO getDatos() { 
        return new EventoSismicoResumenDTO(
            this.getId(), 
            this.getFechaHoraOcurrencia(), 
            this.getLatitudEpicentro(), 
            this.getLongitudEpicentro(), 
            this.getLatitudHipocentro(), 
            this.getLongitudHipocentro(), 
            this.getValorMagnitud() 
        );
    }
 

    private void ensureState() {
        if (this.estadoActual == null || this.estadoActual.getNombreEstado() == null) {
            throw new IllegalStateException("El evento sísmico no tiene estadoActual definido");
        }
        this.estadoState = EventoEstadoFactory.fromNombre(this.estadoActual.getNombreEstado());
    }

    public void revisar(LocalDateTime fechaHoraActual, Empleado empleadoResponsable, EstadoRepository estadoRepository) { 
        ensureState();
        this.estadoState.revisar(fechaHoraActual, this, empleadoResponsable, estadoRepository);
    }

    public String getClasificacion() { 
        if (this.clasificacion != null) { return this.clasificacion.getNombre(); } 
        return "N/A";
    }

    public String getAlcance() { 
        if (this.alcanceSismo != null) { return this.alcanceSismo.getNombre(); } 
        return "N/A";
    }

    public String getOrigen() { 
        if (this.origenGeneracion != null) { return this.origenGeneracion.getNombre(); } 
        return "N/A";
    }

    public List<SerieTemporalDTO> obtenerSeriesTemporales() { 
        List<SerieTemporalDTO> dtos = this.seriesTemporales.stream()
                .map(serie -> serie.getDatosSerieTemporal()) // Delega a SerieTemporal
                .collect(Collectors.toList());
        this.clasificarSeriesTemporales(dtos); 
        return dtos;
    }

    public void rechazar(LocalDateTime fechaHoraActual, Empleado empleadoResponsable, EstadoRepository estadoRepository) { 
        ensureState();
        this.estadoState.rechazar(fechaHoraActual, this, empleadoResponsable, estadoRepository);
    }

    public void setEstado(EstadoDatos nuevoEstado) { 
        this.setEstadoActual(nuevoEstado);
    }

    // --- 3. MÉTODOS PRIVADOS ---
    private CambioEstado buscarUltimoCambioEstado() { 
                List<CambioEstado> todosLosCambios = this.historialCambioEstado;
        CambioEstado ultimoCambioEstado = null;

        // Inicia el loop [mientras exista estados]
        for (CambioEstado cambioEstado : todosLosCambios) {
        
            if (cambioEstado.esUltimoCambioEstado()) {
                ultimoCambioEstado = cambioEstado;
                break;
            }
        }
        return ultimoCambioEstado;
    }
    
    private CambioEstado crearCambioEstado(LocalDateTime fecha, EstadoDatos estado, Empleado empleado) { 
        return new CambioEstado(fecha, estado, this, empleado); 
    }
    
    private void clasificarSeriesTemporales(List<SerieTemporalDTO> series) { 
        System.out.println("Ejecutando MSG 52: ClasificarSeriesTemporales en EventoSismico...");
    }
}