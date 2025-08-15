package tpi.diseno.sismos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tpi.diseno.sismos.model.Estado;
import java.util.Optional;

@Repository
// La clave primaria es de tipo Long. (id)
public interface EstadoRepository extends JpaRepository<Estado, Long> {
    
    // El método para buscar por nombre sigue siendo muy útil.
    Optional<Estado> findByNombreEstado(String nombreEstado);
    
}
