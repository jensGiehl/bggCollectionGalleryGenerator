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
import de.agiehl.bggCollectionGalleryGenerator.galleryGenerator.ThumbnailGalleryGenerator;
import de.agiehl.bggCollectionGalleryGenerator.model.collection.UserCollection;

@SpringBootApplication
public class Application implements CommandLineRunner {

	@Autowired
	private CollectionDownloader colDownloader;

	@Autowired
	private GalleryGenerator galleryGenerator;

	@Autowired
	private ThumbnailGalleryGenerator thumbnailGenerator;

	@Autowired
	private FileUploader fileUploader;

	@Value("${bgg.username}")
	private String bggUsername;

	@Override
	// TODO: banner.txt
	public void run(String... args) throws Exception {
		if (StringUtils.isEmpty(bggUsername)) {
			throw new IllegalArgumentException("bgg.username should not be empty");
		}

		UserCollection userCollections = colDownloader.loadUserCollections(bggUsername);

		File generatedGalleryHtml = galleryGenerator.generateHtmlForCollection(userCollections);

		File generatedThumnailGalleryHtml = thumbnailGenerator.generateHtmlForCollection(userCollections);

		fileUploader.uploadFiles(generatedGalleryHtml, generatedThumnailGalleryHtml);
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
