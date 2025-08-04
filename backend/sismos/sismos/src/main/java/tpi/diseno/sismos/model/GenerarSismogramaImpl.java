package tpi.diseno.sismos.model;

import org.springframework.stereotype.Service;

@Service 
public class GenerarSismogramaImpl implements GenerarSismograma {
    @Override
    public String include(Long idEvento) {
        return "Ubicación del evento: " + idEvento; 
    }
}