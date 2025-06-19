package tpi.diseno.sismos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tpi.diseno.sismos.model.Sismografo;

@Repository
public interface SismografoRepository extends JpaRepository<Sismografo, Long> {
}
