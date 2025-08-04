// Archivo completo, final y con la consulta SQL nativa para: repository/EventoSismicoRepository.java

package tpi.diseno.sismos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import tpi.diseno.sismos.model.EventoSismico;
import java.util.List;

@Repository
public interface EventoSismicoRepository extends JpaRepository<EventoSismico, Long> {

    /**
     * Busca todos los eventos sísmicos cuyo estado actual es 'PendienteDeRevision'.
     * Esta consulta utiliza SQL nativo para asegurar la máxima compatibilidad y precisión,
     * uniendo la tabla 'evento_sismico' con la tabla 'estado' a través de la clave foránea.
     * 
     * @return una lista de objetos EventoSismico que están pendientes de revisión.
     */
    @Query(value = "SELECT es.* FROM evento_sismico es " +
                   "JOIN estado e ON es.estado_actual_id = e.id " +
                   "WHERE e.nombre_estado = 'PendienteDeRevision'", 
           nativeQuery = true)
    List<EventoSismico> findEventosPendientes();
    
}