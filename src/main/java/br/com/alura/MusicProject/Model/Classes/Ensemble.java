package br.com.alura.MusicProject.Model.Classes;

import br.com.alura.MusicProject.Model.Enums.Styles;
import br.com.alura.MusicProject.Model.Enums.TypesOfGroup;
import br.com.alura.MusicProject.Service.Service;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
@Entity
public class Ensemble {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(unique = true)
    private String name;
    @Enumerated(EnumType.STRING)
    private Styles styles;
    @Enumerated(EnumType.STRING)
    private TypesOfGroup typesOfGroup;
    @OneToMany(mappedBy = "ensemble",cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Artist> members = new ArrayList<>();
    @OneToMany(mappedBy = "ensemble",cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Album> albums = new ArrayList<>();

    public Ensemble(String name, Styles styles, TypesOfGroup typesOfGroup){
        this.name = name;
        this.typesOfGroup = typesOfGroup;
        this.styles = styles;
    }

    public Ensemble() {}

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Styles getStyles() {
        return styles;
    }

    public void setStyles(Styles styles) {
        this.styles = styles;
    }

    public TypesOfGroup getTypesOfGroup() {
        return typesOfGroup;
    }

    public void setTypesOfGroup(TypesOfGroup typesOfGroup) {
        this.typesOfGroup = typesOfGroup;
    }

    public List<Artist> getMembers() {
        return members;
    }

    public void setMembers(List<Artist> members) {
        this.members = members;
    }

    public List<Album> getAlbums() {
        return albums;
    }

    public void setAlbums(List<Album> albums) {
        this.albums = albums;
    }

    @Override
    public String toString() {
        return String.format("""
                ==============================
                Nome: %s
                Tipo de grupo: %s
                Estilo musical: %s
                Membros: %s
                Albuns: %s
                ==============================
                
                """,getName(),getTypesOfGroup(),getStyles(),
                Service.listName(members, Artist::getName),
                Service.listName(albums, Album::getName));
    }
}
