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
                    1   - Registrar artista solo ou grupo
                    2   - Registrar Álbum
                                    
                    0   - Sair do programa
                    
                    Digite:\s""");
            option = WRITE.nextInt();
            WRITE.nextLine();
            switch (option){
                case 1:
                    newEnsemble();
                    break;
                case 2:
                    newAlbum();
            }
        }while (option != 0);
    }
    //ENSEMBLE:
    private void newEnsemble(){
        List<Artist> artistList = new ArrayList<>();
        int qGroup;
        String groupName;
        while (true) {
            System.out.println("Artista solo ou grupo?");
            var option = WRITE.nextLine();
            if (option.toLowerCase().contains("solo")) {
                Artist artist = newArtist();
                artistList.add(artist);
                groupName = artist.getName();
                qGroup = 1;
                break;

            } else if (option.toLowerCase().contains("grupo")) {
                System.out.print("nome do grupo: ");
                groupName = WRITE.nextLine();
                while (true) {
                    try {
                        System.out.print("Quantidade de artistas no grupo: ");
                        qGroup = WRITE.nextInt();
                        if (qGroup > 0) {
                            break;
                        }else {
                            System.out.println("Uma grupo não pode ter menos de 1 artista.");
                        }
                    }catch (NumberFormatException e){
                        System.out.println("Por favor, digite números inteiros.");
                    }
                }
                for (int i = 0 ; i< qGroup ; i++){
                    Artist artist = newArtist();
                    artistList.add(artist);
                }
                break;

            } else {
                System.out.println("Opção incorreta. tente novamente.");
            }
        }
        TypesOfGroup type;
        if (qGroup == 1){
            type = TypesOfGroup.SOLO;
        } else if (qGroup == 2) {
            type = TypesOfGroup.DUET;
        } else if (qGroup == 3) {
            type = TypesOfGroup.TRIO;
        }else {
            type = TypesOfGroup.BAND;
        }
        Styles styles;
        while (true) {
            System.out.print("Estilo do grupo ou artista: ");
            var style = WRITE.nextLine();
            if (in.inStyle(style)){
                styles = Styles.styles(style);
                break;
            }else {
                System.out.println("Estilo não registrado. Tente novamente.");
            }

        }


        Ensemble newEnsemble = new Ensemble(groupName, styles,type);
        newEnsemble.setMembers(artistList);

        System.out.println(newEnsemble.getMembers());

//        repository.save(newEnsemble);
        System.out.println(newEnsemble);
    }
    //NEW ARTIST:
    private Artist newArtist(){
        System.out.print("Nome do artista: ");
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
