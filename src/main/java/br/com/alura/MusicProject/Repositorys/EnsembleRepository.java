package br.com.alura.MusicProject.Repositorys;

import br.com.alura.MusicProject.Model.Classes.Album;
import br.com.alura.MusicProject.Model.Classes.Ensemble;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface EnsembleRepository extends JpaRepository<Ensemble, Long>{

    Optional<Ensemble> findByNameContainingIgnoreCase(String name);
    @Query("SELECT a FROM Album a INNER JOIN a.ensemble e " +
            "WHERE e.name ILIKE %:ensemble% " +
            "AND a.name ILIKE %:album% " +
            "GROUP BY a.id, a.name, a.release")
    Optional<Album> findAlbum(String ensemble, String album);
    @Query("SELECT a from Album a INNER JOIN a.ensemble e WHERE e.name ILIKE %:ensembleName%" +
            " GROUP BY a.id, a.name, a.release")
    List<Album> findAlbumByEnsembleName(String ensembleName);
}
