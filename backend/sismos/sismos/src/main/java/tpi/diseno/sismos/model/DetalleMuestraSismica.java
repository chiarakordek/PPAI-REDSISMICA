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
        String tipoDeDato = this.buscarTipoDeDato();
        Double valor = this.getValor();
        MuestraSismica muestraSismica = this.getMuestraSismica();
        return new DetalleMuestraSismica(valor, muestraSismica, this.tipoDeDato);
        
    }

    /**
     * Devuelve el tipo de dato al que corresponde esta muestra. 
     */
    public String buscarTipoDeDato() {
        List<TipoDeDato> tiposDeDatos = tipoDeDatoRepository.findAll();
        for(TipoDeDato tipoDeDato : tiposDeDatos) {
            if (tipoDeDato.getId().equals(this.tipoDeDato.getId())) {
                return tipoDeDato.getDenominacion();
            }
        }
        return "Tipo de dato no encontrado";
    
    }
}