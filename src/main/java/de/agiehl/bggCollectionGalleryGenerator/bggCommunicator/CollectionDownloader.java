package de.agiehl.bggCollectionGalleryGenerator.bggCommunicator;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpStatus.ACCEPTED;
import static org.springframework.http.HttpStatus.OK;

import java.io.StringReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

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
				logError(response);
				waitForRetry();
				return loadUserCollection(userName, retryCount + 1);
			}
		}

		if (!response.getStatusCode().equals(OK)) {
			throw new RestClientException(collectionUrl + " could not loaded: " + response.getStatusCode());
		}

		return convertXmlToObject(userName, response);
	}

	private UserCollection convertXmlToObject(String userName, ResponseEntity<String> response) {
		UserCollection userCollection = null;
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(UserCollection.class);
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			userCollection = (UserCollection) unmarshaller.unmarshal(new StringReader(response.getBody()));

			userCollection.setUsername(userName);
		} catch (JAXBException e) {
			logger.error("Error while converting XML to object", e);
		}
		return userCollection;
	}

	private void logError(ResponseEntity<String> response) {
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(ErrorObject.class);
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			ErrorObject error = (ErrorObject) unmarshaller.unmarshal(new StringReader(response.getBody()));

			logger.info("Response message form bgg: {}", error.getMessage());
		} catch (JAXBException e) {
			logger.error("Error while converting XML to object: " + response.getBody(), e);
		}
	}

	private void waitForRetry() {
		try {
			Thread.sleep(waitBetweenRetries);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
