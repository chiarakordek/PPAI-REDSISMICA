package tpi.diseno.sismos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tpi.diseno.sismos.model.SerieTemporal;

@Repository
public interface SerieTemporalRepository extends JpaRepository<SerieTemporal, Long> {
}
