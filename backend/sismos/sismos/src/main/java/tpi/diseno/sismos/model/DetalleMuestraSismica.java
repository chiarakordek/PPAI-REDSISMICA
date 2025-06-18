package tpi.diseno.sismos.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

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

    public DetalleMuestraSismica() {}

    public DetalleMuestraSismica(Double valor, MuestraSismica muestraSismica, TipoDeDato tipoDeDato) {
        this.valor = valor;
        this.muestraSismica = muestraSismica;
        this.tipoDeDato = tipoDeDato;
    //GETS y SETS
    }
    public Long getId(){
        return this.id;
    }

    public Double getValor() { return this.valor;}

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
    //Metodos Diag Clases
    public DetalleMuestraSismica getDatosDetalleMuestra(){return this;}
    
    public TipoDeDato buscarTipoDeDato(){ return this.TipoDeDato; }


}
