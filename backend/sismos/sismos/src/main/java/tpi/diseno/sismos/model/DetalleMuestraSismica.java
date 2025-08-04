package tpi.diseno.sismos.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import java.util.Map;

@Entity
public class DetalleMuestraSismica {

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

    /**Constructor (Vacío)*/
    public DetalleMuestraSismica() {}

    /**Constructor */
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


    //Devuelve el valor registrado en esta muestra. 
    public Map<String, Object> getDatosDetalleMuestra() {
        //Para cada detalle se obtiene el valor y el tipo de dato al que pertenece
        return Map.of(
            "Valor", this.valor,
            "Tipo de dato", buscarTipoDeDato()
        );
    }

    //Obtiene el tipo de dato para el valor del detalle
    public String buscarTipoDeDato() {
        //Si el tipo de dato es null, se informa que no se reconoce el tipo de dato.
        return this.tipoDeDato != null ? this.tipoDeDato.getDenominacion() : "Tipo desconocido";
    }
}