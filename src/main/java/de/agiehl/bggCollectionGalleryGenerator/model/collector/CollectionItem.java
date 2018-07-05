package de.agiehl.bggCollectionGalleryGenerator.model.collector;

import java.util.Objects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.NONE)
public class CollectionItem {

	@XmlAttribute(name = "objecttype", required = false)
	private String type;

	@XmlAttribute(name = "objectid", required = true)
	private int id;

	@XmlAttribute(name = "subtype", required = false)
	private String subtype;

	@XmlAttribute(name = "collid", required = false)
	private long collectionId;

	@XmlElement(name = "name", required = false)
	private String name;

	@XmlElement(name = "yearpublished", required = false)
	private String year;

	@XmlElement(name = "image", required = false)
	private String imageUrl;

	@XmlElement(name = "thumbnail", required = false)
	private String thumbnailUrl;

	@XmlElement(required = false)
	private String status;

	@XmlElement(required = false)
	private int numplays;

	@XmlElement(required = false)
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

	public String getYear() {
		return year;
	}

	public String getStatus() {
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
		return id == that.id && collectionId == that.collectionId && numplays == that.numplays
				&& Objects.equals(type, that.type) && Objects.equals(subtype, that.subtype)
				&& Objects.equals(name, that.name) && Objects.equals(year, that.year)
				&& Objects.equals(imageUrl, that.imageUrl) && Objects.equals(thumbnailUrl, that.thumbnailUrl)
				&& Objects.equals(status, that.status) && Objects.equals(comment, that.comment);
	}

	@Override
	public int hashCode() {
		return Objects.hash(type, id, subtype, collectionId, name, year, imageUrl, thumbnailUrl, status, numplays,
				comment);
	}
}
