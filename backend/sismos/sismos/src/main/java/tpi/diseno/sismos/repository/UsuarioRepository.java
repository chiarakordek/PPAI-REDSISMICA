package tpi.diseno.sismos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tpi.diseno.sismos.model.Usuario;

import java.util.Optional;

@Repository
// --- CORRECCIÓN ---
// El segundo tipo genérico ahora es String, que es el tipo de la clave primaria.
public interface UsuarioRepository extends JpaRepository<Usuario, String> {

    // Este método sigue siendo útil si quieres buscar por nombre,
    // pero findById(nombre) ahora hará lo mismo.
    Optional<Usuario> findByNombre(String nombre);

}