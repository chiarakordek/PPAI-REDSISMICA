package tpi.diseno.sismos.model;

import java.time.LocalDateTime;
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
import lombok.Setter;
import tpi.diseno.sismos.dto.EventoSismicoResumenDTO;
import tpi.diseno.sismos.dto.SerieTemporalDTO;


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

    public EventoSismicoResumenDTO getDatos() { // MSG 7
    return new EventoSismicoResumenDTO(
        this.id,                    // Acceso directo al campo
        this.fechaHoraOcurrencia,   // Acceso directo al campo  
        this.latitudEpicentro,      // Acceso directo al campo
        this.longitudEpicentro,     // Acceso directo al campo
        this.latitudHipocentro,     // Acceso directo al campo
        this.longitudHipocentro,    // Acceso directo al campo
        this.ValorMagnitud          // Acceso directo al campo
    );
}
 

    public void revisar(Estado nuevoEstado, LocalDateTime fechaHoraActual, Empleado empleadoResponsable) { // MSG 27
        CambioEstado ultimoCambio = this.buscarUltimoCambioEstado(); // MSG 28
        if (ultimoCambio != null) {
            ultimoCambio.setFechaFin(fechaHoraActual);
        }
        CambioEstado nuevoCambioEstado = this.crearCambioEstado(fechaHoraActual, nuevoEstado, empleadoResponsable); // MSG 31
        this.historialCambioEstado.add(nuevoCambioEstado);
        this.setEstado(nuevoEstado); // MSG 33
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
    private CambioEstado buscarUltimoCambioEstado() { // MSG 28
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