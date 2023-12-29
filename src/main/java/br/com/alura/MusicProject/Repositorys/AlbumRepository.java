package br.com.alura.MusicProject.Repositorys;

import br.com.alura.MusicProject.Model.Classes.Album;
import br.com.alura.MusicProject.Model.Classes.Music;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AlbumRepository extends JpaRepository<Album, Long> {
    @Query("SELECT m FROM Music m WHERE m.name ILIKE %:musicName%")
    List<Music> findMusic(String musicName);
}
