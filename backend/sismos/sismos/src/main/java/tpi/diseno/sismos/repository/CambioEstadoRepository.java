package tpi.diseno.sismos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tpi.diseno.sismos.model.CambioEstado;

@Repository
public interface CambioEstadoRepository extends JpaRepository<CambioEstado, Long> {
}
