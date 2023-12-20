package br.com.alura.MusicProject.Main;


import br.com.alura.MusicProject.Model.Classes.Album;
import br.com.alura.MusicProject.Model.Classes.Artist;
import br.com.alura.MusicProject.Model.Classes.Ensemble;
import br.com.alura.MusicProject.Model.Classes.Music;
import br.com.alura.MusicProject.Model.Enums.Instruments;
import br.com.alura.MusicProject.Model.Enums.Styles;
import br.com.alura.MusicProject.Model.Enums.TypesOfGroup;
import br.com.alura.MusicProject.Repository.MusicRepository;
import br.com.alura.MusicProject.Service.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Main {
    private final Scanner WRITE = new Scanner(System.in);
    private final MusicRepository repository;
    public Service in = new Service();
    public Main (MusicRepository repository){this.repository = repository;}

    public void showMenu(){
        int option;
        do {
            System.out.print("""
                    ==============================================
                    1   - Registrar Artista solo
                    2   - Registrar Grupo
                    3   - Registrar Álbum
                                    
                    0   - Sair do programa
                    
                    Digite:\s""");
            option = WRITE.nextInt();
            WRITE.nextLine();
            switch (option){
                case 1:
                    addSoloArtist();
                    break;
                case 2:
                    newEnsemble();
                case 3:
                    newAlbum();
            }
        }while (option != 0);
    }

    //SOLO ARTIST:
    private void addSoloArtist(){
        Artist artist = newArtist();

        System.out.print("Estilo musical: ");
        var style = WRITE.nextLine();

        Ensemble ensemble = new Ensemble(artist.getName(), Styles.styles(style), TypesOfGroup.SOLO);
        List<Artist> artists = new ArrayList<>();
        artists.add(artist);
        ensemble.setMembers(artists);
        List<Ensemble> ensembles = new ArrayList<>();
        ensembles.add(ensemble);
        artist.setGroup(ensembles);

        repository.save(ensemble);

        Optional<Ensemble> foundGroup = repository.findByNameContainingIgnoreCase(ensemble.getName());
        foundGroup.ifPresent(repository::save);
    }
    //ENSEMBLE:
    private void newEnsemble(){
        System.out.print("nome: ");
        var name = WRITE.nextLine();
        Styles styles;
        while (true) {
            System.out.print("Estilo da banda: ");
            var style = WRITE.nextLine();
            if (in.inStyle(style)){
                styles = Styles.styles(style);
                break;
            }else {
                System.out.println("Estilo não registrado. Tente novamente.");
            }

        }
        TypesOfGroup typesOfGroup;
        while (true) {
            System.out.print("Tipo do grupo: ");
            var type = WRITE.nextLine();
            if(in.inTypePortuguese(type)){
                typesOfGroup = TypesOfGroup.portugues(type);
                break;
            } else if (in.inTypeEnglish(type)) {
                typesOfGroup = TypesOfGroup.english(type);
                break;
            }else {
                System.out.println("Tipo de grupo não registrado.");
            }
        }


        Ensemble newEnsemble = new Ensemble(name, styles, typesOfGroup);
        repository.save(newEnsemble);
    }
    //NEW ARTIST:
    private Artist newArtist(){
        System.out.print("Nome: ");
        var name = WRITE.nextLine();

        System.out.print("Idade: ");
        int old = WRITE.nextInt();
        WRITE.nextLine();

        System.out.print("Quantos instrumentos o(a) artista toca: ");
        int plays = WRITE.nextInt();
        WRITE.nextLine();
        List<Instruments> instruments = new ArrayList<>();

        for (int i = 0; i<plays; i++){
            while (true) {
                System.out.print((i + 1) + "° instrumento: ");
                var play = WRITE.nextLine();

                if (in.inInstrumentPortuguese(play)) {
                    instruments.add(Instruments.Portuguese(play));
                    break;

                } else if (in.inInstrumentEnglish(play)) {
                    instruments.add(Instruments.English(play));
                    break;

                } else {
                    System.out.println("Instrumento não encontrado. Tente novamente.");

                }
            }
        }
        return new Artist(name,old,instruments);
    }
    //NEW MUSIC:
    private Music newMusic(){
        System.out.print("Nome da musica: ");
        var name = WRITE.nextLine();
        System.out.print("Tempo da musica: ");
        double time = WRITE.nextDouble();
        WRITE.nextLine();
        return new Music(name,time);
    }
    //NEW ALBUM:
    private void newAlbum(){
        System.out.print("Nome do grupo ou artista: ");
        var groupName = WRITE.nextLine();
        Optional<Ensemble> foundGroup = repository.findByNameContainingIgnoreCase(groupName);
        while (true) {
            if (foundGroup.isPresent()) {
                System.out.print("Nome do álbum: ");
                var name = WRITE.nextLine();
                while (true) {
                    System.out.print("Data do lançamento do álbum (dd/MM/yyyy): ");
                    var release = WRITE.nextLine();
                    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    try {
                        LocalDate date = LocalDate.parse(release, dateFormatter);

                        Album album = new Album(name,date);
                        album.setEnsemble(foundGroup.get());
                        foundGroup.get().getAlbums().add(album);
                        repository.save(foundGroup.get());
                        return;

                    } catch (Exception e) {
                        System.out.println("Formatação invalida, tente novamente.");
                    }
                }
            } else {
                System.out.println("Grupo ou Artista não encontrado.\n Cria novo grupo?");
                var options = WRITE.nextLine();
                if (options.contains("s") || options.contains("y")) {
                    newEnsemble();
                } else {
                    System.out.println("Álbum retornará como null");
                    return;
                }
            }
        }
    }

}
