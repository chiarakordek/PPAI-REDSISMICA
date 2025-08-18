package tpi.diseno.sismos.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tpi.diseno.sismos.dto.EventoSismicoResumenDTO;
import tpi.diseno.sismos.dto.EventoSismicoDetalleDTO;
import tpi.diseno.sismos.dto.SerieTemporalDTO;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    private Estado estadoActual;
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
 

    public void revisar(Estado nuevoEstado, LocalDateTime fechaHoraActual, Empleado empleadoResponsable) { 
        CambioEstado ultimoCambio = this.buscarUltimoCambioEstado(); 
        ultimoCambio.setFechaFin(fechaHoraActual);
        CambioEstado nuevoCambioEstado = this.crearCambioEstado(fechaHoraActual, nuevoEstado, empleadoResponsable); 
        this.historialCambioEstado.add(nuevoCambioEstado);
        this.setEstado(nuevoEstado); 
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

    public void rechazar(Estado estadoRechazado, LocalDateTime fechaHoraActual, Empleado empleadoResponsable) { 
        CambioEstado ultimoCambio = this.buscarUltimoCambioEstado(); 
        ultimoCambio.setFechaFin(fechaHoraActual);
        CambioEstado nuevoCambioEstado = this.crearCambioEstado(fechaHoraActual, estadoRechazado , empleadoResponsable);
        this.historialCambioEstado.add(nuevoCambioEstado);
        this.setEstado(estadoRechazado); 
    }

    public void setEstado(Estado nuevoEstado) { 
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
    
    private CambioEstado crearCambioEstado(LocalDateTime fecha, Estado estado, Empleado empleado) { 
        return new CambioEstado(fecha, estado, this, empleado); 
    }
    
    private void clasificarSeriesTemporales(List<SerieTemporalDTO> series) { 
        System.out.println("Ejecutando MSG 52: ClasificarSeriesTemporales en EventoSismico...");
    }
}