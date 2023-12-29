package br.com.alura.MusicProject.Model.Classes;

import jakarta.persistence.*;

@Entity
public class Music {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private double time;
    @ManyToOne
    private Album album;
    public Music(String name, double time){
        this.name = name;
        this.time = time;
    }

    public Music() {}

    public int getId() {return id;}

    public String getName() {return name;}

    public void setName(String name) {this.name = name;}

    public double getTime() {return time;}

    public void setTime(double time) {this.time = time;}

    public Album getAlbum() {return album;}

    public void setAlbum(Album album) {this.album = album;}

    @Override
    public String toString() {
        return String.format("""
                ==============================
                Nome: %s
                Tempo: %.1fs
                Album: %s
                ==============================
                """,getName(),getTime(),getAlbum().getName());
    }
}
