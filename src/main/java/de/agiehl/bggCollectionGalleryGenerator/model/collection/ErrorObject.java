package de.agiehl.bggCollectionGalleryGenerator.model.collection;

import org.springframework.util.StringUtils;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlText;

@JacksonXmlRootElement(localName = "message")
public class ErrorObject {

	@JacksonXmlText
	private String message;

	public String getMessage() {
		return StringUtils.replace(message, "\n", "").trim();
	}

}
