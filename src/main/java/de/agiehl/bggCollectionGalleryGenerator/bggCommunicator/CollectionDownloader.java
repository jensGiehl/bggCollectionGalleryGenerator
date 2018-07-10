package de.agiehl.bggCollectionGalleryGenerator.bggCommunicator;

import static com.fasterxml.jackson.databind.DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT;
import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpStatus.ACCEPTED;
import static org.springframework.http.HttpStatus.OK;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import de.agiehl.bggCollectionGalleryGenerator.model.collection.ErrorObject;
import de.agiehl.bggCollectionGalleryGenerator.model.collection.UserCollection;

@Component
public class CollectionDownloader {

	@Value("${bgg.collection.url:https://boardgamegeek.com/xmlapi2/collection?username=}")
	private String url;

	@Value("${bgg.maxRetry:3}")
	private int maxRetries;

	// TODO: This seems a little bit to complicated
	@Value("#{new Long(${bgg.waitBetweenRetriesInMilliSeconds:30000})}")
	private long waitBetweenRetries;

	private static final Logger logger = LoggerFactory.getLogger(CollectionDownloader.class);

	public UserCollection loadUserCollections(String userName) {
		return loadUserCollection(userName, 1);
	}

	private UserCollection loadUserCollection(String userName, int retryCount) {
		String collectionUrl = url + userName;

		logger.info("Loading {} (Retry-Count: {}/{})", collectionUrl, retryCount, maxRetries);

		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> response = restTemplate.exchange(collectionUrl, GET, null, String.class);

		logger.info("Response Statuscode: {}", response.getStatusCode());

		if (response.getStatusCode().equals(ACCEPTED)) {
			if (retryCount <= maxRetries) {
				try {
					ObjectMapper xmlMapper = new XmlMapper();
					xmlMapper.configure(FAIL_ON_UNKNOWN_PROPERTIES, false);
					ErrorObject error = xmlMapper.readValue(response.getBody(), ErrorObject.class);
					logger.info("Response message form bgg: {}", error.getMessage());
				} catch (IOException e) {
					logger.error("Error while converting XML to object: " + response.getBody(), e);
				}
				waitForRetry();
				return loadUserCollection(userName, retryCount + 1);
			}
		}

		if (!response.getStatusCode().equals(OK)) {
			throw new RestClientException(collectionUrl + " could not loaded: " + response.getStatusCode());
		}

		UserCollection userCollection = null;
		try {
			ObjectMapper xmlMapper = new XmlMapper();
			xmlMapper.disable(FAIL_ON_UNKNOWN_PROPERTIES);
			xmlMapper.enable(ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
			userCollection = xmlMapper.readValue(response.getBody(), UserCollection.class);
			userCollection.setUsername(userName);
		} catch (IOException e) {
			logger.error("Error while converting XML to object", e);
		}

		return userCollection;
	}

	private void waitForRetry() {
		try {
			Thread.sleep(waitBetweenRetries);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
