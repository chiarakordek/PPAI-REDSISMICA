package tpi.diseno.sismos.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tpi.diseno.sismos.model.EstacionSismologica;
import tpi.diseno.sismos.model.Sismografo;
import tpi.diseno.sismos.repository.EstacionSismologicaRepository;
import tpi.diseno.sismos.repository.SismografoRepository;

@Service
public class SismografoService {

    @Autowired
    private SismografoRepository sismografoRepository;

    @Autowired
    private EstacionSismologicaRepository estacionSismologicaRepository;

    

    public List<Sismografo> getTodos() {
        return sismografoRepository.findAll();
    }

    public Optional<Sismografo> getPorId(Long id) {
        return sismografoRepository.findById(id);
    }

    //Metodos
    public Sismografo guardar(Sismografo sismografo) {
        if (sismografo.getEstacionSismologica() != null &&
            sismografo.getEstacionSismologica().getId() != null) {

            Optional<EstacionSismologica> estacionOpt = estacionSismologicaRepository
                .findById(sismografo.getEstacionSismologica().getId());

            if (estacionOpt.isPresent()) {
                sismografo.setEstacionSismologica(estacionOpt.get());
            } else {
                throw new RuntimeException("Estación no encontrada con ID: " + sismografo.getEstacionSismologica().getId());
            }
        }
        return sismografoRepository.save(sismografo);
    }

    public String getDatosDeSismografo(Long id) {
        Optional<Sismografo> s = sismografoRepository.findById(id);
        if (s.isPresent()) {
            Sismografo sis = s.get();
            EstacionSismologica est = sis.getEstacionSismologica();
            // Asumiendo que est está cargado completo, construyo el string
            return sis.getDatosSismografo() + " - Estación: " + est.getNombre() + ", Código: " + est.getCodigoEstacion();
        }
        return "No existe el sismógrafo";
    }
}
