package br.com.alura.MusicProject;

import br.com.alura.MusicProject.Service.MusicService;
import br.com.alura.MusicProject.Repositorys.AlbumRepository;
import br.com.alura.MusicProject.Repositorys.EnsembleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@SpringBootApplication
public class ProjectMusicApplication implements CommandLineRunner {
	@Autowired
	private EnsembleRepository eRepository;
	@Autowired
	private AlbumRepository aRepository;
	private static final Scanner WRITE = new Scanner(System.in);


	public static void main(String[] args) {
		SpringApplication.run(ProjectMusicApplication.class, args);
	}

	public void run (String... args) throws Exception{
		MusicService service = new MusicService(eRepository, aRepository);
		int option;
		do {
			System.out.print("""
                    ==============================================
                    1   - Registrar artista solo ou grupo;
                    2   - Registrar álbum;
                    3   - Registrar músicas em albuns já existentes;
                    4   - Buscar música;
                    5   - Buscar álbuns por nome de grupo ou artista;
                    6   - Buscar músicas por artista ou grupo;
                                    
                    0   - Sair do programa.
                    
                    Digite:\s""");
			option = WRITE.nextInt();
			WRITE.nextLine();
			switch (option){
				case 1:
					service.newEnsemble();
					break;
				case 2:
					service.newAlbum();
					break;
				case 3:
					service.addMusic();
				case 4:
					service.searchMusic();
					break;
				case 5:
					service.searchAlbumByEnsemble();
					break;
				case 6:
					service.searchMusicByEnsemble();
					break;
			}
		}while (option != 0);

	}

}
