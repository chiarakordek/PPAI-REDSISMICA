package tpi.diseno.sismos.model;

import org.springframework.stereotype.Service;

@Service // ¡Esta anotación es clave!
public class GenerarSismogramaImpl implements GenerarSismograma {
    @Override
    public String include(Long idEvento) {
        // Aquí va tu lógica para generar el mapa (puedes dejarlo así por ahora)
        return "Ubicación del evento: " + idEvento; 
    }
}