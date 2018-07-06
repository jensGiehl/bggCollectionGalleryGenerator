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

		/*
		 * <div id="links"> <a href="images/banana.jpg" title="Banana"> <img
		 * src="images/thumbnails/banana.jpg" alt="Banana"> </a> <a
		 * href="images/apple.jpg" title="Apple"> <img src="images/thumbnails/apple.jpg"
		 * alt="Apple"> </a> <a href="images/orange.jpg" title="Orange"> <img
		 * src="images/thumbnails/orange.jpg" alt="Orange"> </a> </div>
		 */

		System.out.println("<div id=\"links\">");
		st.getBody().getItems().stream()
				.filter(x -> x.getImageUrl() != null && !"null".equalsIgnoreCase(x.getImageUrl())) //
				.filter(x -> x.getStatus().isOwn()) //
				.distinct() //
				.forEach(x -> {
					System.out.println("<a href=\"" + x.getImageUrl() + "\" title=\"" + x.getName() + "\">");
					// System.out.println("<img src=\"" + x.getThumbnailUrl() + "\" alt=\"" +
					// x.getName() + "\">");
					System.out.println("</a>");
				});
		System.out.println("</div>");

	}
}
