package tpi.diseno.sismos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tpi.diseno.sismos.model.EventoSismico;

@Repository
public interface EventoSismicoRepository extends JpaRepository<EventoSismico, Long> {
}
