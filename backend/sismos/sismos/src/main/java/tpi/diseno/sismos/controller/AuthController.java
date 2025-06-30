package tpi.diseno.sismos.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tpi.diseno.sismos.dto.LoginRequest;
import tpi.diseno.sismos.model.Sesion;
import tpi.diseno.sismos.service.AuthService;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = {"http://localhost:5500", "http://127.0.0.1:5500"})
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            Sesion sesion = authService.autenticarUsuario(loginRequest.getNombreUsuario());
            
            if (sesion == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("message", "Usuario no encontrado"));
            }
            
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Login exitoso");
            response.put("usuario", sesion.getUsuario().getNombre());
            response.put("sesionId", sesion.getId());
            response.put("fechaInicio", sesion.getFechaInicio());
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(Map.of("message", "Error en el servidor: " + e.getMessage()));
        }
    }
}