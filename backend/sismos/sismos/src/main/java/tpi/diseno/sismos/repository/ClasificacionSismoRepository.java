// Crea este archivo en: src/main/java/tpi/diseno/sismos/repository/ClasificacionSismoRepository.java
package tpi.diseno.sismos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tpi.diseno.sismos.model.ClasificacionSismo;

@Repository
public interface ClasificacionSismoRepository extends JpaRepository<ClasificacionSismo, Long> {
}