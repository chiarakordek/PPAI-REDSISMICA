package tpi.diseno.sismos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tpi.diseno.sismos.model.DetalleMuestraSismica;

@Repository
public interface DetalleMuestraSismicaRepository extends JpaRepository<DetalleMuestraSismica, Long> {
}
