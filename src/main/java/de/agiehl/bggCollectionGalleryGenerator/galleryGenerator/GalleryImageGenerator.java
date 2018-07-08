package de.agiehl.bggCollectionGalleryGenerator.galleryGenerator;

import java.util.function.Function;
import java.util.function.Predicate;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import de.agiehl.bggCollectionGalleryGenerator.model.collector.CollectionItem;
import de.agiehl.bggCollectionGalleryGenerator.model.collector.UserCollection;

@Component
public class GalleryImageGenerator {

	public String generateHtmlSnippetForImages(UserCollection collection) {
		StringBuilder htmlSnippet = new StringBuilder();

		collection.getItems().stream() //
				.filter(hasAnImage()) //
				.filter(owned()) //
				.distinct() //
				.map(convertToHtml()) //
				.forEach(htmlSnippet::append);

		return htmlSnippet.toString();
	}

	private Function<? super CollectionItem, ? extends String> convertToHtml() {
		return item -> "<a href=\"" + item.getImageUrl() + "\" title=\"" + item.getName() + "\"></a>";
	}

	private Predicate<? super CollectionItem> owned() {
		return item -> item.getStatus().isOwn();
	}

	private Predicate<? super CollectionItem> hasAnImage() {
		return item -> !StringUtils.isEmpty(item.getImageUrl());
	}

}
