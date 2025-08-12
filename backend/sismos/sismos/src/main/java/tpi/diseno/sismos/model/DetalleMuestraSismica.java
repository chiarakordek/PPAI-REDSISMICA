package tpi.diseno.sismos.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import tpi.diseno.sismos.repository.TipoDeDatoRepository;
import java.util.Map;

import java.util.HashMap;
import java.util.List;

@Entity
public class DetalleMuestraSismica {
//ATRIBUTOS
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double valor;
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

/////metodos del negocio
/** *
     * Devuelve el valor registrado en esta muestra. 
     */
    public Map<String, Object> getDatosDetalleMuestra() {
        return Map.of(
            "Valor", this.valor,
            "Tipo de dato", buscarTipoDeDato()
        );
    }

    public String buscarTipoDeDato() {
        return this.tipoDeDato != null ? this.tipoDeDato.getDenominacion() : "Tipo desconocido";
    }
}