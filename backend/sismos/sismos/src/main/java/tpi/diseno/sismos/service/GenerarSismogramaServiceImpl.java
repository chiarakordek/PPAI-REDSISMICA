package tpi.diseno.sismos.service;

import org.springframework.stereotype.Service;

@Service
public class GenerarSismogramaServiceImpl implements GenerarSismogramaService {

    /**
     * Este método es la implementación del caso de uso incluido (include).
     */
    @Override
    public void generarSismograma() {
        // Acá iría la lógica completa del CU-18.
        System.out.println("--- Ejecutando Caso de Uso Incluido: Generar Sismograma (CU-18) ---");
    }
}