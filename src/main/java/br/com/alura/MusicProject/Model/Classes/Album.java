package br.com.alura.MusicProject.Model.Classes;

import br.com.alura.MusicProject.Service.Service;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
@Entity
public class Album {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private LocalDate release;
    @OneToMany(mappedBy = "album", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Music> musics = new ArrayList<>();
    @ManyToOne
    private Ensemble ensemble;

    public Album(String name, LocalDate release){
        this.name = name;
        this.release = release;
    }

    public Album() {}

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getRelease() {
        return release;
    }

    public void setRelease(LocalDate release) {
        this.release = release;
    }

    public List<Music> getMusics() {
        return musics;
    }

    public void setMusics(List<Music> musics) {
        this.musics = musics;
    }

    public Ensemble getEnsemble() {
        return ensemble;
    }

    public void setEnsemble(Ensemble ensemble) {
        this.ensemble = ensemble;
    }

    @Override
    public String toString() {
        return String.format("""
                              Album
                =================================
                Nome: %s
                Conjunto: %s
                Data: %s
                Musicas: %s
                =================================
                
                """,getName(),getEnsemble().getName(),getRelease(),
                Service.listName(musics, Music::getName));
    }
}
