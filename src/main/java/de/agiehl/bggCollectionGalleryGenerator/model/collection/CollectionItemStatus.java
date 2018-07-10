package de.agiehl.bggCollectionGalleryGenerator.model.collection;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class CollectionItemStatus {

	@JacksonXmlProperty(isAttribute = true)
	private boolean own;
	@JacksonXmlProperty(isAttribute = true)
	private boolean prevowned;
	@JacksonXmlProperty(isAttribute = true)
	private boolean fortrade;
	@JacksonXmlProperty(isAttribute = true)
	private boolean want;
	@JacksonXmlProperty(isAttribute = true)
	private boolean wanttoplay;
	@JacksonXmlProperty(isAttribute = true)
	private boolean wanttobuy;
	@JacksonXmlProperty(isAttribute = true)
	private boolean wishlist;
	@JacksonXmlProperty(isAttribute = true)
	private boolean preordered;
	@JacksonXmlProperty(isAttribute = true)
	private String lastmodified;

	public boolean isOwn() {
		return own;
	}

	public boolean isPrevowned() {
		return prevowned;
	}

	public boolean isFortrade() {
		return fortrade;
	}

	public boolean isWant() {
		return want;
	}

	public boolean isWanttoplay() {
		return wanttoplay;
	}

	public boolean isWanttobuy() {
		return wanttobuy;
	}

	public boolean isWishlist() {
		return wishlist;
	}

	public boolean isPreordered() {
		return preordered;
	}

	public String getLastmodified() {
		return lastmodified;
	}

}
