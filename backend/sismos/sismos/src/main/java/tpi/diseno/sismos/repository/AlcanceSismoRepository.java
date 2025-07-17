// Crea este archivo en: src/main/java/tpi/diseno/sismos/repository/AlcanceSismoRepository.java
package tpi.diseno.sismos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tpi.diseno.sismos.model.AlcanceSismo;

@Repository
public interface AlcanceSismoRepository extends JpaRepository<AlcanceSismo, Long> {
}