package de.agiehl.bggCollectionGalleryGenerator;

import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import de.agiehl.bggCollectionGalleryGenerator.model.collector.UserCollection;

public class App {
	public static void main(String[] args) {
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<UserCollection> st = restTemplate.exchange(
				"https://boardgamegeek.com/xmlapi2/collection?username=jensG", HttpMethod.GET, null,
				UserCollection.class);

		st.getBody().getItems().stream().forEach(x -> System.out.println(x.getName()));
		// System.out.println(st.getBody().getTotalItems());
	}
}
