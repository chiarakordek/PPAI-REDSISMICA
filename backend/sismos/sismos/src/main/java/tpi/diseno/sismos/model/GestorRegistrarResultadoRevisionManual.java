package tpi.diseno.sismos.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import tpi.diseno.sismos.repository.EventoSismicoRepository;
import tpi.diseno.sismos.repository.EstadoRepository;


@Service
public class GestorRegistrarResultadoRevisionManual {

    private LocalDateTime fechaHoraActual;

    private List<EventoSismico> eventosSismicos;
    private EventoSismico eventoSeleccionado;
    private List<SerieTemporal> SeriesTemporalesEventoSeleccionado;

    private List<String> datosEventosSismicos;
    private Estado punteroBloqueadoEnRevision;
    private Estado punteroRechazado;

    private Sesion sesionActual;
    private Empleado punteroEmpleado;

    //SIMULA LA INTEGRACIÓN CON OTRO CASO DE USO
    private final GenerarSismograma generarSismograma;
    private String mapaUbicacion;

    private List<Map<String, Object>> datosParaFrontend;


    private enum opcionResultadoRevision {
        RECHAZADO,
        CONFIRMADO,
        DERIVADO
    };

    private List<Estado> estados;

    private EventoSismicoRepository eventoSismicoRepository;
    private EstadoRepository estadoRepository;

    public GestorRegistrarResultadoRevisionManual( EventoSismicoRepository eventoSismicoRepository, EstadoRepository estadoRepository, GenerarSismograma generarSismograma) {
        this.eventoSismicoRepository = eventoSismicoRepository;
        this.estadoRepository = estadoRepository;
        this.generarSismograma = generarSismograma;

        // Inicialización de listas para evitar NullPointerException
        this.eventosSismicos = new ArrayList<>();
        this.datosEventosSismicos = new ArrayList<>();
        this.SeriesTemporalesEventoSeleccionado = new ArrayList<>();
    }

        // Getter para datos que mostrás en el frontend
    public List<String> getDatosEventosSismicos() {
        return this.datosEventosSismicos;
    }

    // Getter para las series temporales del evento seleccionado (para el frontend)
    public List<SerieTemporal> getSeriesTemporalesEventoSeleccionado() {
        return this.SeriesTemporalesEventoSeleccionado;
    }


    public void RegistrarNuevaRevision(){
        this.buscarEventosSismicos();
    }

    public void buscarEventosSismicos(){
        List<EventoSismico> eventos = this.eventoSismicoRepository.findAll();
        if (eventos.isEmpty()){
            throw new RuntimeException("No se encontraron eventos sísmicos.");
        }
                // Limpio listas antes de agregar nuevos elementos
        this.eventosSismicos.clear();
        this.datosEventosSismicos.clear();

        for (EventoSismico evento : eventos) {
            if (evento.esAutodetectado() && evento.esPendiente()){
                this.eventosSismicos.add(evento);
                this.datosEventosSismicos.add(evento.getDatos());
            } 
        }
        ordenarEventoSismicos();
    }

    public void ordenarEventoSismicos(){
        this.eventosSismicos.sort(Comparator.comparing(EventoSismico::getFechaHoraOcurrencia));
    }

    public void tomarSeleccionEventoSismico(EventoSismico evento){
        this.eventoSeleccionado = evento;
        bloquearEvento();
    }

    public Estado buscarEstadoBloqueado(){
        this.estados = this.estadoRepository.findAll();
        for (Estado estado : estados) {
            if (estado.esAmbitoEventoSismico() && estado.esBloqueadoEnRevision()){
                return estado;
            }
        }
        return null;
    }

    public LocalDateTime tomarFechaHoraActual(){
        return LocalDateTime.now();
    }

    public Empleado buscarEmpleadoLogueado(){
        return this.sesionActual.obtenerUsuarioLogueado();
    }

    public void bloquearEvento(){
        this.fechaHoraActual = tomarFechaHoraActual();
        this.punteroBloqueadoEnRevision = buscarEstadoBloqueado();
        if (this.punteroBloqueadoEnRevision == null){
            throw new RuntimeException("No se encontró estado de bloqueo en revisión.");
        }
        this.punteroEmpleado = buscarEmpleadoLogueado();
        this.eventoSeleccionado.revisar(fechaHoraActual, eventoSeleccionado, punteroBloqueadoEnRevision, punteroEmpleado);
        buscarDatosSismicos(eventoSeleccionado);
    }

    public void buscarDatosSismicos(EventoSismico evento){
        this.SeriesTemporalesEventoSeleccionado = evento.obtenerSeriesTemporales();


        List<Map<String, Object>> resultado = new ArrayList<>();
        for (SerieTemporal serieTemporal : this.SeriesTemporalesEventoSeleccionado) {
             String estacion = serieTemporal.buscarEstacionSismologica();
            for (MuestraSismica muestra : serieTemporal.buscarMuestrasSismicas()) {
                Map<String, Object> datos = new HashMap<>();
                datos.put("estacion", estacion);

                for (Map<String, Object> detalle : muestra.buscarDetalleMuestra()) {
                    String tipo = detalle.get("Tipo de dato").toString();
                    String valor = detalle.get("Valor").toString();

                    switch (tipo.toLowerCase()) {
                    case "velocidad":
                        datos.put("velocidad", valor);
                        break;
                    case "frecuencia":
                        datos.put("frecuencia", valor);
                        break;
                    case "longitud":
                        datos.put("longitud", valor);
                        break;
                    }
                }

            resultado.add(datos);
            }
        }
        // Guardalo en un atributo de la clase si lo querés usar luego
        this.datosParaFrontend = resultado;
        llamarCasoDeUsoGenerarSismograma();
    }

    public void llamarCasoDeUsoGenerarSismograma(){
        this.mapaUbicacion = this.generarSismograma.include(this.eventoSeleccionado.getId());
        //COMUNICACION CON LA PANTALLA PARA VER MAPA 
    }

    public String tomarOpcVerMapa(String opcion){
        if (opcion.equalsIgnoreCase("VER MAPA")){
            return this.mapaUbicacion;
        }
        return null;
    }

    public void tomarOpcModificarDatos(){
        //COMUNICACION CON LA PANTALLA
    }

    public void evaluarResultadoInspeccion(){
        //COMUNICACION CON LA PANTALLA
    }

    public void tomarSeleccionRechazada(String opcion){
        if (opcionResultadoRevision.RECHAZADO.name().equalsIgnoreCase(opcion)){
            validarExistenciaDatos();
        }else{
            throw new RuntimeException("Opción no reconocida.");
        }
    }

     private void validarExistenciaDatos(){
         if (this.SeriesTemporalesEventoSeleccionado.isEmpty()){
             throw new RuntimeException("No se encontraron datos de las series temporales para el evento seleccionado.");
         }
         for (SerieTemporal serieTemporal : this.SeriesTemporalesEventoSeleccionado) {
             if (serieTemporal.getMuestrasSismicas().isEmpty()){
                 throw new RuntimeException("No se encontraron datos de las muestras sismicas para la serie temporal seleccionada.");
             }
         }
         rechazararEvento();
     }

    public Estado buscarEstadoRechazado(){
        this.estados = this.estadoRepository.findAll();
        for (Estado estado : estados) {
            if (estado.esAmbitoEventoSismico() && estado.esRechazado()){
                return estado;
            }
        }
        return null;
    } 

    public void rechazararEvento(){
        this.punteroRechazado = buscarEstadoRechazado();
        if (this.punteroRechazado == null){
            throw new RuntimeException("No se encontró estado de rechazo.");
        }
        this.fechaHoraActual = tomarFechaHoraActual();
        this.eventoSeleccionado.rechazar(fechaHoraActual, eventoSeleccionado, punteroRechazado, punteroEmpleado);
    }

    public void finCU(){
        this.sesionActual.setFechaFin(tomarFechaHoraActual());
    }

    public List<Map<String, Object>> getDatosParaFrontend() {
        return this.datosParaFrontend;
    }

}


