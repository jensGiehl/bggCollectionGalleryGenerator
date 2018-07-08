package de.agiehl.bggCollectionGalleryGenerator.galleryGenerator;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.List;
import java.util.Locale;
import java.util.function.Predicate;
import java.util.stream.Collectors;

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

	public File generateHtmlForCollection(UserCollection collection) {
		Context ctx = new Context(Locale.ENGLISH);
		ctx.setVariable("username", collection.getUsername());
		ctx.setVariable("date", collection.getPubDate());
		ctx.setVariable("collection", getOwnedItems(collection));

		String htmlAsString = templateEngine.process("gallery.html", ctx);

		HtmlCompressor compressor = new HtmlCompressor();
		htmlAsString = compressor.compress(htmlAsString);

		File tmpFile = null;
		PrintWriter out = null;
		try {
			tmpFile = Files.createTempFile("bggGallery", ".html").toFile();

			out = new PrintWriter(tmpFile);
			out.println(htmlAsString);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// TODO: autocloseable
			if (out != null) {
				out.close();
			}
		}
		return tmpFile; // TODO: Optional
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
