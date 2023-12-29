package br.com.alura.MusicProject.Service;


import br.com.alura.MusicProject.Model.Classes.Album;
import br.com.alura.MusicProject.Model.Classes.Artist;
import br.com.alura.MusicProject.Model.Classes.Ensemble;
import br.com.alura.MusicProject.Model.Classes.Music;
import br.com.alura.MusicProject.Model.Enums.Instruments;
import br.com.alura.MusicProject.Model.Enums.Styles;
import br.com.alura.MusicProject.Model.Enums.TypesOfGroup;
import br.com.alura.MusicProject.Repositorys.AlbumRepository;
import br.com.alura.MusicProject.Repositorys.EnsembleRepository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class MusicService {
    private final Scanner WRITE = new Scanner(System.in);
    private final EnsembleRepository eRepository;
    private final AlbumRepository aRepository;
    public Service in = new Service();
    public MusicService(EnsembleRepository eRepository, AlbumRepository aRepository){
        this.eRepository = eRepository;
        this.aRepository = aRepository;
    }

//    ADD MUSIC:
    public void addMusic(){
        while (true) {
            System.out.print("Nome conjunto: ");
            var nameOfEnsemble = WRITE.nextLine();
            Optional<Ensemble> ensemble = eRepository.findByNameContainingIgnoreCase(nameOfEnsemble);
            if (ensemble.isPresent()) {
                System.out.print("Nome do album: ");
                var nameOfAlbum = WRITE.nextLine();
                Optional<Album> foundAlbum = eRepository.findAlbum(nameOfEnsemble, nameOfAlbum);
                if (foundAlbum.isPresent()) {
                    System.out.print("Quantidade de musicas: ");
                    int qMusic = WRITE.nextInt();
                    WRITE.nextLine();
                    for (int i = 0; i < qMusic; i++) {
                        System.out.printf("=============== %d° música ===============%n", i + 1);
                        Music music = newMusic();
                        music.setAlbum(foundAlbum.get());
                        foundAlbum.get().getMusics().add(music);

                        aRepository.save(foundAlbum.get());
                    }
                    System.out.println("==========================================");
                    break;
                }else {
                    System.out.println("Nome do album está incorreto ou não consta no Banco de dados.\n " +
                            "Tente novamente.");
                }
            }else {
                System.out.println("Nome do artista ou grupo está incorreto ou não consta no Banco de dados.\n " +
                        "Tente novamente.");
            }
        }

    }
//    ENSEMBLE:
    public void newEnsemble(){
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
                        WRITE.nextLine();
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
                    System.out.printf("=============== %d° artista ===============%n",i+1);
                    Artist artist = newArtist();
                    artistList.add(artist);
                }
                System.out.println("=============================================");
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
        for (Artist artist : artistList) {
            artist.setGroup(newEnsemble);
        }

        eRepository.save(newEnsemble);
    }
//    NEW ARTIST:
    private Artist newArtist(){

//        Attributes:
        String name;
        int old;
        List<Instruments> instruments = new ArrayList<>();
        int plays;

//        Structure:
        System.out.print("Nome do artista: ");
        name = WRITE.nextLine();
        while (true) {
            try {
                System.out.print("Idade: ");
                old = WRITE.nextInt();
                WRITE.nextLine();
                break;
            }catch (InputMismatchException e){
                System.out.println("Digitação incorreta. Tente novamente");
                WRITE.nextLine();
            }
        }
        while (true) {
            try {
                System.out.print("Quantos instrumentos o(a) artista toca: ");
                plays = WRITE.nextInt();
                WRITE.nextLine();
                break;
            }catch (InputMismatchException e){
                System.out.println("Digitação incorreta. Tente novamente");
                WRITE.nextLine();
            }
        }

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
//    NEW MUSIC:
    private Music newMusic(){
        System.out.print("Nome da musica: ");
        var name = WRITE.nextLine().replace("'","’");
        System.out.print("Tempo da musica: ");
        double time = WRITE.nextDouble();
        WRITE.nextLine();
        return new Music(name,time);
    }
//    NEW ALBUM:
    public void newAlbum(){
        System.out.print("Nome do grupo ou artista: ");
        var groupName = WRITE.nextLine();
        Optional<Ensemble> foundGroup = eRepository.findByNameContainingIgnoreCase(groupName);
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

                        System.out.println("Deseja adicionar musicas ao album?");
                        var option = WRITE.nextLine();
                        if (option.contains("s") || option.contains("y")){
                            System.out.println("Quantas musicas?");
                            int qMusic = WRITE.nextInt();
                            WRITE.nextLine();
                            List<Music> musicList = new ArrayList<>();
                            for (int i = 0; i < qMusic; i++ ){
                                System.out.printf("=============== %d° música ===============%n",i+1);
                                Music music = newMusic();
                                music.setAlbum(album);
                                musicList.add(music);
                            }
                            System.out.println("=========================================");
                            album.setMusics(musicList);
                        }
                        eRepository.save(foundGroup.get());
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
//    SEARCH MUSIC:
    public void searchMusic() {
        System.out.print("Nome da música: ");
        var musicName = WRITE.nextLine();
        List<Music> musicList = aRepository.findMusic(musicName);
        if (!musicList.isEmpty()) {
            musicList.forEach(System.out::println);
        }else {
            System.out.println("Não foi encontrado nem uma música com esse nome ou fragmento.");
        }

    }
//    SEARCH ALBUM:
    public void searchAlbumByEnsemble() {
        System.out.print("Nome do grupo ou artista solo: ");
        String ensembleName = WRITE.nextLine();
        List<Album> albums = eRepository.findAlbumByEnsembleName(ensembleName);
        albums.forEach(System.out::println);
    }

    public void searchMusicByEnsemble() {
        System.out.print("Nome do grupo ou artista solo: ");
        String ensembleName = WRITE.nextLine();
        List<Album> albums = eRepository.findAlbumByEnsembleName(ensembleName);
        albums.stream()
                .sorted(Comparator.comparing(Album::getRelease))
                .forEach(album -> {
                    System.out.printf("""
                                            %s
                            """, album.getName());
                    album.getMusics().forEach(System.out::println);
                });
    }
}
