// Archivo completo, final y corregido para: model/GestorRegistrarResultadoRevisionManual.java

package tpi.diseno.sismos.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tpi.diseno.sismos.repository.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GestorRegistrarResultadoRevisionManual {

    // --- Atributos ---
    private List<EventoSismico> eventosSismicos;
    private EventoSismico eventoSeleccionado;
    
    @Autowired
    private EventoSismicoRepository eventoSismicoRepository;
    @Autowired
    private EstadoRepository estadoRepository;

    // Constructor para la inyección de dependencias
    public GestorRegistrarResultadoRevisionManual(EventoSismicoRepository eventoSismicoRepository, EstadoRepository estadoRepository) {
        this.eventoSismicoRepository = eventoSismicoRepository;
        this.estadoRepository = estadoRepository;
        this.eventosSismicos = new ArrayList<>();
    }

    // --- Métodos de la Lógica de Negocio ---
    
    public List<EventoSismico> getEventosSismicosPendientesCompletos() {
        return eventoSismicoRepository.findEventosPendientes();
    }

    // Este método ya no le pide a EventoSismico que haga el trabajo.
    // Simplemente devuelve los datos que ya tenemos.
    public List<Map<String, String>> getDatosEventosSismicos() {
        List<Map<String, String>> listaDeDatos = new ArrayList<>();
        if (this.eventosSismicos != null) {
            for (EventoSismico evento : this.eventosSismicos) {
                Map<String, String> datos = new HashMap<>();
                datos.put("id", evento.getId().toString());
                datos.put("fechaHora", evento.getFechaHoraOcurrencia().toString());
                datos.put("magnitud", evento.getMagnitud().toString());
                listaDeDatos.add(datos);
            }
        }
        return listaDeDatos;
    }

    public void tomarSeleccionEventoSismico(EventoSismico evento) {
        this.eventoSeleccionado = evento;
        this.bloquearEvento(); // Llamamos al método para cambiar el estado
    }
    

    // La lógica de cambiar el estado y guardar está aquí, en el Gestor.
    public void bloquearEvento(){
        if (this.eventoSeleccionado == null) return;

        Estado estadoBloqueado = buscarEstado("BloqueadoEnRevision"); // Busca el estado correcto
        if (estadoBloqueado == null) {
            throw new RuntimeException("No se encontró el estado 'BloqueadoEnRevision'.");
        }

        // El Gestor modifica el estado del evento
        this.eventoSeleccionado.setEstado(estadoBloqueado);
        
        // El Gestor le pide al repositorio que guarde el cambio en la base de datos
        eventoSismicoRepository.save(this.eventoSeleccionado);
    }
    

    // La lógica de rechazar y guardar está aquí.
    public void rechazarEvento(){
        if (this.eventoSeleccionado == null) return;
        
        Estado estadoRechazado = buscarEstado("Rechazado");
        if (estadoRechazado == null) {
            throw new RuntimeException("No se encontró el estado 'Rechazado'.");
        }
        
        this.eventoSeleccionado.setEstado(estadoRechazado);
        eventoSismicoRepository.save(this.eventoSeleccionado);
    }

    // Método de ayuda para buscar estados
    private Estado buscarEstado(String nombre) {
        return estadoRepository.findByNombreEstado(nombre).orElse(null);
    }

    // ... (Puedes añadir el resto de tus métodos aquí, asegurándote de que no llamen
    // a métodos que no existen en las clases de modelo como EventoSismico)
}