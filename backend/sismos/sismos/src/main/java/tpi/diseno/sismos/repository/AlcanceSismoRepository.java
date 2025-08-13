package tpi.diseno.sismos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tpi.diseno.sismos.model.AlcanceSismo;

@Repository
public interface AlcanceSismoRepository extends JpaRepository<AlcanceSismo, Long> {
}