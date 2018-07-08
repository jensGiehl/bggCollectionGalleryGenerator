package de.agiehl.bggCollectionGalleryGenerator;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.util.StringUtils;

import de.agiehl.bggCollectionGalleryGenerator.bggCommunicator.CollectionDownloader;
import de.agiehl.bggCollectionGalleryGenerator.ftp.FileUploader;
import de.agiehl.bggCollectionGalleryGenerator.galleryGenerator.GalleryGenerator;
import de.agiehl.bggCollectionGalleryGenerator.model.collector.UserCollection;

@SpringBootApplication
public class Application implements CommandLineRunner {

	@Autowired
	private CollectionDownloader colDownloader;

	@Autowired
	private GalleryGenerator galleryGenerator;

	@Autowired
	private FileUploader fileUploader;

	@Value("${bgg.username}")
	private String bggUsername;

	@Override
	public void run(String... args) throws Exception {
		if (StringUtils.isEmpty(bggUsername)) {
			throw new IllegalArgumentException("bgg.username should not be empty");
		}

		UserCollection userCollections = colDownloader.loadUserCollections(bggUsername);

		File generatedGalleryHtml = galleryGenerator.generateHtmlForCollection(userCollections);

		fileUploader.uploadFiles(generatedGalleryHtml);
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
