package tpi.diseno.sismos.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

@Entity
public class Usuario {

    @Id
    private String nombre;

/** Empleado asociado a este usuario. */
    @OneToOne
    private Empleado empleado;
}
