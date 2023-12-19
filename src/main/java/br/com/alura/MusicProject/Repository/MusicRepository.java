package br.com.alura.MusicProject.Repository;

import br.com.alura.MusicProject.Model.Classes.Artist;
import br.com.alura.MusicProject.Model.Classes.Ensemble;
import br.com.alura.MusicProject.Model.Enums.Instruments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MusicRepository extends JpaRepository<Ensemble, Long> {

    Optional<Ensemble> findByNameContainingIgnoreCase(String name);
}
