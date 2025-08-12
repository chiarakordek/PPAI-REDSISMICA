package tpi.diseno.sismos.model;

import org.springframework.stereotype.Service;
import tpi.diseno.sismos.repository.EventoSismicoRepository;
import tpi.diseno.sismos.repository.EstadoRepository;
import tpi.diseno.sismos.repository.SismografoRepository;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GestorRegistrarResultadoRevisionManual {

 // ATRIBUTOS.
    private LocalDateTime fechaHoraActual;
    private List<EventoSismico> eventosSismicos;
    private EventoSismico eventoSeleccionado;
    private Estado punteroBloqueadoEnRevision;
    private Estado punteroRechazado;
    private Sesion sesionActual;
    private Empleado punteroEmpleado;

   private final EventoSismicoRepository eventoSismicoRepository;
   private final EstadoRepository estadoRepository;

 // Métodos para comunicación con otros objetos
    private final GenerarSismograma generarSismograma;
    private String mapaUbicacion;
    private final SismografoRepository sismografoRepository;
    private List<Map<String, Object>> datosParaFrontend;

 private enum opcionResultadoRevision {
   RECHAZADO,
   CONFIRMADO,
   DERIVADO
   }
  //CONSTRUCTOR
  public GestorRegistrarResultadoRevisionManual(EventoSismicoRepository eventoSismicoRepository,
                                                EstadoRepository estadoRepository,
                                                GenerarSismograma generarSismograma,
                                                SismografoRepository sismografoRepository) {
        this.eventoSismicoRepository = eventoSismicoRepository;
        this.estadoRepository = estadoRepository;
        this.generarSismograma = generarSismograma;
        this.sismografoRepository = sismografoRepository;

        // Inicialización de listas
        this.eventosSismicos = new ArrayList<>();
        this.datosParaFrontend = new ArrayList<>();
 }

// METODOS
 public void registrarNuevaRevision() {
 this.buscarEventosSismicos();
 }

  public void buscarEventosSismicos() {
        List<EventoSismico> todosLosEventos = eventoSismicoRepository.findAll();
        this.eventosSismicos = new ArrayList<>();

       
        for (EventoSismico evento : todosLosEventos) {
        
            if (evento.esAutodetectado()) {
            // Se agrega el evento a la lista si cumple la condición.
            // Se aplica el patron experto ya que solo el evento conoce sus estados
            this.eventosSismicos.add(evento);

            // Se obtienen los datos del evento, lo que a su vez llama a los getters.
            this.datosParaFrontend.add(evento.getDatos());
            }
        }

 }

 public void ordenarEventoSismicos() {
  if (this.eventosSismicos != null && !this.eventosSismicos.isEmpty()) {
   this.eventosSismicos.sort(Comparator.comparing(EventoSismico::getFechaHoraOcurrencia));
  }
}

public void tomarSeleccionEventoSismico(EventoSismico evento) {
  this.eventoSeleccionado = evento;
 bloquearEvento();
 }

 public Estado buscarEstadoBloqueado() {
return estadoRepository.findByNombre("Bloqueado en revisión");
}

 public LocalDateTime tomarFechaHoraActual() {
 return LocalDateTime.now();
 }

 public Empleado buscarEmpleadoLogueado() {
   if (this.sesionActual != null) {
      return this.sesionActual.obtenerUsuarioLogueado();
   }
   Empleado empleadoSimulado = new Empleado();
   empleadoSimulado.setNombre("Empleado Simulado");
   return empleadoSimulado;
}

public void bloquearEvento() {
 this.fechaHoraActual = tomarFechaHoraActual();
 this.punteroBloqueadoEnRevision = buscarEstadoBloqueado();
  if (this.punteroBloqueadoEnRevision == null) {
  throw new RuntimeException("No se encontró estado de bloqueo en revisión.");
  }
  this.punteroEmpleado = buscarEmpleadoLogueado();
  this.eventoSeleccionado.revisar(fechaHoraActual, punteroBloqueadoEnRevision, punteroEmpleado);
   buscarDatosSismicos();
}

public void buscarDatosSismicos() {
  List<SerieTemporal> seriesTemporales = eventoSeleccionado.getSeriesTemporales();

  List<Map<String, Object>> resultado = new ArrayList<>();
 for (SerieTemporal serieTemporal : seriesTemporales) {
    Sismografo sismografo = serieTemporal.getSismografo();
    if (sismografo != null) {
      String estacion = sismografo.getDatosSismografo();

      for (MuestraSismica muestra : serieTemporal.getMuestrasSismicas()) {
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
    this.datosParaFrontend = resultado;
    llamarCasoDeUsoGenerarSismograma();
}

    public List<EventoSismico> getEventosSismicosPendientesCompletos() {
    return this.eventosSismicos; 
    }

    public List<Map<String, Object>> getDatosParaFrontend() {
    return this.datosParaFrontend;
    }
}
}