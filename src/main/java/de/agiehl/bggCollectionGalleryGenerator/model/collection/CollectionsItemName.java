package de.agiehl.bggCollectionGalleryGenerator.model.collection;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlText;

public class CollectionsItemName {

	@JacksonXmlText
	private String name;

	@JacksonXmlProperty(isAttribute = true)
	private int sortindex;

	public String getName() {
		return name;
	}

	public int getSortindex() {
		return sortindex;
	}

}
