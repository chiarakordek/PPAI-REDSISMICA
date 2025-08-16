package tpi.diseno.sismos.service;

import java.time.LocalDateTime;
import org.springframework.stereotype.Service;
import tpi.diseno.sismos.model.Sesion;
import tpi.diseno.sismos.model.Usuario;
import tpi.diseno.sismos.repository.SesionRepository;
import tpi.diseno.sismos.repository.UsuarioRepository;
import java.time.LocalDateTime; // <-- Importante añadir esta línea

@Service
public class AuthService {
    private final UsuarioRepository usuarioRepository;
    private final SesionRepository sesionRepository;

    // El constructor para la inyección de dependencias está perfecto, no se toca.
    public AuthService(UsuarioRepository usuarioRepository, SesionRepository sesionRepository) {
        this.usuarioRepository = usuarioRepository;
        this.sesionRepository = sesionRepository;
    }

    public Sesion autenticarUsuario(String nombreUsuario) {
        // Agrego logs para depuración
        System.out.println("Buscando usuario: " + nombreUsuario);
        
        // La búsqueda del usuario está perfecta, no se toca.
        Usuario usuario = usuarioRepository.findByNombre(nombreUsuario) // Asumo que tienes findByNombre, si no, usa findById si el ID es el nombre
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado en la base de datos"));
        
        System.out.println("Usuario encontrado: " + usuario.getNombre());
        
        // --- INICIO DE LA CORRECCIÓN ---
        
        // 1. Crear una instancia de Sesion usando el constructor vacío.
        Sesion sesion = new Sesion();
        
        // 2. Establecer los valores necesarios usando los métodos 'set'.
        sesion.setUsuario(usuario);
        sesion.setFechaInicio(LocalDateTime.now()); // Es buena práctica establecer la fecha de inicio.
        
        // --- FIN DE LA CORRECCIÓN ---

        // Guardar la sesión ya configurada en la base de datos.
        return sesionRepository.save(sesion);
    }
}