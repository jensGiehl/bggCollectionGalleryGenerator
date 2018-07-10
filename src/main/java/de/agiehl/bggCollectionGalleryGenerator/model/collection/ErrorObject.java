package de.agiehl.bggCollectionGalleryGenerator.model.collection;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

import org.springframework.util.StringUtils;

@XmlRootElement(name = "message")
@XmlAccessorType(XmlAccessType.NONE)
public class ErrorObject {

	@XmlValue
	private String message;

	public String getMessage() {
		return StringUtils.replace(message, "\n", "").trim();
	}

}
