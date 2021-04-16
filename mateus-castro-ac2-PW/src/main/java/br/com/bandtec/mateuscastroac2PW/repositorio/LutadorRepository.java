package br.com.bandtec.mateuscastroac2PW.repositorio;

import br.com.bandtec.mateuscastroac2PW.dominio.Lutador;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LutadorRepository extends JpaRepository<Lutador, Integer> {
    List<Lutador> findByOrderByForcaGolpe();
    List<Lutador> findAllByVivoIsTrue();
}
