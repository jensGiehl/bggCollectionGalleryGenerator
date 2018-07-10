package de.agiehl.bggCollectionGalleryGenerator.model.collection;

import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@JacksonXmlRootElement(localName = "items")
public class UserCollection {

	@JacksonXmlProperty(localName = "totalitems", isAttribute = true)
	private int totalItems;

	@JacksonXmlProperty(localName = "termsofuse", isAttribute = true)
	private String termsOfUseUrl;

	@JacksonXmlProperty(localName = "pubdate", isAttribute = true)
	private String pubDate;

	@JacksonXmlProperty(localName = "item", isAttribute = false)
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