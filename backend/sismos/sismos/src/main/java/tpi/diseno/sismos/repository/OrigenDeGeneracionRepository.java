// Crea este archivo en: src/main/java/tpi/diseno/sismos/repository/OrigenDeGeneracionRepository.java
package tpi.diseno.sismos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tpi.diseno.sismos.model.OrigenDeGeneracion;

@Repository
public interface OrigenDeGeneracionRepository extends JpaRepository<OrigenDeGeneracion, Long> {
}