package tpi.diseno.sismos.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tpi.diseno.sismos.dto.EventoSismicoResumenDTO;
import tpi.diseno.sismos.dto.SerieTemporalDTO;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class EventoSismico {

    // --- 1. ATRIBUTOS Y RELACIONES ---
    @Id // Habria q agregar id en el diagrama de clase, para evitar incosistencias. Si no esta fechaHoraFin de Primary key, nos da error
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime fechaHoraFin;
    private LocalDateTime fechaHoraOcurrencia;
    private double latitudEpicentro;
    private double latitudHipocentro;
    private double longitudHipocentro;
    private double longitudEpicentro;
    private double valorMagnitud;

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

    // --- 2. MÉTODOS PÚBLICOS (En orden secuencial según el diagrama) ---
    
    public boolean esPendienteDeRevision() { // MSG 5
        if (this.estadoActual != null) {
            return this.estadoActual.esPendienteDeRevision(); // MSG 6 -> Delega al estado.
        }
        return false;
    }

    public EventoSismicoResumenDTO getDatos() { // MSG 7
        return new EventoSismicoResumenDTO(
            this.getFechaHoraOcurrencia(), // MSG 9
            this.getLatitudEpicentro(), // MSG 10
            this.getLongitudEpicentro(), // MSG 11
            this.getLatitudHipocentro(), // MSG 12
            this.getLongitudHipocentro(), // MSG 13
            this.getValorMagnitud() // MSG 14
        );
    }
 

    public void revisar(Estado nuevoEstado, LocalDateTime fechaHoraActual, Empleado empleadoResponsable) { // MSG 27
        CambioEstado ultimoCambio = this.buscarUltimoCambioEstado(); // MSG 28
        if (ultimoCambio != null) {
            ultimoCambio.esUltimoCambioEstado(fechaHoraActual); // MSG 29 -- Delega a CambioEstado
        }
        CambioEstado nuevoCambioEstado = this.crearCambioEstado(fechaHoraActual, nuevoEstado, empleadoResponsable); // MSG 31
        this.historialCambioEstado.add(nuevoCambioEstado);
        this.setEstado(nuevoEstado); // MSG 33
    }

    public String getClasificacion() { // MSG 35
        if (this.clasificacion != null) { return this.clasificacion.getClasificacion(); } // MSG 36
        return "N/A";
    }

    public String getAlcance() { // MSG 37
        if (this.alcanceSismo != null) { return this.alcanceSismo.getAlcance(); } // MSG 38
        return "N/A";
    }

    public String getOrigen() { // MSG 39
        if (this.origenGeneracion != null) { return this.origenGeneracion.getOrigen(); } // MSG 40
        return "N/A";
    }

    public List<SerieTemporalDTO> obtenerSeriesTemporales() { // MSG 41
        List<SerieTemporalDTO> dtos = this.seriesTemporales.stream()
                .map(serie -> serie.getDatosSerieTemporal()) // MSG 42 - Delega a SerieTemporal
                .collect(Collectors.toList());
        this.clasificarSeriesTemporales(dtos); // MSG 52
        return dtos;
    }

    public void rechazar(Estado estadoRechazado, LocalDateTime fechaHoraActual, Empleado empleadoResponsable) { // MSG 70
        CambioEstado ultimoCambio = this.buscarUltimoCambioEstado();
        if (ultimoCambio != null) {
            ultimoCambio.esUltimoCambioEstado(fechaHoraActual); // MSG 71
        }
        CambioEstado nuevoCambioEstadoRechazado = this.crearCambioEstado(fechaHoraActual, estadoRechazado, empleadoResponsable); // MSG 72
        this.historialCambioEstado.add(nuevoCambioEstadoRechazado);
        this.setEstado(estadoRechazado); // MSG 74
    }

    public void setEstado(Estado nuevoEstado) { // MSG 33 y 74
        this.setEstadoActual(nuevoEstado);
    }

    // --- 3. MÉTODOS PRIVADOS ---
    private CambioEstado buscarUltimoCambioEstado() { // MSG 28
        if (this.historialCambioEstado == null || this.historialCambioEstado.isEmpty()) { return null; }
        return this.historialCambioEstado.get(this.historialCambioEstado.size() - 1);
    }
    
    private CambioEstado crearCambioEstado(LocalDateTime fecha, Estado estado, Empleado empleado) { // MSG 31 y 72
        return new CambioEstado(fecha, estado, this, empleado); // MSG 32 y 73
    }
    
    private void clasificarSeriesTemporales(List<SerieTemporalDTO> series) { // MSG 52
        System.out.println("Ejecutando MSG 52: ClasificarSeriesTemporales en EventoSismico...");
    }
}