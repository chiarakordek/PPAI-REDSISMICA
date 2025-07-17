package tpi.diseno.sismos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tpi.diseno.sismos.model.EstacionSismologica;

@Repository
public interface EstacionSismologicaRepository extends JpaRepository<EstacionSismologica, Long> {
}
