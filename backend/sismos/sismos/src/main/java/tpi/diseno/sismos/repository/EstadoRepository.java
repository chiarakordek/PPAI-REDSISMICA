// Contenido para el archivo: EstadoRepository.java

package tpi.diseno.sismos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tpi.diseno.sismos.model.Estado;

import java.util.Optional;

@Repository
public interface EstadoRepository extends JpaRepository<Estado, Long> {

    // Busca un estado por su campo "nombreEstado".
    Optional<Estado> findByNombreEstado(String nombreEstado);
}