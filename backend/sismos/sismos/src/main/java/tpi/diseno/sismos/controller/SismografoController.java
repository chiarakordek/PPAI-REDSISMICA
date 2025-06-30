package tpi.diseno.sismos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import tpi.diseno.sismos.model.Sismografo;
import tpi.diseno.sismos.service.SismografoService;

@RestController
@RequestMapping("/sismografos")
public class SismografoController {

    @Autowired
    private SismografoService sismografoService;

    @GetMapping("/{id}/datos")
    public String getDatos(@PathVariable Long id) {
        return sismografoService.getDatosDeSismografo(id);
    }

    @GetMapping("/{id}/es-mi-sismografo")
    public boolean esMiSismografo(@PathVariable Long id, @RequestParam Integer nroSerie) {
        return sismografoService.sosMiSismografo(id, nroSerie);
    }
    @PostMapping
    public ResponseEntity<Sismografo> crearSismografo(@RequestBody Sismografo sismografo) {
        Sismografo creado = sismografoService.guardar(sismografo);
        return new ResponseEntity<>(creado, HttpStatus.CREATED);
    }
}