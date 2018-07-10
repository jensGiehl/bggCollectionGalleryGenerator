package de.agiehl.bggCollectionGalleryGenerator.galleryGenerator;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.List;
import java.util.Locale;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.googlecode.htmlcompressor.compressor.HtmlCompressor;

import de.agiehl.bggCollectionGalleryGenerator.model.collection.CollectionItem;
import de.agiehl.bggCollectionGalleryGenerator.model.collection.UserCollection;

@Component
public class GalleryGenerator {

	@Autowired
	private TemplateEngine templateEngine;

	private static final Logger logger = LoggerFactory.getLogger(GalleryGenerator.class);

	public File generateHtmlForCollection(UserCollection collection) {
		String htmlAsString = generateHtml(collection);

		htmlAsString = compressHtml(htmlAsString);

		File tmpFile = writeHtmlToTempFile(htmlAsString);
		return tmpFile; // TODO: Optional
	}

	private File writeHtmlToTempFile(String htmlAsString) {
		File tmpFile = null;
		PrintWriter out = null;
		try {
			tmpFile = Files.createTempFile("bggGallery", ".html").toFile();

			out = new PrintWriter(tmpFile);
			out.println(htmlAsString);
		} catch (IOException e) {
			logger.error("Error while writing HTML to file (" + tmpFile + ")", e);
		} finally {
			// TODO: autocloseable
			if (out != null) {
				out.close();
			}
		}
		return tmpFile;
	}

	private String generateHtml(UserCollection collection) {
		Context ctx = new Context(Locale.ENGLISH);
		ctx.setVariable("username", collection.getUsername());
		ctx.setVariable("date", collection.getPubDate());

		List<CollectionItem> ownedItems = getOwnedItems(collection);
		ctx.setVariable("collection", ownedItems);
		logger.info("Found {} owned items", ownedItems.size());

		String htmlAsString = templateEngine.process("gallery.html", ctx);
		return htmlAsString;
	}

	private String compressHtml(String htmlAsString) {
		HtmlCompressor compressor = new HtmlCompressor();
		htmlAsString = compressor.compress(htmlAsString);
		return htmlAsString;
	}

	private List<CollectionItem> getOwnedItems(UserCollection collection) {
		return collection.getItems().stream() //
				.filter(hasAnImage()) //
				.filter(owned()) //
				.distinct() //
				.collect(Collectors.toList());
	}

	private Predicate<? super CollectionItem> owned() {
		return item -> item.getStatus().isOwn();
	}

	private Predicate<? super CollectionItem> hasAnImage() {
		return item -> !StringUtils.isEmpty(item.getImageUrl());
	}

}
