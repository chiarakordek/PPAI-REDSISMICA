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

/** Lista de series temporales asociadas al evento. */
    @OneToMany(mappedBy = "eventoSismico", cascade = CascadeType.ALL)
    private List<SerieTemporal> seriesTemporales;

/** Cambios de estado que ha atravesado este evento sísmico. */
    @OneToMany(mappedBy = "eventoSismico", cascade = CascadeType.ALL)
    private List<CambioEstado> cambiosEstado;

    @ManyToOne()
    private Estado estadoActual;



/**Constructor */
    public EventoSismico() {
    }

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

    //////////// Getters y Setters
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

    public Double getLatitud() {
        return latitudEpicentro;
    }
    public void setLatitudEpicentro(Double latitudEpicentro) {
        this.latitudEpicentro = latitudEpicentro;
    }

    public Double getLatitudHP() {
        return latitudHipocentro;
    }
    public void setLatitudHipocentro(Double latitudHipocentro) {
        this.latitudHipocentro = latitudHipocentro;
    }

    public Double getLongitudHP() {
        return longitudHipocentro;
    }
    public void setLongitudHipocentro(Double longitudHipocentro) {
        this.longitudHipocentro = longitudHipocentro;
    }

    public Double getLongitud() {
        return longitudEpicentro;
    }
    public void setLongitudEpicentro(Double longitudEpicentro) {
        this.longitudEpicentro = longitudEpicentro;
    }

    public Double getMagnitud() {
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
    public void setEstado(Estado estado){
        this.estadoActual = estado;
    }
    public Estado getEstado(){
        return this.estadoActual;
    }
    public boolean esAutodetectado(){
        return this.estadoActual.esAutodetectado();
    } 
    public boolean esPendiente(){
        return this.estadoActual.esPendiente();
    }
    //PARA DEVOLVER LOS DATOS UTILIZA UNA CLASE AUXILIAR (DatosEventoSismico), NO SE SI ESTA BIEN
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
    public void revisar(String denominacion, String nombreUnidadMedida, Double valorUmbral, LocalDateTime fechaCambioEstado, EventoSismico eventoSismico, Estado estado){
        this.buscarUltimoCambioEstado();
        this.crearCambioEstado(denominacion, nombreUnidadMedida, valorUmbral, fechaCambioEstado, eventoSismico,estado);
        this.setEstado(estado);
    }

    public void buscarUltimoCambioEstado(){
        for(CambioEstado cambio: this.getCambiosEstado()){
            if(cambio.esUltimoCambioEstado()){
                cambio.setFechaHoraFin();
            }
        }
    }
    public void crearCambioEstado(String denominacion, String nombreUnidadMedida, Double valorUmbral, LocalDateTime fechaCambioEstado, EventoSismico eventoSismico, Estado estado){
        new CambioEstado(denominacion, nombreUnidadMedida, valorUmbral, fechaCambioEstado, eventoSismico,estado);

    }

    public ArrayList<SerieTemporal> obtenerSeriesTemporales(){
        return clasificarSeriesTemporales(this.seriesTemporales.getDatosSerieTemporal());
    }

    //ORDENA LAS SERIES TEMPORALES POR ID DE MENOR A MAYOR, NO SE SI ESTA BIEN
    public ArrayList<SerieTemporal> clasificarSeriesTemporales(ArrayList<SerieTemporal> seriesTemporales){
        ArrayList<SerieTemporal> seriesOrdenadas = new ArrayList();
        for(SerieTemporal serie: seriesTemporales){
            Long IdEstacionSismologica = serie.getEstacionSismologica().getId();
            seriesOrdenadas.sort(Comparator.comparingLong(SerieTemporal::IdEstacionSismologica));
        }
        return seriesOrdenadas;
    }
    //BUSCA EL ULTIMO CAMBIO DE ESTADO PERO EL GESTOR YA LO TIENE.
    public void rechazar(String denominacion, String nombreUnidadMedida, Double valorUmbral, LocalDateTime fechaCambioEstado, EventoSismico eventoSismico, Estado estado){
        this.buscarUltimoCambioEstado();
        this.crearCambioEstado(denominacion, nombreUnidadMedida, valorUmbral, fechaCambioEstado, eventoSismico,estado);
        this.setEstado(estado);
    }
}
