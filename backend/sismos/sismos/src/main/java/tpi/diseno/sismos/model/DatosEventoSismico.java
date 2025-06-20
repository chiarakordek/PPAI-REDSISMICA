package tpi.diseno.sismos.model;

import java.time.LocalDateTime;


public class DatosEventoSismico {
    private LocalDateTime fechaHoraOcurrencia;
    private double latitud;
    private double longitud;
    private double latitudHipocentro;
    private double longitudHipocentro;
    private double valorMagnitud;

    // Constructor
    public DatosEventoSismico(LocalDateTime fechaHora, double latitud, double longitud,
                      double latitudHP, double longitudHP, double magnitud) {
        this.fechaHoraOcurrencia = fechaHora;
        this.latitud = latitud;
        this.longitud = longitud;
        this.latitudHipocentro = latitudHP;
        this.longitudHipocentro = longitudHP;
        this.valorMagnitud = magnitud;
    }

    // Getters y setters 
    public LocalDateTime getFechaHora() { return this.fechaHoraOcurrencia; }
    public void setFechaHora(LocalDateTime fechaHora) { this.fechaHoraOcurrencia = fechaHora; }

    public double getLatitud() { return latitud; }
    public void setLatitud(double latitud) { this.latitud = latitud; }

    public double getLongitud() { return longitud; }
    public void setLongitud(double longitud) { this.longitud = longitud; }

    public double getLatitudHP() { return this.latitudHipocentro; }
    public void setLatitudHP(double latitudHP) { this.latitudHipocentro = latitudHP; }

    public double getLongitudHP() { return longitudHipocentro; }
    public void setLongitudHP(double longitudHP) { this.longitudHipocentro = longitudHP; }

    public double getMagnitud() { return valorMagnitud; }
    public void setMagnitud(double magnitud) { this.valorMagnitud = magnitud; }
}
    

