package tpi.diseno.sismos.repository;

import tpi.diseno.sismos.model.Sesion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SesionRepository extends JpaRepository<Sesion, Long> {
    // Podríamos necesitar un método para encontrar la sesión activa actual
}