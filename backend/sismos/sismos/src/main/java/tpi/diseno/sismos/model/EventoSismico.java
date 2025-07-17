// Archivo completo para: backend\sismos\sismos\src\main\java\tpi\diseno\sismos\model\EventoSismico.java

package tpi.diseno.sismos.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Entity
public class EventoSismico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime fechaHoraFin;
    private LocalDateTime fechaHoraOcurrencia;
    private Double latitudEpicentro;
    private Double latitudHipocentro;   // Nombre del campo
    private Double longitudHipocentro;  // Nombre del campo
    private Double longitudEpicentro;
    private Double valorMagnitud;

    @OneToMany(mappedBy = "eventoSismico", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("evento-serie")
    private List<SerieTemporal> seriesTemporales = new ArrayList<>();

    @OneToMany(mappedBy = "eventoSismico", cascade = CascadeType.ALL)
    @JsonManagedReference("evento-cambioestado")
    private List<CambioEstado> cambiosEstado;

    @ManyToOne(fetch = FetchType.EAGER)
    private Estado estadoActual;

    @ManyToOne(fetch = FetchType.EAGER)
    private AlcanceSismo alcanceSismo;

    @ManyToOne(fetch = FetchType.EAGER)
    private ClasificacionSismo clasificacionSismo;

    @ManyToOne(fetch = FetchType.EAGER)
    private OrigenDeGeneracion origenDeGeneracion;

    public EventoSismico() {}
    public EventoSismico(LocalDateTime fechaHoraFin, LocalDateTime fechaHoraOcurrencia, Double latitudEpicentro, Double latitudHipocentro, Double longitudHipocentro, Double longitudEpicentro, Double valorMagnitud, Estado estado, AlcanceSismo alcanceSismo, ClasificacionSismo clasificacionSismo, OrigenDeGeneracion origenDeGeneracion) { this.fechaHoraFin = fechaHoraFin; this.fechaHoraOcurrencia = fechaHoraOcurrencia; this.latitudEpicentro = latitudEpicentro; this.latitudHipocentro = latitudHipocentro; this.longitudHipocentro = longitudHipocentro; this.longitudEpicentro = longitudEpicentro; this.valorMagnitud = valorMagnitud; this.estadoActual = estado; this.alcanceSismo = alcanceSismo; this.clasificacionSismo = clasificacionSismo; this.origenDeGeneracion = origenDeGeneracion; }
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public LocalDateTime getFechaHoraFin() { return fechaHoraFin; }
    public void setFechaHoraFin(LocalDateTime fechaHoraFin) { this.fechaHoraFin = fechaHoraFin; }
    public LocalDateTime getFechaHoraOcurrencia() { return fechaHoraOcurrencia; }
    public void setFechaHoraOcurrencia(LocalDateTime fechaHoraOcurrencia) { this.fechaHoraOcurrencia = fechaHoraOcurrencia; }
    public Double getLatitudEpicentro() { return latitudEpicentro; }
    public void setLatitudEpicentro(Double latitudEpicentro) { this.latitudEpicentro = latitudEpicentro; }
    
    // --- MÉTODOS CORREGIDOS ---
    // Ahora el nombre del método coincide con el del campo, generando un JSON consistente.
    public Double getLatitudHipocentro() { return latitudHipocentro; }
    public void setLatitudHipocentro(Double latitudHipocentro) { this.latitudHipocentro = latitudHipocentro; }
    public Double getLongitudHipocentro() { return longitudHipocentro; }
    public void setLongitudHipocentro(Double longitudHipocentro) { this.longitudHipocentro = longitudHipocentro; }

    public Double getLongitudEpicentro() { return longitudEpicentro; }
    public void setLongitudEpicentro(Double longitudEpicentro) { this.longitudEpicentro = longitudEpicentro; }
    
    @JsonProperty("magnitud")
    public Double getMagnitud() { return valorMagnitud; }
    public void setValorMagnitud(Double valorMagnitud) { this.valorMagnitud = valorMagnitud; }

    public List<SerieTemporal> getSeriesTemporales() { return seriesTemporales; }
    public void setSeriesTemporales(ArrayList<SerieTemporal> seriesTemporales) { this.seriesTemporales = seriesTemporales; }
    public List<CambioEstado> getCambiosEstado() { return cambiosEstado; }
    public void setCambiosEstado(List<CambioEstado> cambiosEstado) { this.cambiosEstado = cambiosEstado; }
    public void setEstado(Estado estado){ this.estadoActual = estado; }
    public Estado getEstado(){ return this.estadoActual; }
    public AlcanceSismo getAlcanceSismo() { return alcanceSismo; }
    public void setAlcanceSismo(AlcanceSismo alcanceSismo) { this.alcanceSismo = alcanceSismo; }
    public ClasificacionSismo getClasificacionSismo() { return clasificacionSismo; }
    public void setClasificacionSismo(ClasificacionSismo clasificacionSismo) { this.clasificacionSismo = clasificacionSismo; }
    public OrigenDeGeneracion getOrigenDeGeneracion() { return origenDeGeneracion; }
    public void setOrigenDeGeneracion(OrigenDeGeneracion origenDeGeneracion) { this.origenDeGeneracion = origenDeGeneracion; }
    public boolean esAutodetectado(){ return this.estadoActual != null && this.estadoActual.esAutodetectado(); }
    public boolean esPendiente(){ return this.estadoActual != null && this.estadoActual.esPendienteDeRevision(); }
    
    // El método getDatos() ahora usará los métodos corregidos
    public String getDatos() { return String.format( "Fecha/Hora: %s | Ubicación Epicentro: (%.4f, %.4f) | Ubicación Hipocentro: (%.4f, %.4f) | Magnitud: %.1f | Estado: %s", getFechaHoraOcurrencia() != null ? getFechaHoraOcurrencia() : "N/A", getLatitudEpicentro() != null ? getLatitudEpicentro() : 0.0, getLongitudEpicentro() != null ? getLongitudEpicentro() : 0.0, getLatitudHipocentro() != null ? getLatitudHipocentro() : 0.0, getLongitudHipocentro() != null ? getLongitudHipocentro() : 0.0, getMagnitud() != null ? getMagnitud() : 0.0, estadoActual != null ? estadoActual.getNombreEstado() : "N/A" ); }
    
    public void revisar(LocalDateTime fechaInicio, EventoSismico eventoSismico, Estado estado, Empleado empleadoResponsable){ this.buscarUltimoCambioEstado(); this.crearCambioEstado(fechaInicio, eventoSismico, estado, empleadoResponsable); this.setEstado(estado); }
    public void buscarUltimoCambioEstado(){ if (this.cambiosEstado != null) { for(CambioEstado cambio: cambiosEstado){ if(cambio.esUltimoCambioEstado()){ cambio.setFechaFin(); } } } }
    public void crearCambioEstado(LocalDateTime fechaInicio, EventoSismico eventoSismico, Estado estado, Empleado empleadoResponsable){ if (this.cambiosEstado == null) { this.cambiosEstado = new ArrayList<>(); } CambioEstado cambio = new CambioEstado(fechaInicio, eventoSismico, estado, empleadoResponsable); this.cambiosEstado.add(cambio); }
    public List<SerieTemporal> obtenerSeriesTemporales(List<Sismografo> sismografos) { List<SerieTemporal> series = new ArrayList<>(); List<String> estaciones = new ArrayList<>(); if (this.seriesTemporales != null) { for (SerieTemporal serie : this.seriesTemporales) { serie.getDatosSerieTemporal(); series.add(serie); estaciones.add(serie.buscarEstacionSismologica(sismografos)); } } return clasificarSeriesTemporales(series, estaciones, sismografos); }
    public List<SerieTemporal> clasificarSeriesTemporales(List<SerieTemporal> seriesTemporales, List<String> estaciones, List<Sismografo> sismografos){ estaciones.sort(Comparator.naturalOrder()); Map<String, Integer> ordenEstaciones = new HashMap<>(); for (int i = 0; i < estaciones.size(); i++) { ordenEstaciones.put(estaciones.get(i), i); } return seriesTemporales.stream() .sorted(Comparator.comparingInt( s -> ordenEstaciones.getOrDefault(s.buscarEstacionSismologica(sismografos), Integer.MAX_VALUE) )) .collect(Collectors.toList()); }
    public void rechazar(LocalDateTime fechaCambioEstado, EventoSismico eventoSismico, Estado estado, Empleado empleadoResponsable){ this.buscarUltimoCambioEstado(); this.crearCambioEstado(fechaCambioEstado, eventoSismico,estado, empleadoResponsable); this.setEstado(estado); }
}