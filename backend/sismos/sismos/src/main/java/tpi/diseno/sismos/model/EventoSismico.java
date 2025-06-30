package tpi.diseno.sismos.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Map;
import java.util.HashMap;

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
    @OneToMany(mappedBy = "eventoSismico", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SerieTemporal> seriesTemporales = new ArrayList<>();

/** Cambios de estado que ha atravesado este evento sísmico. */
    @OneToMany(mappedBy = "eventoSismico", cascade = CascadeType.ALL)
    private List<CambioEstado> cambiosEstado;

    @ManyToOne()
    private Estado estadoActual;

    @ManyToOne()
    private AlcanceSismo alcanceSismo;

    @ManyToOne()
    private ClasificacionSismo clasificacionSismo;

    @ManyToOne()
    private OrigenDeGeneracion origenDeGeneracion;


/**Constructor */
    public EventoSismico() {
    }

    public EventoSismico(LocalDateTime fechaHoraFin, LocalDateTime fechaHoraOcurrencia,
                        Double latitudEpicentro, Double latitudHipocentro,
                        Double longitudHipocentro, Double longitudEpicentro,
                        Double valorMagnitud, Estado estado, 
                        AlcanceSismo alcanceSismo, ClasificacionSismo clasificacionSismo, OrigenDeGeneracion origenDeGeneracion) {
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



    public boolean esAutodetectado(){
        return this.estadoActual.esAutodetectado();
    } 
    public boolean esPendiente(){
        return this.estadoActual.esPendienteDeRevision();
    }
    //PARA DEVOLVER fecha y hora de ocurrencia del evento,ubicación (coordenadas geográficas del epicentro y del hipocentro), magnitud
    public String getDatos() {
        return String.format(
            "Fecha/Hora: %s | Ubicación Epicentro: (%.4f, %.4f) | Ubicación Hipocentro: (%.4f, %.4f) | Magnitud: %.1f | Estado: %s",
            getFechaHoraOcurrencia() != null ? getFechaHoraOcurrencia() : "N/A",
            getLatitudEpicentro() != null ? getLatitudEpicentro() : 0.0,
            getLongitudEpicentro() != null ? getLongitudEpicentro() : 0.0,
            getLatitudHP() != null ? getLatitudHP() : 0.0,
            getLongitudHP() != null ? getLongitudHP() : 0.0,
            getMagnitud() != null ? getMagnitud() : 0.0,
            estadoActual != null ? estadoActual.getNombreEstado() : "N/A"
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

    public List<SerieTemporal> obtenerSeriesTemporales(List<Sismografo> sismografos) {


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

        // Lista de series temporales
        List<SerieTemporal> series = new ArrayList<>();
        // Lista de estaciones sismológicas
        List<String> estaciones = new ArrayList<>();

        // Obtiene todos los datos de las series temporales asociadas al evento 
        for (SerieTemporal serie : this.seriesTemporales) {
            serie.getDatosSerieTemporal(); 
            series.add(serie);  
            //Para cada serie temporal, se obtiene su estación sismológica
            estaciones.add(serie.buscarEstacionSismologica(sismografos));
        }   

        // Ordena la lista de series temporales por estación sismológica
        List<SerieTemporal> ordenadas = clasificarSeriesTemporales(series, estaciones, sismografos);
        // Devuelve la lista ordenada de series temporales
        return ordenadas;
    }

    //Ordena las series temporales por estación sismológica
    public List<SerieTemporal> clasificarSeriesTemporales(List<SerieTemporal> seriesTemporales, List<String> estaciones, List<Sismografo> sismografos){
        //Ordena las estaciones alfabéticamente
        estaciones.sort(Comparator.naturalOrder());
        //Crea un Map (ordenEstaciones) que asigna a cada estación su posición o índice en la lista ordenada.
        Map<String, Integer> ordenEstaciones = new HashMap<>();
        for (int i = 0; i < estaciones.size(); i++) {
            ordenEstaciones.put(estaciones.get(i), i);
        }
        
        //Ordena las series temporales según la posición de su estación asociada en el mapa
        return seriesTemporales.stream()
            .sorted(Comparator.comparingInt(
                s -> ordenEstaciones.getOrDefault(s.buscarEstacionSismologica(sismografos), Integer.MAX_VALUE)
                //Si una serie tiene una estación que no está en la lista estaciones, se le asigna Integer.MAX_VALUE 
                //(quedará al final).
            ))
            //Lo transforma en lista para devolverla
            .collect(Collectors.toList());
    }
    //BUSCA EL ULTIMO CAMBIO DE ESTADO PERO EL GESTOR YA LO TIENE.
    public void rechazar(LocalDateTime fechaCambioEstado, EventoSismico eventoSismico, Estado estado, Empleado empleadoResponsable){
        this.buscarUltimoCambioEstado();
        this.crearCambioEstado(fechaCambioEstado, eventoSismico,estado, empleadoResponsable);
        this.setEstado(estado);
    }
}