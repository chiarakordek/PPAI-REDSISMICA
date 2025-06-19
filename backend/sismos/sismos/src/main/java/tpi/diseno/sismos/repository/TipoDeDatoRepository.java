package tpi.diseno.sismos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tpi.diseno.sismos.model.TipoDeDato;

@Repository
public interface TipoDeDatoRepository extends JpaRepository<TipoDeDato, Long> {
}
