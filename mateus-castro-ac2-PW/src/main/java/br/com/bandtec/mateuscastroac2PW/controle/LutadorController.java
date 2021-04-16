package br.com.bandtec.mateuscastroac2PW.controle;

import br.com.bandtec.mateuscastroac2PW.dominio.Golpe;
import br.com.bandtec.mateuscastroac2PW.dominio.Lutador;
import br.com.bandtec.mateuscastroac2PW.repositorio.LutadorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RequestMapping("/lutadores")
@RestController
public class LutadorController {

    @Autowired
    private LutadorRepository repository;

    @PostMapping
    public ResponseEntity postLutadores(@RequestBody @Valid Lutador novoLutador){
        repository.save(novoLutador);
        return ResponseEntity.status(201).build();
    }

    @GetMapping
    public ResponseEntity getLutadores(){
        List<Lutador> lutadores = repository.findByOrderByForcaGolpe();

        repository.findAll();
        return ResponseEntity.status(200).body(lutadores);
    }

    @GetMapping("/contagem-vivos")
    public ResponseEntity getLutadoresVivos(){
        List<Lutador> lutadores = repository.findAllByVivoIsTrue();
        return ResponseEntity.status(200).body(lutadores.size());
    }

    @PostMapping("/{id}/concentrar")
    public ResponseEntity postConcetraLutador(@PathVariable int id){
        Lutador lutador = repository.getOne(id);

        return ResponseEntity.status(200).body(lutador);
    }

    @PostMapping("/golpe")
    public ResponseEntity postGolpeLutador(@RequestBody Golpe golpe){
        Lutador lutadorBate = repository.getOne(golpe.getIdBate());
        Lutador lutadorApanha = repository.getOne(golpe.getIdApanha());
        List<Lutador> lutadores = new ArrayList<>();



        Double pancada = lutadorBate.getForcaGolpe();
        Double vida = lutadorApanha.getVida();

        double novaVida = vida-pancada;

        if (novaVida < 0.0){
            novaVida = 0.0;

            lutadorApanha.setVida(novaVida);
            return ResponseEntity.status(200).body("A vida de " + lutadorApanha.getNome() + " chegou à 0. Está morto.");
        } else {
            lutadorApanha.setVida(novaVida);
            lutadores.add(lutadorBate);
            lutadores.add(lutadorApanha);
            return ResponseEntity.status(200).body(lutadores);
        }


    }

}
