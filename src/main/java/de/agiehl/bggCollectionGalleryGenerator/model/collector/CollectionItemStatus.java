package de.agiehl.bggCollectionGalleryGenerator.model.collector;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

@XmlAccessorType(XmlAccessType.NONE)
public class CollectionItemStatus {

	@XmlAttribute(required = false)
	private boolean own;
	@XmlAttribute(required = false)
	private boolean prevowned;
	@XmlAttribute(required = false)
	private boolean fortrade;
	@XmlAttribute(required = false)
	private boolean want;
	@XmlAttribute(required = false)
	private boolean wanttoplay;
	@XmlAttribute(required = false)
	private boolean wanttobuy;
	@XmlAttribute(required = false)
	private boolean wishlist;
	@XmlAttribute(required = false)
	private boolean preordered;
	@XmlAttribute(required = false)
	private String lastmodified;

	// own="1" prevowned="0" fortrade="0" want="0" wanttoplay="0" wanttobuy="0"
	// wishlist="0" preordered="0" lastmodified="2017-11-25 02:40:08"

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
