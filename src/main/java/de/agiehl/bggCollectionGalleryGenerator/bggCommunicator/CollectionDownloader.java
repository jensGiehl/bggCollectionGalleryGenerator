package de.agiehl.bggCollectionGalleryGenerator.bggCommunicator;

import static org.springframework.http.HttpMethod.GET;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

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
		ResponseEntity<UserCollection> response = restTemplate.exchange(collectionUrl, GET, null, UserCollection.class);

		logger.info("Response Statuscode: {}", response.getStatusCode());

		if (response.getStatusCode().is4xxClientError()) {
			if (retryCount <= maxRetries) {
				waitForRetry();
				return loadUserCollection(userName, retryCount + 1);
			}
		}

		if (!response.getStatusCode().is2xxSuccessful()) {
			throw new RestClientException(collectionUrl + " could not loaded: " + response.getStatusCode());
		}

		UserCollection userCollection = response.getBody();
		userCollection.setUsername(userName);

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
