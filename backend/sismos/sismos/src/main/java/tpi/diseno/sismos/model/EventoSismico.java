package tpi.diseno.sismos.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;

@Entity
public class EventoSismico {

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

    @OneToMany(mappedBy = "eventoSismico", cascade = CascadeType.ALL)
    private List<SerieTemporal> seriesTemporales;

    @OneToMany(mappedBy = "eventoSismico", cascade = CascadeType.ALL)
    private List<CambioEstado> cambiosEstado;

    @ManyToOne
    private Estado estadoActual;

    // Constructor vacío
    public EventoSismico() {}

    // Constructor completo
    public EventoSismico(LocalDateTime fechaHoraFin, LocalDateTime fechaHoraOcurrencia,
                         Double latitudEpicentro, Double latitudHipocentro,
                         Double longitudHipocentro, Double longitudEpicentro,
                         Double valorMagnitud, Estado estado) {
        this.fechaHoraFin = fechaHoraFin;
        this.fechaHoraOcurrencia = fechaHoraOcurrencia;
        this.latitudEpicentro = latitudEpicentro;
        this.latitudHipocentro = latitudHipocentro;
        this.longitudHipocentro = longitudHipocentro;
        this.longitudEpicentro = longitudEpicentro;
        this.valorMagnitud = valorMagnitud;
        this.estadoActual = estado;
    }

    // Getters y setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public LocalDateTime getFechaHoraFin() { return fechaHoraFin; }
    public void setFechaHoraFin(LocalDateTime fechaHoraFin) { this.fechaHoraFin = fechaHoraFin; }

    public LocalDateTime getFechaHoraOcurrencia() { return fechaHoraOcurrencia; }
    public void setFechaHoraOcurrencia(LocalDateTime fechaHoraOcurrencia) { this.fechaHoraOcurrencia = fechaHoraOcurrencia; }

    public Double getLatitud() { return latitudEpicentro; }
    public void setLatitudEpicentro(Double latitudEpicentro) { this.latitudEpicentro = latitudEpicentro; }

    public Double getLatitudHP() { return latitudHipocentro; }
    public void setLatitudHipocentro(Double latitudHipocentro) { this.latitudHipocentro = latitudHipocentro; }

    public Double getLongitudHP() { return longitudHipocentro; }
    public void setLongitudHipocentro(Double longitudHipocentro) { this.longitudHipocentro = longitudHipocentro; }

    public Double getLongitud() { return longitudEpicentro; }
    public void setLongitudEpicentro(Double longitudEpicentro) { this.longitudEpicentro = longitudEpicentro; }

    public Double getMagnitud() { return valorMagnitud; }
    public void setValorMagnitud(Double valorMagnitud) { this.valorMagnitud = valorMagnitud; }

    public List<SerieTemporal> getSeriesTemporales() { return seriesTemporales; }
    public void setSeriesTemporales(List<SerieTemporal> seriesTemporales) { this.seriesTemporales = seriesTemporales; }

    public List<CambioEstado> getCambiosEstado() { return cambiosEstado; }
    public void setCambiosEstado(List<CambioEstado> cambiosEstado) { this.cambiosEstado = cambiosEstado; }

    public Estado getEstado() { return this.estadoActual; }
    public void setEstado(Estado estado) { this.estadoActual = estado; }

    public boolean esAutodetectado() { return this.estadoActual.esAutodetectado(); }
    public boolean esPendiente() { return this.estadoActual.esPendienteDeRevision(); }

    // Devuelve los datos del evento como DTO
    public DatosEventoSismico getDatos() {
        return new DatosEventoSismico(
            this.getFechaHoraOcurrencia(),
            this.getLatitud(),
            this.getLongitud(),
            this.getLatitudHP(),
            this.getLongitudHP(),
            this.getMagnitud()
        );
    }

    public void revisar(String denominacion, LocalDateTime fechaCambioEstado, EventoSismico eventoSismico, Estado estado){
        this.buscarUltimoCambioEstado();
        this.crearCambioEstado(fechaCambioEstado, eventoSismico, estado, null); // el Empleado se debería pasar si está disponible
        this.setEstado(estado);
    }

    public void buscarUltimoCambioEstado() {
        for(CambioEstado cambio : this.getCambiosEstado()) {
            if(cambio.esUltimoCambioEstado()) {
                cambio.setFechaFin();
            }
        }
    }

    public void crearCambioEstado(LocalDateTime fechaCambioEstado, EventoSismico eventoSismico, Estado estado, Empleado empleadoResponsable){
        CambioEstado cambio = new CambioEstado(fechaCambioEstado, eventoSismico, estado, empleadoResponsable);
        this.cambiosEstado.add(cambio);
    }

    // Devuelve las series temporales ordenadas por ID
    public ArrayList<SerieTemporal> obtenerSeriesTemporales() { 
        return clasificarSeriesTemporales(new ArrayList<>(this.seriesTemporales));
    }

    public ArrayList<SerieTemporal> clasificarSeriesTemporales(ArrayList<SerieTemporal> seriesTemporalesOrdenadas) {
        seriesTemporalesOrdenadas.sort(Comparator.comparingLong(SerieTemporal::getId));
        return seriesTemporalesOrdenadas;
    }

    public void rechazar(LocalDateTime fechaCambioEstado, EventoSismico eventoSismico, Estado estado, Empleado empleadoResponsable){
        this.buscarUltimoCambioEstado();
        this.crearCambioEstado(fechaCambioEstado, eventoSismico, estado, empleadoResponsable);
        this.setEstado(estado);
    }
}
