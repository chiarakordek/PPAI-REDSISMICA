package tpi.diseno.sismos.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Estado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String ambito;
    private String nombreEstado;

    /**Constructor */
    public Estado() {
    }

    public Estado(String ambito, String nombreEstado) {
        this.ambito = ambito;
        this.nombreEstado = nombreEstado;
    }

    //////////////// Getters y Setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getAmbito() {
        return ambito;
    }
    public void setAmbito(String ambito) {
        this.ambito = ambito;
    }

    public String getNombreEstado() {
        return nombreEstado;
    }
    public void setNombreEstado(String nombreEstado) {
        this.nombreEstado = nombreEstado;
    }

    public boolean esAmbitoEventoSismico(){
        return "EventoSismico".equalsIgnoreCase(ambito);
    }

    public boolean esAutodetectado(){
        return "AutoDetectado".equalsIgnoreCase(nombreEstado); 
    }

    public boolean esPendienteDeRevision(){
        return "PendienteDeRevision".equalsIgnoreCase(nombreEstado); 
    }

    public boolean esRechazado(){
        return "Rechazado".equalsIgnoreCase(nombreEstado);
    }

    public boolean esBloqueadoEnRevision(){
        return "BloqueadoEnRevision".equalsIgnoreCase(ambito);
    }
}