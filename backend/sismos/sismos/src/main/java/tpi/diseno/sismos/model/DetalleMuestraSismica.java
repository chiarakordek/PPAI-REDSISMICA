package tpi.diseno.sismos.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import tpi.diseno.sismos.repository.TipoDeDatoRepository;
import java.util.List;

@Entity
public class DetalleMuestraSismica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double valor;
    private TipoDeDatoRepository tipoDeDatoRepository;

/** Muestra sísmica a la que pertenece este detalle. */
    @ManyToOne
    private MuestraSismica muestraSismica;

 /** Tipo de dato al que corresponde el valor registrado. */
    @ManyToOne
    private TipoDeDato tipoDeDato;

    /**Constructor */
    public DetalleMuestraSismica() {
    }

    public DetalleMuestraSismica(Double valor, MuestraSismica muestraSismica, TipoDeDato tipoDeDato) {
        this.valor = valor;
        this.muestraSismica = muestraSismica;
        this.tipoDeDato = tipoDeDato;
    }


    //////////////// Getters y Setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public Double getValor() {
        return valor;
    }
    public void setValor(Double valor) {
        this.valor = valor;
    }

    public MuestraSismica getMuestraSismica() {
        return muestraSismica;
    }
    public void setMuestraSismica(MuestraSismica muestraSismica) {
        this.muestraSismica = muestraSismica;
    }

    public TipoDeDato getTipoDeDato() {
        return tipoDeDato;
    }
    public void setTipoDeDato(TipoDeDato tipoDeDato) {
        this.tipoDeDato = tipoDeDato;
    }

/////metodos
/** *
     * Devuelve el valor registrado en esta muestra. 
     */
    public DetalleMuestraSismica getDatosDetalleMuestra() {
        // Evitás buscar en repo si ya tenés el tipo
        return this;
    }

    public String buscarTipoDeDato() {
        //Debería ser así ya que detalleMuestra ya conoce su tipo de dato, pero en el diagrama buscar todos
        //los tipos y valida si es el suyo. Con esta opcion evitamos usar el repositorio

        //return this.tipoDeDato != null ? this.tipoDeDato.getDenominacion() : "Tipo desconocido";

        // Buscamos el tipo de dato en el repositorio
        for (TipoDeDato tipo : tipoDeDatoRepository.findAll()) {
        if (tipo.getId().equals(this.tipoDeDato.getId())) {
            return tipo.getDenominacion();
        }
        }
        return "Tipo desconocido";
    }
}