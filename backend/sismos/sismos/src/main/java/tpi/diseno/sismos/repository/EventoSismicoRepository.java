// Reemplaza el contenido completo de tu archivo EventoSismicoRepository.java

package tpi.diseno.sismos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import tpi.diseno.sismos.model.EventoSismico;
import java.util.List;

@Repository
public interface EventoSismicoRepository extends JpaRepository<EventoSismico, Long> {

    // --- MÉTODO AÑADIDO ---
    // Esta consulta busca directamente en la base de datos los eventos
    // cuyo estado actual (`estadoActual`) tiene el campo `nombreEstado` igual a 'PendienteDeRevision'.
    // Es la forma más eficiente de implementar los pasos 5 y 6 del DDS.
    @Query("SELECT e FROM EventoSismico e WHERE e.estadoActual.nombreEstado = 'PendienteDeRevision'")
    List<EventoSismico> findEventosPendientes();
    
}