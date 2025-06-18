package tpi.diseno.sismos.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.ManyToOne;

import java.time.LocalDate;

@Entity
public class Sismografo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate fechaAdquisicion;
    private String identificadorSismografo;
    private Integer nroSerie;

/** Estación sismológica donde está instalado el sismógrafo. */
    @ManyToOne
    private EstacionSismologica estacionSismologica;
}
