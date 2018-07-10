package de.agiehl.bggCollectionGalleryGenerator.model.collection;

import java.util.Objects;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class CollectionItem {

	@JacksonXmlProperty(localName = "objecttype", isAttribute = true)
	private String type;

	@JacksonXmlProperty(localName = "objectid", isAttribute = true)
	private int id;

	@JacksonXmlProperty(localName = "subtype", isAttribute = true)
	private String subtype;

	@JacksonXmlProperty(localName = "collid", isAttribute = true)
	private long collectionId;

	@JacksonXmlProperty(localName = "name", isAttribute = false)
	private String name;

	@JacksonXmlProperty(localName = "yearpublished", isAttribute = false)
	private Integer year;

	@JacksonXmlProperty(localName = "image", isAttribute = false)
	private String imageUrl;

	@JacksonXmlProperty(localName = "thumbnail", isAttribute = false)
	private String thumbnailUrl;

	@JacksonXmlProperty(localName = "status", isAttribute = false)
	private CollectionItemStatus status;

	@JacksonXmlProperty(localName = "numplays", isAttribute = false)
	private int numplays;

	@JacksonXmlProperty(localName = "comment", isAttribute = false)
	private String comment;

	public long getCollectionId() {
		return collectionId;
	}

	public int getId() {
		return id;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public String getName() {
		return name;
	}

	public String getSubtype() {
		return subtype;
	}

	public String getThumbnailUrl() {
		return thumbnailUrl;
	}

	public String getType() {
		return type;
	}

	public Integer getYear() {
		return year;
	}

	public CollectionItemStatus getStatus() {
		return status;
	}

	public int getNumPlays() {
		return numplays;
	}

	public String getComment() {
		return comment;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		CollectionItem that = (CollectionItem) o;
		return id == that.id;
	}

	@Override
	public int hashCode() {
		return Objects.hash(type, id, subtype, collectionId, name, year, imageUrl, thumbnailUrl, numplays, comment);
	}
}
