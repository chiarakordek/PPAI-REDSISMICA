package tpi.diseno.sismos.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Entity
public class EventoSismico {
    //ATRIBUTOS
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

    @OneToMany(mappedBy = "eventoSismico", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("evento-serie")
    private List<SerieTemporal> seriesTemporales = new ArrayList<>();

    @OneToMany(mappedBy = "eventoSismico", cascade = CascadeType.ALL)
    @JsonManagedReference("evento-cambioestado")
    private List<CambioEstado> cambiosEstado = new ArrayList<>();

    @ManyToOne(fetch = FetchType.EAGER)
    private Estado estadoActual;

    @ManyToOne(fetch = FetchType.EAGER)
    private AlcanceSismo alcanceSismo;

    @ManyToOne(fetch = FetchType.EAGER)
    private ClasificacionSismo clasificacionSismo;

    @ManyToOne(fetch = FetchType.EAGER)
    private OrigenDeGeneracion origenDeGeneracion;

    //CONSTRUCTORES
    public EventoSismico() {
    }

    public EventoSismico(LocalDateTime fechaHoraFin, LocalDateTime fechaHoraOcurrencia, Double latitudEpicentro, Double latitudHipocentro, Double longitudHipocentro, Double longitudEpicentro, Double valorMagnitud, Estado estado, AlcanceSismo alcanceSismo, ClasificacionSismo clasificacionSismo, OrigenDeGeneracion origenDeGeneracion) {
        this.fechaHoraFin = fechaHoraFin;
        this.fechaHoraOcurrencia = fechaHoraOcurrencia;
        this.latitudEpicentro = latitudEpicentro;
        this.latitudHipocentro = latitudHipocentro;
        this.longitudHipocentro = longitudHipocentro;
        this.longitudEpicentro = longitudEpicentro;
        this.valorMagnitud = valorMagnitud;
        this.estadoActual = estado;
        this.alcanceSismo = alcanceSismo;
        this.clasificacionSismo = clasificacionSismo;
        this.origenDeGeneracion = origenDeGeneracion;
    }

    // GETTERS Y SETTERS
    public Long getId() { 
        return id; 
    }
    public void setId(Long id) { 
        this.id = id; 
    }
    public LocalDateTime getFechaHoraFin() { 
        return fechaHoraFin; 
    }
    public void setFechaHoraFin(LocalDateTime fechaHoraFin) { 
        this.fechaHoraFin = fechaHoraFin; 
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
    public Double getLongitudEpicentro() { 
        return longitudEpicentro; 
    }
    public void setLongitudEpicentro(Double longitudEpicentro) { 
        this.longitudEpicentro = longitudEpicentro; 
    }
    @JsonProperty("magnitud")
    public Double getValorMagnitud() { 
        return valorMagnitud; 
    }
    public void setValorMagnitud(Double valorMagnitud) { 
        this.valorMagnitud = valorMagnitud; 
    }
    public List<SerieTemporal> getSeriesTemporales() { 
        return seriesTemporales; 
    }
    public void setSeriesTemporales(List<SerieTemporal> seriesTemporales) { 
        this.seriesTemporales = seriesTemporales; 
    }
    public List<CambioEstado> getCambiosEstado() { 
        return cambiosEstado; 
    }
    public void setCambiosEstado(List<CambioEstado> cambiosEstado) { 
        this.cambiosEstado = cambiosEstado; 
    }
    public Estado getEstadoActual() { 
        return estadoActual; 
    }
    public void setEstadoActual(Estado estadoActual) { 
        this.estadoActual = estadoActual; 
    }
    public AlcanceSismo getAlcanceSismo() { 
        return alcanceSismo; 
    }
    public void setAlcanceSismo(AlcanceSismo alcanceSismo) { 
        this.alcanceSismo = alcanceSismo; 
    }
    public ClasificacionSismo getClasificacionSismo() { 
        return clasificacionSismo; 
    }
    public void setClasificacionSismo(ClasificacionSismo clasificacionSismo) { 
        this.clasificacionSismo = clasificacionSismo; 
    }
    public OrigenDeGeneracion getOrigenDeGeneracion() { 
        return origenDeGeneracion; 
    }
    public void setOrigenDeGeneracion(OrigenDeGeneracion origenDeGeneracion) { 
        this.origenDeGeneracion = origenDeGeneracion; 
    }

    // MÉTODOS LÓGICOS
    public boolean esAutodetectado() { return this.estadoActual != null && this.estadoActual.esAutodetectado(); }
    public boolean esPendiente() { return this.estadoActual != null && this.estadoActual.esPendienteDeRevision(); }

    public String getDatos() {
        return String.format(
            "Fecha/Hora: %s | Ubicación Epicentro: (%.4f, %.4f) | Ubicación Hipocentro: (%.4f, %.4f) | Magnitud: %.1f | Estado: %s",
            getFechaHoraOcurrencia() != null ? getFechaHoraOcurrencia() : "N/A",
            getLatitudEpicentro() != null ? getLatitudEpicentro() : 0.0,
            getLongitudEpicentro() != null ? getLongitudEpicentro() : 0.0,
            getLatitudHipocentro() != null ? getLatitudHipocentro() : 0.0,
            getLongitudHipocentro() != null ? getLongitudHipocentro() : 0.0,
            getValorMagnitud() != null ? getValorMagnitud() : 0.0,
            estadoActual != null ? estadoActual.getNombreEstado() : "N/A"
        );
    }

    public void revisar(LocalDateTime fechaInicio, Estado estado, Empleado empleadoResponsable) {
        buscarUltimoCambioEstado();
        crearCambioEstado(fechaInicio, estado, empleadoResponsable);
        this.setEstadoActual(estado);
    }

    public void buscarUltimoCambioEstado() {
        if (this.cambiosEstado != null && !this.cambiosEstado.isEmpty()) {
            Optional<CambioEstado> ultimoCambio = this.cambiosEstado.stream()
                .filter(cambio -> cambio.getFechaFin() == null)
                .max(Comparator.comparing(CambioEstado::getFechaInicio));

            // Corrección
            ultimoCambio.ifPresent(CambioEstado::setFechaFin);
        }
    }
    
    public void crearCambioEstado(LocalDateTime fechaInicio, Estado estado, Empleado empleadoResponsable) {
        CambioEstado nuevoCambio = new CambioEstado(fechaInicio, this, estado, empleadoResponsable);
        this.cambiosEstado.add(nuevoCambio);
    }
    
    public List<SerieTemporal> obtenerSeriesTemporales(List<Sismografo> sismografos) {
        if (this.seriesTemporales == null || this.seriesTemporales.isEmpty()) {
            return new ArrayList<>();
        }
        
        // 1. Mapeamos para obtener la lista de nombres de estación, navegando por los objetos.
        List<String> estaciones = this.seriesTemporales.stream()
            .map(serie -> {
                if (serie.getSismografo() != null && serie.getSismografo().getEstacionSismologica() != null) {
                    return serie.getSismografo().getEstacionSismologica().getNombre();
                }
                return null;
            })
            .filter(Objects::nonNull)
            .sorted()
            .collect(Collectors.toList());

        Map<String, Integer> ordenEstaciones = new HashMap<>();
        for (int i = 0; i < estaciones.size(); i++) {
            ordenEstaciones.put(estaciones.get(i), i);
        }

        // 2. Ordenamos la lista original de series temporales usando nuestro mapa de orden.
        return this.seriesTemporales.stream()
            .sorted(Comparator.comparingInt(s -> {
                if (s.getSismografo() != null && s.getSismografo().getEstacionSismologica() != null) {
                    String nombreEstacion = s.getSismografo().getEstacionSismologica().getNombre();
                    return ordenEstaciones.getOrDefault(nombreEstacion, Integer.MAX_VALUE);
                }
                return Integer.MAX_VALUE; // Las series sin estación van al final.
            }))
            .collect(Collectors.toList());
    }
    
    public void rechazar(LocalDateTime fechaCambioEstado, Estado estado, Empleado empleadoResponsable) {
        this.buscarUltimoCambioEstado();
        this.crearCambioEstado(fechaCambioEstado, estado, empleadoResponsable);
        this.setEstadoActual(estado);
    }
}