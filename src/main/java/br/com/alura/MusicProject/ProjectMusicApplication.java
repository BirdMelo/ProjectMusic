package br.com.alura.MusicProject;

import br.com.alura.MusicProject.Main.Main;
import br.com.alura.MusicProject.Repository.MusicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ProjectMusicApplication implements CommandLineRunner {
	@Autowired
	private MusicRepository repository;

	public static void main(String[] args) {
		SpringApplication.run(ProjectMusicApplication.class, args);
	}

	public void run (String... args) throws Exception{
		Main main = new Main(repository);
		main.showMenu();

	}

}
