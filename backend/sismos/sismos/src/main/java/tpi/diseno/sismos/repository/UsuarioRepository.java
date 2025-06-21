package tpi.diseno.sismos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tpi.diseno.sismos.model.Usuario;

import tpi.diseno.sismos.model.Empleado;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, String> {
    Optional<Usuario> findByEmpleado(Empleado empleado);
}
