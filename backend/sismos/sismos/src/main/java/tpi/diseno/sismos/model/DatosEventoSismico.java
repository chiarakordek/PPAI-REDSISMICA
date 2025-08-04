package tpi.diseno.sismos.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DatosEventoSismico {
    
    private Long id;
    private String fechaHora;
    private String ubicacion;
    private String ubicacionHipocentro; 
    private double magnitud;
    private String alcance;
    private String estado;

    public DatosEventoSismico() {}

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getFechaHora() { return fechaHora; }
    public void setFechaHoraFromLocalDateTime(LocalDateTime fecha) {
        if (fecha != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
            this.fechaHora = fecha.format(formatter);
        } else {
            this.fechaHora = "No definida";
        }
    }
    public String getUbicacion() { return ubicacion; }
    public void setUbicacionFromCoordenadas(Double lat, Double lon) {
        if (lat != null && lon != null) {
            this.ubicacion = String.format(Locale.US, "%.2f°S, %.2f°W", Math.abs(lat), Math.abs(lon));
        } else {
            this.ubicacion = "No definida";
        }
    }
    
    // --- NUEVO MÉTODO PARA EL HIPOCENTRO ---
    public String getUbicacionHipocentro() { return ubicacionHipocentro; }
    public void setUbicacionHipocentroFromCoordenadas(Double lat, Double lon) {
        if (lat != null && lon != null) {
            this.ubicacionHipocentro = String.format(Locale.US, "%.2f°S, %.2f°W", Math.abs(lat), Math.abs(lon));
        } else {
            this.ubicacionHipocentro = "No disponible";
        }
    }
    
    public double getMagnitud() { return magnitud; }
    public void setMagnitud(double magnitud) { this.magnitud = magnitud; }
    public String getAlcance() { return alcance; }
    public void setAlcance(String alcance) { this.alcance = alcance; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}