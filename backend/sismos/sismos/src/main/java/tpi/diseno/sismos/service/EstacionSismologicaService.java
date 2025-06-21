package tpi.diseno.sismos.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tpi.diseno.sismos.model.EstacionSismologica;
import tpi.diseno.sismos.repository.EstacionSismologicaRepository;

@Service
public class EstacionSismologicaService {

    @Autowired
    private EstacionSismologicaRepository repository;

    public EstacionSismologica guardarEstacion(EstacionSismologica estacion) {
        return repository.save(estacion);
    }

    public List<EstacionSismologica> obtenerTodas() {
        return repository.findAll();
    }

    public Optional<EstacionSismologica> obtenerPorId(Long id) {
        return repository.findById(id);
    }

    public String getDatosEstacion(Long id) {
        Optional<EstacionSismologica> estacionOpt = repository.findById(id);
        return estacionOpt.map(EstacionSismologica::getDatosEstacion).orElse("Estación no encontrada");
    }
}