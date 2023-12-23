package br.com.alura.MusicProject.Model.Classes;

import br.com.alura.MusicProject.Model.Enums.Instruments;
import br.com.alura.MusicProject.Service.Service;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
@Entity
@Table(name = "Artists")
public class Artist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(unique = true)
    private String name;
    private int old;
    @ManyToOne
    private Ensemble ensemble;
    @Enumerated(EnumType.STRING)
    private List<Instruments> instruments = new ArrayList<>();

    public Artist(String name, int old, List<Instruments> instruments){
        this.name = name;
        this.instruments = instruments;
        this.old = old;
    }

    public Artist() {}

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOld() {
        return old;
    }

    public void setOld(int old) {
        this.old = old;
    }

    public Ensemble getGroup() {
        return ensemble;
    }

    public void setGroup(Ensemble ensemble) {
        this.ensemble = ensemble;
    }

    public List<Instruments> getInstruments() {
        return instruments;
    }

    public void setInstruments(List<Instruments> instruments) {
        this.instruments = instruments;
    }

    @Override
    public String toString() {

        return String.format("""
                            Artista
                =================================
                Nome: %s
                Idade: %d
                Instrumento: %s
                Grupo: %s
                =================================
                
                """,
                getName(),getOld(),
                Service.listName(instruments, Instruments::name),
                getGroup().getName());
    }
}
