package tpi.diseno.sismos.service;

import org.springframework.stereotype.Service;
import tpi.diseno.sismos.model.Sesion;
import tpi.diseno.sismos.model.Usuario;
import tpi.diseno.sismos.repository.SesionRepository;
import tpi.diseno.sismos.repository.UsuarioRepository;

@Service
public class AuthService {
    private final UsuarioRepository usuarioRepository;
    private final SesionRepository sesionRepository;

    public AuthService(UsuarioRepository usuarioRepository, SesionRepository sesionRepository) {
        this.usuarioRepository = usuarioRepository;
        this.sesionRepository = sesionRepository;
    }

    public Sesion autenticarUsuario(String nombreUsuario) {
        // Agrega logs para depuración
        System.out.println("Buscando usuario: " + nombreUsuario);
        
        Usuario usuario = usuarioRepository.findById(nombreUsuario)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado en la base de datos"));
        
        System.out.println("Usuario encontrado: " + usuario.getNombre());
        
        Sesion sesion = new Sesion(usuario);
        return sesionRepository.save(sesion);
    }
}