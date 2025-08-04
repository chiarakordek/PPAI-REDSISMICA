package tpi.diseno.sismos.model;

import java.time.LocalDateTime;
import java.util.List;

public class DatosMuestraSismica {

    private LocalDateTime fechaHora;
    private List<DetalleMuestraSismica> detalles;

    public DatosMuestraSismica(LocalDateTime fechaHora, List<DetalleMuestraSismica> detalles) {
        this.fechaHora = fechaHora;
        this.detalles = detalles;
    }

    // Getters
    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public List<DetalleMuestraSismica> getDetalles() {
        return detalles;
    }
}

