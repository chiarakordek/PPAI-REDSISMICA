package tpi.diseno.sismos.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tpi.diseno.sismos.dto.EstacionSismologicaDTO;
import tpi.diseno.sismos.model.EstacionSismologica;
import tpi.diseno.sismos.repository.EstacionSismologicaRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EstacionSismologicaService {

    private final EstacionSismologicaRepository estacionRepository;

    @Autowired
    public EstacionSismologicaService(EstacionSismologicaRepository estacionRepository) {
        this.estacionRepository = estacionRepository;
    }

    /**
     * Busca todas las estaciones y las convierte a su DTO correspondiente.
     * @return Lista de EstacionSismologicaDTO.
     */
    public List<EstacionSismologicaDTO> obtenerTodasLasEstaciones() {
        List<EstacionSismologica> estaciones = estacionRepository.findAll();
        
        return estaciones.stream()
                .map(EstacionSismologica::getDatosEstacion) // Llama al método que devuelve el DTO
                .collect(Collectors.toList());
    }

    /**
     * --- MÉTODO AÑADIDO (CORRECCIÓN 1) ---
     * Guarda una nueva estación sismológica en la base de datos.
     * @param estacion El objeto EstacionSismologica a guardar.
     * @return El objeto guardado, ahora con su ID generado.
     */
    public EstacionSismologica guardarEstacion(EstacionSismologica estacion) {
        return estacionRepository.save(estacion);
    }

    /**
     * --- MÉTODO AÑADIDO (CORRECCIÓN 2) ---
     * Busca una única estación por su ID y devuelve sus datos en un DTO.
     * @param id El ID de la estación a buscar.
     * @return El DTO con los datos de la estación encontrada.
     */
    public EstacionSismologicaDTO getDatosEstacion(Long id) {
        // Busca la estación por ID. Si no la encuentra, lanza una excepción.
        EstacionSismologica estacion = estacionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Estación no encontrada con ID: " + id));
        
        // Llama al método del modelo para obtener su DTO.
        return estacion.getDatosEstacion();
    }
}