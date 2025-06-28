package tpi.diseno.sismos.model;

import jakarta.persistence.*;
import tpi.diseno.sismos.repository.SismografoRepository;

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
    private ArrayList<SerieTemporal> seriesTemporales;

/** Cambios de estado que ha atravesado este evento sísmico. */
    @OneToMany(mappedBy = "eventoSismico", cascade = CascadeType.ALL)
    private List<CambioEstado> cambiosEstado;

    @ManyToOne()
    private Estado estadoActual;

    private SismografoRepository sismografoRepository;



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

    public Double getLatitudEpicentro() {
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

    public Double getLongitudEpicentro() {
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
    public void setSeriesTemporales(ArrayList<SerieTemporal> seriesTemporales) {
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
        return this.estadoActual.esPendienteDeRevision();
    }
    //PARA DEVOLVER fecha y hora de ocurrencia del evento,ubicación (coordenadas geográficas del epicentro y del hipocentro), magnitud
    public String getDatos() {
        return String.format(
            "Fecha/Hora: %s | Ubicación Epicentro: (%.4f, %.4f) | Ubicación Hipocentro: (%.4f, %.4f) | Magnitud: %.1f | %s | Estado: %s",
            getFechaHoraOcurrencia(),
            getLatitudEpicentro(),
            getLongitudEpicentro(),
            getLatitudHP(),
            getLongitudHP(),
            getMagnitud()
        );
    }
    public void revisar(LocalDateTime fechaInicio, EventoSismico eventoSismico, Estado estado, Empleado empleadoResponsable){
        this.buscarUltimoCambioEstado();
        this.crearCambioEstado(fechaInicio, eventoSismico, estado, empleadoResponsable);
        this.setEstado(estado);
    }

    public void buscarUltimoCambioEstado(){
        for(CambioEstado cambio: cambiosEstado){
            if(cambio.esUltimoCambioEstado()){
                cambio.setFechaFin();
            }
        }
    }
    public void crearCambioEstado(LocalDateTime fechaInicio, EventoSismico eventoSismico, Estado estado, Empleado empleadoResponsable){
        CambioEstado cambio = new CambioEstado(fechaInicio, eventoSismico, estado, empleadoResponsable);
        this.cambiosEstado.add(cambio);
    }

    public List<SerieTemporal> obtenerSeriesTemporales() {
        /*Resultados esperados por el gestor:
         * Estación: Córdoba
            - Velocidad: 5.6
            - Frecuencia: 2.1
            - Longitud: 34.8

           Estación: Mendoza
            - Velocidad: 6.2
            - Frecuencia: 1.8
            - Longitud: 30.4
         */
        List<SerieTemporal> series = new ArrayList<>();

        for (SerieTemporal serie : this.seriesTemporales) {
            serie.getDatosSerieTemporal(); 
            series.add(serie);  
        }

        

        
        List<Sismografo> sismografos = sismografoRepository.findAll();
        String nombre = "";
        for (SerieTemporal serie : series) {
            for(Sismografo sismografo : sismografos) {
                if(sismografo.sosMiSismografo(serie.getId())) {
                    nombre = sismografo.getDatosSismografo();
                    break;
                }
            }

        }
        List<SerieTemporal> ordenadas = clasificarSeriesTemporales(series, nombre);
        return ordenadas;
    }

    //ORDENA LAS SERIES TEMPORALES POR ID DE MENOR A MAYOR, NO SE SI ESTA BIEN
    public List<SerieTemporal> clasificarSeriesTemporales(List<SerieTemporal> seriesTemporales, String nombreEstacion){
        seriesTemporales.sort(Comparator.comparing(nombreEstacion));
        return seriesTemporales;
    }
    //BUSCA EL ULTIMO CAMBIO DE ESTADO PERO EL GESTOR YA LO TIENE.
    public void rechazar(LocalDateTime fechaCambioEstado, EventoSismico eventoSismico, Estado estado, Empleado empleadoResponsable){
        this.buscarUltimoCambioEstado();
        this.crearCambioEstado(fechaCambioEstado, eventoSismico,estado, empleadoResponsable);
        this.setEstado(estado);
    }
}
