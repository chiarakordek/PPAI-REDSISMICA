package tpi.diseno.sismos.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;

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
    private Estado estado;

/** Lista de series temporales asociadas al evento. */
    @OneToMany(mappedBy = "eventoSismico", cascade = CascadeType.ALL)
    private List<SerieTemporal> seriesTemporales;

/** Cambios de estado que ha atravesado este evento sísmico. */
    @OneToMany(mappedBy = "eventoSismico", cascade = CascadeType.ALL)
    private List<CambioEstado> cambiosEstado;

    public EventoSismico() {
    }
    public EventoSismico(LocalDateTime fechaHoraFin, LocalDateTime fechaHoraOcurrencia, Double latitudEpicentro, Double latitudHipocentro, Double longitudHipocentro, Double longitudEpicentro, Double valorMagnitud, Estado estado) {
        this.fechaHoraFin = fechaHoraFin;
        this.fechaHoraOcurrencia = fechaHoraOcurrencia;
        this.latitudEpicentro = latitudEpicentro;
        this.latitudHipocentro = latitudHipocentro;
        this.longitudHipocentro = longitudHipocentro;
        this.longitudEpicentro = longitudEpicentro;
        this.valorMagnitud = valorMagnitud;
        this.estado = estado;
    }
    public Long getId() {
        return this.id;
    }
    public LocalDateTime getFechaHoraFin() {
        return this.fechaHoraFin;
    }
    public void setFechaHoraFin(LocalDateTime fechaHoraFin) {
        this.fechaHoraFin = fechaHoraFin;
    }
    public LocalDateTime getFechaHoraOcurrencia() {
        return this.fechaHoraOcurrencia;
    }
    public void setFechaHoraOcurrencia(LocalDateTime fechaHoraOcurrencia) {
        this.fechaHoraOcurrencia = fechaHoraOcurrencia;
    }
    public Double getLatitud() {
        return this.latitudEpicentro;
    }
    public void setLatitudEpicentro(Double latitudEpicentro) {
        this.latitudEpicentro = latitudEpicentro;
    }
    public Double getLatitudHP() {
        return this.latitudHipocentro;
    }
    public void setLatitudHipocentro(Double latitudHipocentro) {
        this.latitudHipocentro = latitudHipocentro;
    }
    public Double getLongitudHP() {
        return this.longitudHipocentro;
    }
    public void setLongitudHipocentro(Double longitudHipocentro) {
        this.longitudHipocentro = longitudHipocentro;
    }
    public Double getLongitud() {
        return this.longitudEpicentro;
    }
    public void setLongitudEpicentro(Double longitudEpicentro) {
        this.longitudEpicentro = longitudEpicentro;
    }
    public Double getMagnitud() {
        return this.valorMagnitud;
    }
    public void setValorMagnitud(Double valorMagnitud) {
        this.valorMagnitud = valorMagnitud;
    }

    public EventoSismico getDatos() {
        return this;
    }

    public List<SerieTemporal> obtenerSeriesTemporales() {
        return seriesTemporales;
    }

    public boolean esAutodetectado(){
        return this.estado.esAutodetectado();
    }
    public boolean esPendiente(){
        return this.estado.esPendiente();
    }

    public CambioEstado BuscarUltimoCambioEstado(){
        for( CambioEstado cambio : cambiosEstado) {
            if (cambio.esUltimoCambioEstado()){
                return cambio;
            }
        }
        return null;
    }

    public ArrayList<CambioEstado> getCambiosEstado(){
        ArrayList<CambioEstado> cambios = new ArrayList<>();
        for( CambioEstado cambio : cambiosEstado) {
            if (cambio.getEventoSismico().getId().equals(this.id)) {
                cambios.add(cambio);
            }
        }
        return cambios;
    }
}
