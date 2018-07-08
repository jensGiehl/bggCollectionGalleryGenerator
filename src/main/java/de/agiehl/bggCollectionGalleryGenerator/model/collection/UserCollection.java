package de.agiehl.bggCollectionGalleryGenerator.model.collection;

import java.util.List;
import java.util.Objects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "items")
@XmlAccessorType(XmlAccessType.NONE)
public class UserCollection {

	@XmlAttribute(name = "items", required = false)
	private int totalItems;

	@XmlAttribute(name = "termsofuse", required = false)
	private String termsOfUseUrl;

	@XmlAttribute(name = "pubdate", required = false)
	private String pubDate;

	@XmlElement(name = "item", required = false)
	private List<CollectionItem> items;

	private String username;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getTotalItems() {
		return totalItems;
	}

	public String getTermsOfUseUrl() {
		return termsOfUseUrl;
	}

	public List<CollectionItem> getItems() {
		return items;
	}

	public String getPubDate() {
		return pubDate;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		UserCollection that = (UserCollection) o;
		return totalItems == that.totalItems && Objects.equals(termsOfUseUrl, that.termsOfUseUrl)
				&& Objects.equals(pubDate, that.pubDate) && Objects.equals(items, that.items);
	}

	@Override
	public int hashCode() {
		return Objects.hash(totalItems, termsOfUseUrl, pubDate, items);
	}
}