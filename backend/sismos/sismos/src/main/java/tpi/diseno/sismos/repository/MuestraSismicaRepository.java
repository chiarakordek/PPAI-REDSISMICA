package tpi.diseno.sismos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tpi.diseno.sismos.model.MuestraSismica;

@Repository
public interface MuestraSismicaRepository extends JpaRepository<MuestraSismica, Long> {
}
