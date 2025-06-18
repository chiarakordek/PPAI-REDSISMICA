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

    public Estado(){

    }
    public Estado(String ambito, String nombreEstado){
        this.ambito = ambito;
        this.nombreEstado = nombreEstado;
    }
    public Long getId() {
    return this.id;
    }   
    public String getAmbito() {
        return this.ambito;
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
    
    public boolean esAutodetectado(){
        if(this.nombreEstado == "Autodetectado"){
            return true;
        }
        return false;
    }
    public boolean esPendiente(){
        if(this.nombreEstado == "Pendiente"){
            return true;
        }
        return false;
    }
    public boolean esAmbitoEventoSismico(){
        if(this.ambito == "EventoSismico"){
            return true;
        }
        return false;
    }
    public boolean esBloqueadoEnRevision(){
        if(this.nombreEstado == "BloqueadoEnRevision"){
            return true;
        }
        return false;
    }
    public boolean esRechazado(){
        if(this.nombreEstado == "Rechazado"){
            return true;
        }
        return false;
    }
    
}
